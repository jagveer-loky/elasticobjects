package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.LogLevel;

import java.util.List;

/**
 * Class for call executions.
 *
 * @author Werner Diwischek
 * @version 0.01
 * @date 2.5.2018
 */
public class CallExecutorImpl implements CallExecutor {

    @Override
    public Object transform(Object object) {
        return object;
    }

    public Object execute(final EO eo, final EO eoCall) {
        if (eo == null) {
            return "eo is null!";
        }
        if (eoCall == null) {
            return "call eo is null!";
        }
        Call call = (Call) eoCall.get();
        if (call.getDuration() != null) {
            eo.warn("Already executed within " + call.getDuration() + "ms. ");
            return "Already executed within " + call.getDuration() + "ms. ";
        }
        EO source = eo;
        if (call.hasSourcePath()) {
            source = eo.getEo(call.getSourcePath());
        }

        if (!call.hasTargetPath() && call.hasLocationPath()) {
            eoCall.set(call.getLocationPath(), "targetPath");
        }
        eoCall.set(0L, "duration");
        //List<String> sourcePaths = getPathList(sourcePath, eo);
        List<String> sourcePaths = source.keys(call.getFilterPath());
        EO target = eo;
        boolean createTarget = sourcePaths.size() > 1 && call.getTargetPath() != null;
        if (createTarget) {
            target = eo.set( source.getModel().create(), call.getTargetPath());
        }
        Object returnValue = null;

        Long startTime = System.currentTimeMillis();
        for (String sourcePath : sourcePaths) {
            Object value = transform(call.execute(source.getEo(sourcePath)));
            if (createTarget) {
                target.set(value, sourcePath);
            }
            else if (call.getTargetPath() != null) {
                eo.set(value, call.getTargetPath());
            }
            else {
                source.set(value, sourcePath);
            }
            returnValue = value;
        }
        Long duration = System.currentTimeMillis() - startTime;
        eoCall.set(duration, "duration");
        eo.info("Successfully executed within " + call.getDuration() + " ms.");
        return returnValue;
    }

}
