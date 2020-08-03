package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;

import java.util.List;
import java.util.Set;

/**
 */
public class ExecutorList {
    private static final Logger LOG = LogManager.getLogger(ExecutorList.class);
    public ExecutorList() {
    }
    public String execute(final EO eo) {
        if (eo == null) {
            eo.error("Null adapter!");
            return "";
        }
        Set<String> keys = eo.getCallsEo().keys();
        if (keys.isEmpty()) {
            eo.info("No calls");
            return "";
        }
        StringBuilder templateResult = new StringBuilder();
        int counter = 0;
        for (String key : eo.getCallsEo().keys()) {
            try {
                EO callEo = eo.getCallsEo().getEo(key);
                if (callEo == null) {
                    continue;
                }
                Call call =  (Call)callEo.get();
                templateResult.append(new CallExecutorImpl().execute(eo, call));
                callEo.set(call.getDuration(), "duration");
            } catch (Exception e) {
                eo.error("Problem executing call for " + counter + ": " + e.getMessage());
            }
            counter++;
        }
        if (templateResult.length()>0) {
            eo.set(templateResult.toString(), "_template");
        }
        return templateResult.toString();
    }

    public String execute(final EO eo, List<Call> callList) {
        if (callList == null || callList.isEmpty()) {
            eo.info("Empty executor list");
            return "!!! Empty executor list!!! ";
        }
        StringBuilder templateResult = new StringBuilder();
        int counter = 0;
        for (Call call : callList) {
            try {
                templateResult.append(new CallExecutorImpl().execute(eo, call));
            } catch (Exception e) {
                eo.error("Problem executing call for " + counter + ": " + e.getMessage());
            }
            counter++;
        }
        if (templateResult.length()>0) {
            eo.set(templateResult.toString(), "_template");
        }
        return templateResult.toString();
    }
}
