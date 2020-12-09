package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;


public interface FieldProperties extends ConfigProperties {
    String DEFAULT_VALUE = "defaultValue";
    String NOT_NULL = "notNull";
    String UNIQUE = "unique";
    String FIELD_NAME = "fieldName";
    String GENERATED = "generated";
    String JOIN_INVERSE = "joinInverse";
    String JOIN = "join";
    String HIBERNATE = "hibernate";
    String MAP_KEY = "mapKey";
    String TRANSIENT = "transient";
    String MIN = "min";
    String MAX = "max";
    String OVERRIDE = "override";
    String FINAL = "final";

    Object getDefaultValue();
    default boolean hasDefaultValue() {
        return getDefaultValue()!=null;
    }

    default boolean hasFieldKey() {
        return getFieldKey()!=null && !getFieldKey().isEmpty();
    }
    String getFieldKey();

    Integer getLength();
    default boolean hasLength() {
        return getLength()!=null;
    }

    String getModelKeys();
    default boolean hasModelKeys() {
        return getModelKeys()!=null && !getModelKeys().isEmpty();
    }

    default boolean hasUnique() {
        return getUnique() != null;
    }

    default Boolean getUnique() {
        return hasProperties() ? ScalarConverter.asBoolean(getProperties().get(UNIQUE)) : null;
    }

    default Boolean isUnique() {
        return getUnique();
        //return getUnique() == null ? false : getUnique();
    }

    default boolean hasNotNull() {
        return getNotNull() != null;
    }

    default Boolean getNotNull() {
        return hasProperties() ? ScalarConverter.asBoolean(getProperties().get(NOT_NULL)) : null;
    }

    default Boolean isNotNull() {
        return getNotNull();
        //return getUnique() == null ? false : getUnique();
    }

    default boolean hasGenerated() {
        return getGenerated() != null;
    }

    default Boolean getGenerated() {
        return hasProperties() ? ScalarConverter.asBoolean(getProperties().get(GENERATED)) : null;
    }

    default Boolean isGenerated() {
        return hasProperties() ? ScalarConverter.asBoolean(getProperties().get(GENERATED)) : null;
    }

    default boolean hasFieldName() {
        return getFieldName() != null && !getFieldName().isEmpty();
    }

    default String getFieldName() {
        return hasProperties() ? (String) getProperties().get(FIELD_NAME) : null;
    }

    default boolean hasMax() {
        return getMax() != null && getMax()>-1;
    }

    default Integer getMax() {
        return hasProperties() ? ScalarConverter.toInt(getProperties().get(MAX)) : null;
    }

    default boolean hasMin() {
        return getMin() != null && getMin()>-1;
    }

    default Integer getMin() {
        return hasProperties() ? ScalarConverter.toInt(getProperties().get(MIN)) : null;
    }

    default boolean hasSize() {
        return hasMax()||hasMin();
    }

    default boolean hasTransient() {
        return getTransient() != null;
    }
    default boolean isTransient() {
        return getTransient() != null && getTransient();
    }
    default Boolean getTransient() {
        return hasProperties() ? ScalarConverter.asBoolean(getProperties().get(TRANSIENT)) : null;
    }
}


