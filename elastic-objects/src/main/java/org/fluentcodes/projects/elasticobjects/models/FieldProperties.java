package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;


public interface FieldProperties extends Config {
    String DEFAULT_VALUE = "defaultValue";
    String NOT_NULL = "notNull";
    String UNIQUE = "unique";
    String FIELD_NAME = "fieldName";
    String JOIN_INVERSE = "joinInverse";
    String JOIN = "join";
    String HIBERNATE = "hibernate";
    String MAP_KEY = "mapKey";

    default boolean hasUnique() {
        return getUnique() != null;
    }

    default Boolean getUnique() {
        return hasProperties() ? (Boolean) ScalarConverter.toBoolean(getProperties().get(UNIQUE)) : null;
    }

    default Boolean isUnique() {
        return hasProperties() ? (Boolean) ScalarConverter.toBoolean(getProperties().get(UNIQUE)) : null;
    }

    default boolean hasNotNull() {
        return getNotNull() != null;
    }

    default Boolean getNotNull() {
        return hasProperties() ? (Boolean) ScalarConverter.toBoolean(getProperties().get(NOT_NULL)) : null;
    }

    default Boolean isNotNull() {
        return hasProperties() ? (Boolean) ScalarConverter.toBoolean(getProperties().get(NOT_NULL)) : null;
    }

    default boolean hasFieldName() {
        return getFieldName() != null && !getFieldName().isEmpty();
    }

    default String getFieldName() {
        return hasProperties() ? (String) getProperties().get(FIELD_NAME) : null;
    }
}

