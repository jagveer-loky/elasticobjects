package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;

import java.util.List;

/**
 * Class for call executions.
 *
 * @author Werner Diwischek
 * @version 0.01
 * @date 2.5.2018
 */
public class CallExecutorImpl implements CallExecutor {
    public Object execute(final EO eo, final Call call) {
        if (eo == null) {
            return "eo is null!";
        }
        if (call == null) {
            return "call eo is null!";
        }
        if (call.getDuration() != null) {
            eo.warn("Already executed within " + call.getDuration() + "ms. ");
            return "Already executed within " + call.getDuration() + "ms. ";
        }
        EO source = eo;
        if (call.hasSourcePath()) {
            source = eo.getEo(call.getSourcePath());
        }
        //List<String> sourcePaths = getPathList(sourcePath, eo);
        List<String> loopPaths = source.keys(call.getFilterPath());
        EO target = eo;
        boolean createTarget = loopPaths.size() > 1 && call.getTargetPath() != null && !(call.getInTemplate());
        if (createTarget) {
            target = eo.set( source.getModel().create(), call.getTargetPath());
        }
        long startTime = System.currentTimeMillis();
        StringBuilder templateResult = new StringBuilder();
        for (String sourcePath : loopPaths) {
            EO sourceLoop = source.getEo(sourcePath);
            Object value = call.execute(sourceLoop);
            if (createTarget) {
                target.set(value, sourcePath);
            }
            else {
                /*if (call.hasModels()) {
                    if (call.getTargetPath() != null) {
                        eo.set( new Models(eo.getConfigsCache(), call.getModelsAsArray()), call.getTargetPath());
                    } else {
                        eo.set(new Models(eo.getConfigsCache(), call.getModelsAsArray()),sourcePath);
                    }
                }*/
                if (call.getInTemplate()) {
                    templateResult.append(value);
                }
                else {
                    if (call.getTargetPath() != null) {
                        eo.set(value, call.getTargetPath());
                    } else {
                        source.set(value, sourcePath);
                    }
                }
            }
        }
        Long duration = System.currentTimeMillis() - startTime;
        call.setDuration(duration);
        eo.info("Successfully executed within " + call.getDuration() + " ms.");
        return templateResult.toString();
    }

    @Override
    public Object transform(Object object) {
        return object;
    }
}
