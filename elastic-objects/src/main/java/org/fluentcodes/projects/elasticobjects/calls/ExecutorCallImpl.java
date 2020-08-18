package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.KeepCalls;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;

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
            eo.warn("Already executed within " + call.getDuration() + "ms. ");
            return "Already executed within " + call.getDuration() + "ms. ";
        }

        EO source = eo;
        if (call.hasSourcePath()) {
            source = eo.getEo(call.getSourcePath());
        }
        List<String> loopPaths = source.keys(call.getFilterPath());
        EO target = eo;
        boolean createTarget = loopPaths.size() > 1 && call.getTargetPath() != null && !(call.getInTemplate());
        if (createTarget) {
            target = eo.set( source.getModel().create(), call.getTargetPath());
        }
        StringBuilder templateResult = new StringBuilder();
        templateResult.append(call.prepend());
        long startTime = System.currentTimeMillis();

        for (String sourcePath : loopPaths) {
            EO sourceLoop = source.getEo(sourcePath);
            if (!call.filter(sourceLoop)) {
                continue;
            }
            Object value = call.execute(sourceLoop);
            if (createTarget) {
                target.set(value, sourcePath);
            }
            else {
                if (call.getInTemplate()) {
                    templateResult.append(value);
                }
                else {
                    if (call.getTargetPath() != null) {
                        eo.set(value, call.getTargetPath());
                    } else {
                        sourceLoop.mapObject(value);
                    }
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
