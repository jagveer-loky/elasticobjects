package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.JavaFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.calls.generate.javascript.JavascriptFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringUpperFirstCharCall;
import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

public interface FieldBeanProperties extends FieldProperties, Base {
    public static final String JAVASCRIPT_TYPE = "javascriptType";
    public static final String SUPER = "super";
    void setModelBean(ModelBean modelBean);
    ModelBean getModelBean();
    void setDefaultValue(Object value);
    default void mergeDefaultValue(Object value) {
        if (value == null) {
            return;
        }
        if (hasDefaultValue()) {
            return;
        }
        setDefaultValue(value);
    }

    void setFieldKey(String fieldKey);
    default void mergeFieldKey(Object value) {
        if (value == null) {
            return;
        }
        if (hasFieldKey()) {
            return;
        }
        setFieldKey(ScalarConverter.toString(value));
    }

    void setLength(Integer length);
    default void mergeLength(Object value) {
        if (value == null) {
            return;
        }
        if (hasLength()) {
            return;
        }
        setLength(ScalarConverter.toInt(value));
    }

    void setModelKeys(String modelKeys);
    default void mergeModelKeys(Object value) {
        if (value == null) {
            return;
        }
        if (hasModelKeys()) {
            return;
        }
        setModelKeys(ScalarConverter.toString(value));
    }

    default Models getModels(EO eo) {
        return new Models(eo.getConfigsCache(), getModelKeys());
    }

    default boolean hasField(String key) {
        return getFieldValue(key)!=null;
    }

    default Object getFieldValue(final String key) {
        return getProperties().get(key);
    }

    default void setByConfig(FieldConfig config) {
        if (!hasField(config.getNaturalId())) {
            return;
        }
        /*
        mapField.put(FIELD_KEY, config.getFieldKey());
        mapField.put(EXPOSE, config.getExpose());
        mapField.put(LENGTH, config.getLength());
        mapField.put(DESCRIPTION, config.getDescription());
        mapField.put(NOT_NULL, config.getNotNull());
        mapField.put(UNIQUE, config.getUnique());
        */
    }

    default void setFieldName (String value) {
        getProperties().put(FIELD_NAME, value );
    }

    default void mergeFieldName (Object value) {
        if (value == null) {
            return;
        }
        if (hasFieldName()) {
            return;
        }
        setFieldName(ScalarConverter.toString(value));
    }

    default boolean hasOverride() {
        return getOverride()!=null && getOverride();
    }

    default Boolean getOverride () {
        return (Boolean) getProperties().get(OVERRIDE);
    }
    default Boolean isOverride () {
        return getOverride();
    }
    default void setOverride (String value) {
        getProperties().put(OVERRIDE, "true".equals(value) );
    }

    default void setOverride (Boolean value) {
        getProperties().put(OVERRIDE, value );
    }

    default void setOverrideDefault() {
        if (hasOverride()) {
            return;
        }
        getProperties().put(OVERRIDE, false );
    }

    default Boolean getSuper () {
        return (Boolean) getProperties().get(SUPER);
    }
    default Boolean isSuper () {
        return getSuper();
    }
    default void setSuper (Boolean value) {
        getProperties().put(SUPER, value );
    }
    default void defaultSuper() {
        getProperties().put(SUPER, false );
    }

    default boolean hasJsonIgnore() {
        return getJsonIgnore()!=null && getJsonIgnore();
    }
    
    default Boolean getJsonIgnore () {
        return (Boolean) getProperties().get(OVERRIDE);
    }

    default void setJsonIgnore (String value) {
        if (hasJsonIgnore()) {
            return;
        }
        getProperties().put(OVERRIDE, "true".equals(value) );
    }

    default void setJsonIgnore (Boolean value) {
        if (hasJsonIgnore()) {
            return;
        }
        getProperties().put(OVERRIDE, value );
    }

    default void setGenerated (Boolean value) {
        if (hasGenerated()) {
            return;
        }
        getProperties().put(GENERATED, value );
    }

    default void mergeGenerated(Object value) {
        if (value == null) {
            return;
        }
        if (hasGenerated()) {
            return;
        }
        setGenerated(ScalarConverter.asBoolean(value));
    }

    default void setGeneratedDefault () {
        setGenerated(false);
    }

    default void setUnique (Boolean value) {
        if (hasUnique()) {
            return;
        }
        getProperties().put(UNIQUE, value );
    }

    default void setUniqueDefault () {
        setUnique(false);
    }

    default void setNotNull (Boolean value) {
        if (hasNotNull()) {
            return;
        }
        getProperties().put(NOT_NULL, value );
    }

    default void setNotNullDefault() {
        setNotNull(false);
    }

    default void setTransient (Boolean value) {
        if (hasNotNull()) {
            return;
        }
        getProperties().put(TRANSIENT, value );
    }

    default void setTransientDefault() {
        setTransient(false);
    }

    default boolean hasFinal() {
        return getFinal()!=null;
    }

    default void setFinal (Boolean value) {
        if (hasFinal()) {
            return;
        }
        getProperties().put(FINAL, value );
    }

    default void setFinalDefault() {
        if (hasFinal()) {
            return;
        }
        getProperties().put(FINAL, false );
    }

    default Boolean getFinal () {
        return (Boolean) getProperties().get(FINAL);
    }
    default Boolean isFinal () {
        return getFinal();
    }

    default void setMax () {
        if (hasMax()) {
            return;
        }
        getProperties().put(MAX, -1 );
    }

    default void setMax (Integer max) {
        if (hasMax()) {
            return;
        }
        getProperties().put(MAX, max );
    }

    default void setMIN () {
        if (hasMax()) {
            return;
        }
        getProperties().put(MIN, -1 );
    }

    default void setMin (Integer min) {

        getProperties().put(MIN, min );
    }

    default void mergeMin(Object value) {
        if (value == null) {
            return;
        }
        if (hasMax()) {
            return;
        }
        setMin(ScalarConverter.toInt(value));
    }

    default void setJavascriptType(String value) {
        getProperties().put(JAVASCRIPT_TYPE, value);
    }

    default void setJavascriptType() {
        getProperties().put(JAVASCRIPT_TYPE, JavascriptFieldTypeCall.createType(getModelKeys()));
    }

    default String getJavascriptType() {
        return (String) getProperties().get(JAVASCRIPT_TYPE);
    }
}
