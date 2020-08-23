package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class And {
    public static final String DELIMITER = "&&";
    private static final Logger LOG = LogManager.getLogger(And.class);
    private static final String SPLITTER = "\\&\\&";
    List<Condition> conditions;

    public And() {
        conditions = new ArrayList<>();
    }

    public And(EO adapter)  {
        this();
        Map keyValues = adapter.getKeyValues();
        for (Object key : keyValues.keySet()) {

            Condition condition = new Eq((String) key, keyValues.get(key));
            conditions.add(condition);
        }
    }

    public And(String andAsString) {
        this();
        if (andAsString == null || andAsString.isEmpty()) {
            return;
        }
        String[] andArray = andAsString.split(SPLITTER);
        for (String and : andArray) {
            Matcher matcher = Condition.ifPattern.matcher(and);
            if (!matcher.find()) {
                LOG.error("Could not find pattern in " + and);
                continue;
                //TODO log
            }
            String key = matcher.group(1);
            String operator = matcher.group(2).replaceAll("[\\s]*$", "");
            String value = matcher.group(3).replaceAll("[\\s]*$", "");
            switch (operator) {
                case Condition.EQ:
                    conditions.add(new Eq(key, value));
                    break;
                case Condition.EQUALS:
                    conditions.add(new Eq(key, value));
                    break;
                case Condition.LIKE:
                    conditions.add(new Like(key, value));
                    break;
                case Condition.MATCH:
                    conditions.add(new Match(key, value));
                    break;
                case Condition.EX:
                    conditions.add(new Exists(key));
                    break;
                case Condition.NEX:
                    conditions.add(new NotExists(key));
                    break;
                default:
                    LOG.error("Could not understand condition " + andAsString + " !!!!!!!!");
            }
        }
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public int size() {
        return conditions.size();
    }

    public Condition getCondition(int i) {
        if (i < conditions.size()) {
            return conditions.get(i);
        }
        return null;
    }

    public void addCondition(Condition condition) {
        if (conditions == null) {
            this.conditions = new ArrayList<>();
        }
        this.conditions.add(condition);
    }

    public void addConditions(And conditions) {
        if (conditions == null) {
            this.conditions = new ArrayList<>();
        }
        this.conditions.addAll(conditions.getConditions());
    }

    public String createQuery(Map<String, Object> keyValues) {
        StringBuilder sql = new StringBuilder("(");
        int fieldCounter = 0;
        for (Condition condition : conditions) {
            sql.append(condition.createQuery(keyValues));
            fieldCounter++;
            if (fieldCounter != conditions.size()) {
                sql.append(" and ");
            }
        }
        sql.append(")");
        return sql.toString();
    }

    public boolean filter(List row) {
        if (row == null) {
            LOG.warn("Null row should not occure!");
            return true;
        }
        boolean result = true;
        for (Condition condition : conditions) {
            result = result && condition.filter(row);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    public boolean filter(EO eo) {
        if (eo == null) {
            LOG.warn("Null adapter should not occure!");
            return true;
        }
        boolean result = true;
        for (Condition condition : conditions) {
            result = result && condition.filter(eo);
            if (!result) {
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        int counter = 0;
        for (Condition condition : conditions) {
            builder.append(condition.toString());
            counter++;
            if (counter < conditions.size()) {
                builder.append(" && ");
            }
        }
        return builder.toString();
    }
}
