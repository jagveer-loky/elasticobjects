package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by werner.diwischek on 19.02.18.
 */
public class DBFieldParams {
    private String fieldName;
    private Integer length;
    private Boolean unique;
    private Boolean notNull;
    private String hibernate;
    private String table;
    private String join;
    private String joinInverse;
    private String mapKey;
    private String defaultValue;

    public DBFieldParams() {}

    public DBFieldParams(Object object) {
        if (object == null || !(object instanceof Map)) {
            return;
        }
        Map map = (Map) object;
        this.fieldName = ScalarConverter.toString(map.get(F_FIELD_NAME));
        try {
            this.length = ScalarConverter.toInt(map.get(F_LENGTH));
        } catch (Exception e) {
            e.printStackTrace();
        }
        this.unique = ScalarConverter.toBoolean(map.get(F_UNIQUE));
        this.notNull = ScalarConverter.toBoolean(map.get(F_NOT_NULL));
        this.hibernate = ScalarConverter.toString(map.get(F_HIBERNATE));
        this.table = ScalarConverter.toString(map.get(F_TABLE));
        this.join = ScalarConverter.toString(map.get(F_JOIN));
        this.joinInverse = ScalarConverter.toString(map.get(F_JOIN_INVERSE));
        this.mapKey = ScalarConverter.toString(map.get(F_MAP_KEY));
        this.defaultValue = ScalarConverter.toString(map.get(F_DEFAULT_VALUE));
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Boolean isUnique() {
        return unique;
    }

    public void setUnique(Boolean unique) {
        this.unique = unique;
    }

    public Boolean isNotNull() {
        return notNull;
    }

    public void setNotNull(Boolean notNull) {
        this.notNull = notNull;
    }

    public String getHibernate() {
        return hibernate;
    }

    public void setHibernate(String hibernate) {
        this.hibernate = hibernate;
    }

    public String getTable() {
        return table;
    }

    public void setTable(String table) {
        this.table = table;
    }

    public String getJoin() {
        return join;
    }

    public void setJoin(String join) {
        this.join = join;
    }

    public String getJoinInverse() {
        return joinInverse;
    }

    public void setJoinInverse(String joinInverse) {
        this.joinInverse = joinInverse;
    }

    public String getMapKey() {
        return mapKey;
    }

    public void setMapKey(String mapKey) {
        this.mapKey = mapKey;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }
}
