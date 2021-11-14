package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.JavascriptFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

/*=>{javaHeader}|*/

/**
 * An interface for {@link FieldBean} with access methods for properties.
 *
 * @author Werner Diwischek
 * @creationDate Fri Nov 06 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 06:47:24 CET 2021
 */
public interface FieldBeanInterface extends FieldConfigInterface, ConfigBeanInterface {
    /*=>{}.*/
    boolean isMerged();

    /*=>{javaAccessors}|*/
    default FieldBeanInterface setDefault(Boolean value) {
        getProperties().put(DEFAULT, value);
        return this;
    }

    default void mergeDefault(final Object value) {
        if (value == null) return;
        if (hasDefault()) return;
        setDefault(ScalarConverter.toBoolean(value));
    }

    FieldBeanInterface setFieldKey(final String fieldKey);

    default FieldBeanInterface setFieldName(String value) {
        getProperties().put(FIELD_NAME, value);
        return this;
    }


    default FieldBeanInterface setFinal(Boolean value) {
        getProperties().put(FINAL, value);
        return this;
    }


    default FieldBeanInterface setGenerated(Boolean value) {
        getProperties().put(GENERATED, value);
        return this;
    }


    default FieldBeanInterface setJavascriptType(String value) {
        getProperties().put(JAVASCRIPT_TYPE, value);
        return this;
    }

    default FieldBeanInterface setJsonIgnore(Boolean value) {
        getProperties().put(JSON_IGNORE, value);
        return this;
    }

    FieldBeanInterface setLength(final Integer length);

    default FieldBeanInterface setMax(Integer value) {
        getProperties().put(MAX, value);
        return this;
    }


    default FieldBeanInterface setMin(Integer value) {
        getProperties().put(MIN, value);
        return this;
    }


    FieldBeanInterface setModelKeys(final String modelKeys);

    default FieldBeanInterface setNotNull(Boolean value) {
        getProperties().put(NOT_NULL, value);
        return this;
    }


    default FieldBeanInterface setOverride(Boolean value) {
        getProperties().put(OVERRIDE, value);
        return this;
    }

    default FieldBeanInterface setProperty(Boolean value) {
        getProperties().put(PROPERTY, value);
        return this;
    }


    default FieldBeanInterface setStaticName(Boolean value) {
        getProperties().put(STATIC_NAME, value);
        return this;
    }


    default FieldBeanInterface setSuper(Boolean value) {
        getProperties().put(SUPER, value);
        return this;
    }


    default FieldBeanInterface setTransient(Boolean value) {
        getProperties().put(TRANSIENT, value);
        return this;
    }


    default FieldBeanInterface setUnique(Boolean value) {
        getProperties().put(UNIQUE, value);
        return this;
    }


    /*=>{}.*/
    default Models getModels(EO eo) {
        return new Models(eo.getConfigsCache(), getModelKeys());
    }

    default boolean hasField(String key) {
        return getFieldValue(key) != null;
    }

    default Object getFieldValue(final String key) {
        return getProperties().get(key);
    }

    /* no null */

    default void setOverride(String value) {
        getProperties().put(OVERRIDE, "true".equals(value));
    }


    default void setMax() {
        if (hasMax()) {
            return;
        }
        getProperties().put(MAX, -1);
    }


    default void setJavascriptType() {
        getProperties().put(JAVASCRIPT_TYPE, JavascriptFieldTypeCall.createType(getModelKeys()));
    }

    void setParentModel(ModelConfigInterface modelBean);


}
