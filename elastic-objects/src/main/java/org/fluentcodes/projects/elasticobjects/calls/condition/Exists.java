package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;

import java.util.List;
import java.util.Map;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class Exists implements Condition {
    private static final Logger LOG = LogManager.getLogger(Exists.class);
    private final String key;

    public Exists(String key) {
        this.key = key;
    }

    public String getKey() {
        return key;
    }

    public Object getValue() {
        return null;
    }

    public boolean compare(Object object) {
        //TODO
        return true;
    }

    public String createQuery(Map<String, Object> keyValues) {
        //TODO
        return "";
    }

    public String createCondition() {
        StringBuilder builder = new StringBuilder();
        return builder.toString();
    }

    public boolean filter(List row) {
        if (row == null) {
            LOG.warn("Null row should not occure!");
            return false;
        }
        try {
            Integer i = Integer.parseInt(key);
            if (i < row.size()) {
                return row.get(i) != null;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public boolean filter(EO adapter) {
        if (adapter == null) {
            LOG.warn("Null adapter should not occure!");
            return false;
        }
        try {
            return adapter.get(key) != null;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        builder.append(this.key);
        return builder.toString();
    }
}
