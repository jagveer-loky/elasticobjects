package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.FIELD_KEY;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.LENGTH;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfig.MODEL_KEYS;

public class FieldBean extends ConfigBean implements FieldBeanInterface {
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
        super();
        merge(values);
    }

    public FieldBean(final Field field) {
        Class modelClass = field.getDeclaringClass();
        Class typeClass = field.getType();
        setFieldKey(field.getName());
        setNaturalId(modelClass.getSimpleName() + "." + field.getName());
        setModelKeys(typeClass.getSimpleName());
    }

    protected FieldBean(final FieldBeanInterface fieldBean) {
        super();
        this.merge((FieldBean) fieldBean);
        setSuper(true);
    }
    public FieldBean get() {
        return this;
    }

    public void merge(final Map values) {
        super.merge(values);
        setNaturalId((String)values.get(NATURAL_ID));
        setFieldKey((String)values.get(FIELD_KEY));
        if (!hasFieldKey()) defaultFieldKey();
        mergeFinal(values.get(FINAL));
        mergeOverride(values.get(OVERRIDE));
        mergeJsonIgnore(values.get(JSON_IGNORE));
        mergeTransient(values.get(TRANSIENT));
        mergeDefault(values.get(DEFAULT));

        setLength(ScalarConverter.toInt(values.get(LENGTH)));
        setModelKeys((String)values.get(MODEL_KEYS));
    }

    public void merge(final FieldBean fieldBean) {
        super.merge(fieldBean);
        mergeFieldKey(fieldBean.getFieldKey());
        mergeModelKeys(fieldBean.getModelKeys());
        mergeLength(fieldBean.getLength());
        mergeFieldName(fieldBean.getFieldName());
        mergeGenerated(fieldBean.getGenerated());
        mergeDefaultValue(fieldBean.getDefaultValue());
    }

    public void defaultValues() {
        // default values for templates
        defaultOverride();
        defaultNotNull();
        defaultGenerated();
        defaultFinal();
        defaultTransient();
        defaultUnique();
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

    public List<String> getModelList() {
        if (!hasModelKeys()) {
            return new ArrayList<>();
        }
        return Arrays.asList(modelKeys.split(","));
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
