package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.SUPER;


public interface FieldConfigInterface extends ConfigConfigInterface {
    String DEFAULT_VALUE = "defaultValue";
    String NOT_NULL = "notNull";
    String UNIQUE = "unique";
    String FIELD_NAME = "fieldName";
    String GENERATED = "generated";
    String JOIN_INVERSE = "joinInverse";
    String JSON_IGNORE = "jsonIgnore";
    String JOIN = "join";
    String HIBERNATE = "hibernate";
    String MAP_KEY = "mapKey";
    String TRANSIENT = "transient";
    String MIN = "min";
    String MAX = "max";
    String OVERRIDE = "override";
    String FINAL = "final";
    String DEFAULT = "default";

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
        return hasTransient() && getTransient();
    }
    default Boolean getTransient() {
        return (Boolean)getProperties().get(TRANSIENT);
    }
    default boolean hasJsonIgnore() {
        return getJsonIgnore() != null;
    }
    default boolean isJsonIgnore() {
        return hasJsonIgnore() && getJsonIgnore();
    }
    default Boolean getJsonIgnore() {
        return (Boolean)getProperties().get(JSON_IGNORE);
    }

    default boolean hasSuper() {
        return getSuper() != null;
    }
    default boolean isSuper() {
        return getSuper();
    }
    default Boolean getSuper() {
        return (Boolean)getProperties()
                .get(SUPER);
    }

    default Boolean isFinal() {
        return (hasFinal()&&getFinal())||false;
    }
    default Boolean getFinal () {
        return (Boolean) getProperties().get(FINAL);
    }
    default boolean hasFinal() {
        return getFinal()!=null;
    }

    default Boolean getDefault () {
        return (Boolean) getProperties().get(DEFAULT);
    }

    default Boolean isDefault() {
        return (hasDefault()&&getDefault())||false;
    }
    default boolean hasDefault() {
        return getDefault()!=null;
    }

}


