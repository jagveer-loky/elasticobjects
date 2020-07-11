package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 02.07.2014.
 * Refactored 28.2.2015: Moved to adapter pattern
 * Refactored 11.4.2018: Split in ExecutorListTemplate and ExecutorList
 */
public class ExecutorList {
    private static final Logger LOG = LogManager.getLogger(ExecutorList.class);
    private final List<CallExecutor> executorList;

    public ExecutorList() {
        this.executorList = new ArrayList<>();
    }

    public ExecutorList(List<CallExecutor> executorList) {
        this.executorList = executorList;
    }

    /**
     * This will init the content of the template with a certain parseType
     * <li>template: The template to be parsed.</li>
     * <li>cd: will focus on a certain point of the mapActionKey.</li>
     * <li>parseType: the type of the comment tag</li>
     */

    public void add(final CallExecutor executor) {
        this.executorList.add(executor);
    }

    public boolean isEmpty() {
        if (this.size() == 0) {
            return true;
        }
        return false;
    }

    public int size() {
        return this.executorList.size();
    }

    public List<CallExecutor> getExecutorList() {
        return executorList;
    }

    public String execute(final EO adapter) {
        return execute(adapter, new HashMap<>());
    }

    public String execute(EO adapter, final Map<String, String> externalAttributes) {
        if (adapter == null) {
            adapter.error("Null adapter!");
            return "";
        }
        if (this.isEmpty()) {
            LOG.warn("Empty executor list");
            return "!!! Empty executor list!!! ";
        }
        StringBuilder result = new StringBuilder();
        for (CallExecutor executor : this.executorList) {
            if (executor == null) {
                continue;
            }
            try {
                result.append(executor.execute(adapter));
            } catch (Exception e) {
                adapter.error(e.getMessage());
            }
        }
        return result.toString();
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder("[\n");
        int counter = 0;
        for (CallExecutor executor : executorList) {
            counter++;
            builder.append(executor.toString());
            if (counter != executorList.size()) {
                builder.append(",");
            }
            builder.append("\n");
        }
        builder.append("]");
        return builder.toString();
    }

}
