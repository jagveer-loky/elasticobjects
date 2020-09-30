package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserEoReplace;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/**
 * Class for call executions.
 *
 * @author Werner Diwischek
 * @version 0.01
 * @date 2.5.2018
 */
public class ExecutorCallImpl implements ExecutorCall {
    public Object execute(final EO eo, final Call call) {
        if (eo == null) {
            return "eo is null!";
        }
        if (call == null) {
            return "call eo is null!";
        }
        if (call.getDuration() != null) {
            eo.warn("Already executed within " + call.getDuration() + " ms. ");
            return "Already executed within " + call.getDuration() + " ms. ";
        }
        String sourcePathString = new ParserEoReplace(call.getSourcePath()).parse(eo);
        Path sourcePath = new Path(eo.getPathAsString(), sourcePathString);
        EO sourceParent = sourcePath.moveToParent(eo);
        boolean isFilter = sourcePath.isFilter();
        if (!call.filter(sourceParent)) {
            return "";
        }
        List<String> loopPaths = sourceParent.keys(sourcePath.getParentKey());
        // get targetParent
        String targetPath = call.getTargetPath();
        if (targetPath == null||targetPath.isEmpty()) {
            if (isFilter) {
                call.setTargetPath(sourceParent.getPathAsString());
                targetPath = new Path(eo.getPathAsString(), call.getTargetPath()).toString();
            }
            else {
                targetPath = sourcePath.toString();
            }
        }

        StringBuilder templateResult = new StringBuilder();
        templateResult.append(call.prepend());
        long startTime = System.currentTimeMillis();
        if (PathElement.SAME.equals(call.getTargetPath())) {
            call.setInTemplate(true);
        }
        for (String entry : loopPaths) {
            EO sourceEntry = sourceParent.getEo(entry);
            if (!call.localFilter(sourceEntry)) {
                continue;
            }
            if (isFilter) {
                call.setTargetPath(targetPath + Path.DELIMITER + entry);
            }
            else {
                call.setTargetPath(targetPath);
            }
            try {
                templateResult.append(call.execute(sourceEntry));
            }
            catch(EoException e) {
                eo.error(e.getMessage());
                if (call.getInTemplate()) {
                    templateResult.append(e.getMessage());
                }
                continue;
            }
        }
        Long duration = System.currentTimeMillis() - startTime;
        call.setDuration(duration);
        templateResult.append(call.postPend());
        eo.info("Successfully executed within " + call.getDuration() + " ms.");
        return templateResult.toString();
    }

    @Override
    public Object transform(Object object) {
        return object;
    }
}
