package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.LENGTH;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.MODEL_KEYS;

public class FieldBean extends ConfigBean implements
        FieldBeanJavaMethods,
        FieldBeanJavaScriptMethods {
    private String fieldKey;
    private String modelKeys;
    private Integer length;
    private Object defaultValue;
    private ModelBean modelBean;

    public FieldBean() {
        super();
    }
    public FieldBean(String key) {
        super(key);
    }

    public FieldBean(final Map values) {
        super(values);
        setFieldKey((String)values.get(FIELD_KEY));
        setLength(ScalarConverter.toInt(values.get(LENGTH)));
        setModelKeys((String)values.get(MODEL_KEYS));
    }

    protected FieldBean(final FieldBean fieldBean) {
        super();
        merge(fieldBean);
        setSuper(true);
    }


    public void merge(final FieldBean fieldBean) {
        super.merge(fieldBean);
        mergeFieldKey(fieldBean.getFieldKey());
        mergeModelKeys(fieldBean.getModelKeys());
        mergeLength(fieldBean.getLength());
        mergeFieldName(fieldBean.getFieldName());
        mergeGenerated(fieldBean.getGenerated());
        mergeDefaultValue(fieldBean.getDefaultValue());
        // default values for templates
        setOverrideDefault();
        setNotNullDefault();
        setGeneratedDefault();
        setFinalDefault();
        setTransientDefault();
        setUniqueDefault();
        defaultSuper();
    }

    @Override
    public String getFieldKey() {
        return fieldKey;
    }

    @Override
    public void setFieldKey(String fieldKey) {
        this.fieldKey = fieldKey;
    }

    @Override
    public String getModelKeys() {
        return modelKeys;
    }

    @Override
    public void setModelKeys(String modelKeys) {
        this.modelKeys = modelKeys;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    @Override
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue = defaultValue;
    }

    @Override
    public Integer getLength() {
        return length;
    }

    @Override
    public void setLength(Integer length) {
        this.length = length;
    }

    @Override
    public String toString() {
        return "FieldBean(" + getNaturalId() + ")";
    }

    public ModelBean getModelBean() {
        return modelBean;
    }

    public void setModelBean(ModelBean modelBean) {
        this.modelBean = modelBean;
    }
}
