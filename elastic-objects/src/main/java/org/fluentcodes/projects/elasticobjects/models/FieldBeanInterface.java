package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.JavascriptFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

public interface FieldBeanInterface extends Config, FieldConfigInterface {
    public static final String JAVASCRIPT_TYPE = "javascriptType";
    public static final String SUPER = "super";
    void setModelBean(ModelBean modelBean);
    ModelBean getModelBean();

    default void setSuper() {
        getProperties().put(SUPER, true);
    }

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
        if (value == null) return;
        if (hasFieldKey()) return;
        setFieldKey(ScalarConverter.toString(value));
    }
    default void defaultFieldKey() {
        if (hasFieldKey()) return;
        if (!hasNaturalId()) throw new EoException("Field with neither fieldKey nor naturalId is set");
        setFieldKey(getNaturalId());
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

    default void mergeOverride (Object value) {
        if (value == null) {
            return;
        }
        if (hasOverride()) {
            return;
        }
        getProperties().put(OVERRIDE, ScalarConverter.toBoolean(value));
    }


    default void defaultOverride() {
        if (hasOverride()) {
            return;
        }
        getProperties().put(OVERRIDE, false );
    }

    default void setSuper (Boolean value) {
        getProperties().put(SUPER, value );
    }

    default void defaultSuper() {
        getProperties().put(SUPER, false );
    }

    default void mergeJsonIgnore (Object value) {
        if (value == null) {
            return;
        }
        if (hasJsonIgnore()) {
            return;
        }
        if (value instanceof Boolean) {
            getProperties().put(JSON_IGNORE, value);
        }
        if (value instanceof String) {
            getProperties().put(JSON_IGNORE, "true".equals(value));
        }
    }

    default void setJsonIgnore (Boolean value) {
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

    default void defaultGenerated() {
        setGenerated(false);
    }

    default void setUnique (Boolean value) {
        if (hasUnique()) {
            return;
        }
        getProperties().put(UNIQUE, value );
    }

    default void defaultUnique() {
        setUnique(false);
    }

    default void setNotNull (Boolean value) {
        if (hasNotNull()) {
            return;
        }
        getProperties().put(NOT_NULL, value );
    }

    default void defaultNotNull() {
        setNotNull(false);
    }

    default void setTransient (Boolean value) {
        getProperties().put(TRANSIENT, value );
    }

    default void defaultTransient() {
        setTransient(false);
    }

    default void mergeTransient (Object value) {
        if (value == null) {
            return;
        }
        if (hasTransient()) {
            return;
        }
        getProperties().put(TRANSIENT, ScalarConverter.toBoolean(value));
    }

    default void setDefault (Boolean value) {
        getProperties().put(DEFAULT, value );
    }

    default void defaultDefault() {
        setDefault(false);
    }

    default void mergeDefault (Object value) {
        if (value == null) {
            return;
        }
        if (hasDefault()) {
            return;
        }
        getProperties().put(DEFAULT, ScalarConverter.toBoolean(value));
    }

    default void setFinal (Boolean value) {
        getProperties().put(FINAL, value );
    }

    default void mergeFinal (Object value) {
        if (value == null) {
            return;
        }
        if (hasFinal()) {
            return;
        }
        getProperties().put(FINAL, ScalarConverter.toBoolean(value));
    }

    default void defaultFinal() {
        if (hasFinal()) {
            return;
        }
        getProperties().put(FINAL, false );
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
