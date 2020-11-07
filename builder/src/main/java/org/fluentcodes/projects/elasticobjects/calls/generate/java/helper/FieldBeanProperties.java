package org.fluentcodes.projects.elasticobjects.calls.generate.java.helper;

import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldProperties;

import static org.fluentcodes.projects.elasticobjects.domain.Base.DESCRIPTION;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfigProperties.FINAL;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfigProperties.OVERRIDE;
import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.LENGTH;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.MODEL_KEYS;

public interface FieldBeanProperties extends FieldProperties {

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

    default boolean hasNaturalId() {
        return getNaturalId()!=null && !getNaturalId().isEmpty();
    }


    default void setNaturalId (String value) {
        if (hasNaturalId()) {
            return;
        }
        getProperties().put(NATURAL_ID, value );
    }

    default String getNaturalId () {
        return (String) getProperties().get(NATURAL_ID);
    }


    default boolean hasFieldKey() {
        return getFieldKey()!=null && !getFieldKey().isEmpty();
    }

    default void setFieldKey (String value) {
        if (hasFieldKey()) {
            return;
        }
        getProperties().put(FIELD_KEY, value );
    }

    default void setFieldKey () {
        if (hasFieldKey()) {
            return;
        }
        getProperties().put(FIELD_KEY, getNaturalId() );
    }

    default String getFieldKey () {
        return (String) getProperties().get(FIELD_KEY);
    }


    default void setFieldName (String value) {
        if (hasFieldName()) {
            return;
        }
        getProperties().put(FIELD_NAME, value );
    }

    default void setFieldName () {
        if (hasFieldName()) {
            return;
        }
        getProperties().put(FIELD_NAME, getFieldKey() );
    }

    default boolean hasOverride() {
        return getOverride()!=null&&getOverride();
    }

    default Boolean getOverride () {
        return (Boolean) getProperties().get(OVERRIDE);
    }

    default void setOverride (String value) {
        if (hasOverride()) {
            return;
        }
        getProperties().put(OVERRIDE, "true".equals(value) );
    }

    default void setOverride (Boolean value) {
        if (hasOverride()) {
            return;
        }
        getProperties().put(OVERRIDE, value );
    }

    default void setOverride () {
        if (hasOverride()) {
            return;
        }
        getProperties().put(OVERRIDE, false );
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



    default boolean hasDescription() {
        return getDescription()!=null && !getDescription().isEmpty();
    }

    default String getDescription () {
        return (String) getProperties().get(DESCRIPTION);
    }

    default void setDescription (String value) {
        if (hasDescription()) {
            return;
        }
        getProperties().put(DESCRIPTION, value );
    }

    default boolean hasModelKeys() {
        return getModelKeys()!=null && !getModelKeys().isEmpty();
    }

    default String getModelKeys () {
        return (String) getProperties().get(MODEL_KEYS);
    }

    default void setModelKeys (String value) {
        if (hasModelKeys()) {
            return;
        }
        getProperties().put(MODEL_KEYS, value );
    }

    default void setUnique (Boolean value) {
        if (hasUnique()) {
            return;
        }
        getProperties().put(UNIQUE, value );
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

    default void setFinal () {
        if (hasFinal()) {
            return;
        }
        getProperties().put(FINAL, false );
    }

    default Boolean getFinal () {
        return (Boolean) getProperties().get(FINAL);
    }


    default boolean hasLength() {
        return getLength()!=null;
    }

    default void setLength (Integer value) {
        if (hasLength()) {
            return;
        }
        getProperties().put(LENGTH, value );
    }
    default void setLength () {
        if (hasLength()) {
            return;
        }
        getProperties().put(LENGTH, -1 );
    }

    default Integer getLength () {
        return (Integer) getProperties().get(LENGTH);
    }
}
