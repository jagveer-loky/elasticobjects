package org.fluentcodes.projects.elasticobjects.calls.generate.java.helper;

import org.fluentcodes.projects.elasticobjects.models.FieldConfig;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;

public class FieldBean implements FieldBeanProperties {
    private final Map<String, Object> properties;
    public FieldBean(String key) {
        properties = new HashMap<>();
        properties.put(NATURAL_ID, key);
    }

    public FieldBean(String key, Map values) {
        properties = values;
        setNaturalId((String)values.get(NATURAL_ID));
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    public void merge(final FieldConfig fieldConfig) {
        setNaturalId(fieldConfig.getNaturalId());
        setFieldKey(fieldConfig.getFieldKey());
        setFieldName(fieldConfig.hasFieldName() ? fieldConfig.getFieldName() : getFieldKey());
        //setModels(fieldConfig.getModels());
        setModelKeys(fieldConfig.getModelKeys());
        setLength(fieldConfig.getLength());
        setDescription(fieldConfig.getDescription());
        // default values for templates
        setFieldKey();
        setFieldName();
        setOverride();
        setLength();
        setFinal();


    }

}
