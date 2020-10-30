package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

public interface FieldProperties extends ModuleProperties {
    String DEFAULT_VALUE = "defaultValue";
    String NOT_NULL = "notNull";
    String UNIQUE = "unique";
    String FIELD_NAME = "fieldName";
    String JOIN_INVERSE = "joinInverse";
    String JOIN = "join";
    String HIBERNATE = "hibernate";
    String TABLE = "table";
    String MAP_KEY = "mapKey";

    default boolean hasUnique() {
        return getUnique()!=null;
    }

    default Boolean getUnique() {
        return hasProperties()? (Boolean) ScalarConverter.toBoolean(getProperties().get(UNIQUE)) : null;
    }

    default void setUnique(final String value) {
        if (hasProperties()) {
            getProperties().put(UNIQUE, value);
        }
    }

    default boolean hasNotNull() {
        return getNotNull()!=null;
    }

    default Boolean getNotNull() {
        return hasProperties()? (Boolean) ScalarConverter.toBoolean(getProperties().get(NOT_NULL)) : null;
    }

    default void setNotNull(final String value) {
        if (hasProperties()) {
            getProperties().put(NOT_NULL, value);
        }
    }

    default boolean hasFieldName() {
        return getFieldName()!=null && !getFieldName().isEmpty();
    }

    default String getFieldName() {
        return hasProperties() ? (String) getProperties().get(FIELD_NAME) : null;
    }

    default void setFieldName(final String value) {
        if (hasProperties()) {
            getProperties().put(FIELD_NAME, value);
        }
    }
}
