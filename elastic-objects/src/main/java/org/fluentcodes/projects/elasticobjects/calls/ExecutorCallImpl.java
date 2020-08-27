package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathElement;

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

        EO source = eo;
        if (!call.filter(source)) {
            return null;
        }
        Path sourcePath = new Path(eo.getPathAsString(), call.getSourcePath());
        boolean isFilter = sourcePath.isFilter();
        EO sourceParent = sourcePath.moveToParent(eo);
        List<String> loopPaths = sourceParent.keys(sourcePath.getParentKey());

        // get targetParent
        if (!call.hasTargetPath()) {
            if (isFilter) {
                call.setTargetPath(sourceParent.getPathAsString());
            }
            else {
                call.setTargetPath(call.getSourcePath());
            }
        }
        Path targetPath = new Path(eo.getPathAsString(), call.getTargetPath());
        EO targetParent = null;

        if (isFilter) {
            targetParent = targetPath.create(eo, sourceParent.getModels().create());
        }
        else {
            if (targetPath.hasModel()) {
                EO targetEo = targetPath.create(eo);
                targetParent = targetEo.getParent();
            }
            else {
                targetParent = targetPath.createParent(eo, sourceParent.getModels());
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
            /*if (sourceEntry.isEmpty()) {
                continue;
            }*/
            if (!call.localFilter(sourceEntry)) {
                continue;
            }
            Object value = call.execute(sourceEntry);
            if (call.getInTemplate()) {
                templateResult.append(value);
            }
            else {
                if (isFilter) {
                    targetParent.set(value, entry);
                }
                else {
                    targetParent.set(value, targetPath.getParentKey());
                }
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
