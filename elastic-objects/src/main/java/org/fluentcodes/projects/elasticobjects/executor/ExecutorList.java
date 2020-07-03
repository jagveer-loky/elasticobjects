package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.eo.EO;

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
    private final List<Executor> executorList;

    public ExecutorList() {
        this.executorList = new ArrayList<>();
    }

    public ExecutorList(List<Executor> executorList) {
        this.executorList = executorList;
    }

    /**
     * This will init the content of the template with a certain parseType
     * <li>template: The template to be parsed.</li>
     * <li>cd: will focus on a certain point of the mapActionKey.</li>
     * <li>parseType: the type of the comment tag</li>
     */

    public void add(final Executor executor) {
        this.executorList.add(executor);
    }

    public void add(final Map attributes)  {
        String execute = null;
        if (attributes.get(Executor.EXECUTE) != null) {
            execute = (String) attributes.get(Executor.EXECUTE);
        } else {
            throw new EoException("Map for executor call has no " + Executor.EXECUTE + "! Skip adding call");
        }
        if (execute.contains("Call.")) {
            this.executorList.add(new CallExecutor(attributes));
        } else if (execute.startsWith("Value")) {
            this.executorList.add(new ExecutorValues(attributes));
        } else {
            throw new EoException("Naming convention for calling is ^Value|Call. Executor value is " + execute);
        }
    }


    public void addContentTemplateAction(final String content, Map parentAttributes)  {
        if (content == null || content.isEmpty()) {
            return;
        }
        Map attributes = new HashMap();
        attributes.putAll(parentAttributes);
        attributes.put(Executor.EXECUTE, "TemplateCall.execute(content)");
        attributes.put("content", content);
        add(attributes);
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

    public List<Executor> getExecutorList() {
        return executorList;
    }

    public List<Map> getListMap() {
        List<Map> listOfAttributes = new ArrayList<>();
        for (Executor executor : executorList) {
            listOfAttributes.add(executor.getAttributes());
        }
        return listOfAttributes;
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
        for (Executor element : this.executorList) {
            if (element == null) {
                continue;
            }
            try {
                result.append(element.execute(adapter, externalAttributes));
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
        for (Executor executor : executorList) {
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
