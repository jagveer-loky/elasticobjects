package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Werner on 02.07.2014.
 * Refactored 28.2.2015: Moved to adapter pattern
 * Refactored 11.4.2018: Split in ExecutorListTemplate and ExecutorList
 */
public class ExecutorList {
    private static final Logger LOG = LogManager.getLogger(ExecutorList.class);
    public ExecutorList() {
    }
    /**
     * This will init the content of the template with a certain parseType
     * <li>template: The template to be parsed.</li>
     * <li>cd: will focus on a certain point of the mapActionKey.</li>
     * <li>parseType: the type of the comment tag</li>
     */

    public String execute(final EO eo) {
        if (eo == null) {
            eo.error("Null adapter!");
            return "";
        }
        List<Call> callList = (List<Call>)eo.get(PathElement.CALLS);
        if (callList.isEmpty()) {
            eo.info("Empty executor list");
            return "!!! Empty executor list!!! ";
        }
        StringBuilder result = new StringBuilder();
        for (String key : eo.getEo(PathElement.CALLS).keys()) {
            try {
                result.append(new CallExecutorImpl().execute(eo, eo.getEo(PathElement.CALLS, key)));
            } catch (Exception e) {
                eo.error(e.getMessage());
            }
        }
        return result.toString();
    }
}
