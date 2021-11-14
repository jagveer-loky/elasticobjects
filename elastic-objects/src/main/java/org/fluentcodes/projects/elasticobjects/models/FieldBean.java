package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/*=>{javaHeader}|*/

/**
 * The basic bean container class for the configuration class {@link FieldConfig}. Also used as a base for building source code.
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 09 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 04:43:19 CET 2021
 */
public class FieldBean extends ConfigBean implements FieldBeanInterface {
    /*=>{}.*/

    /*=>{javaInstanceVars}|*/
    /* fieldKey */
    private String fieldKey;
    /* Length of a field. */
    private Integer length;
    /* A string representation for a list of modelsConfig. */
    private String modelKeys;
    /*=>{}.*/
    private boolean merged = false;

    private ModelConfigInterface parentModel;

    public FieldBean() {
        super();
    }

    public FieldBean(String key) {
        super(key);
        //this.fieldKey = key;
    }

    public FieldBean(final Map values) {
        super();
        merge(values);
    }

    public FieldBean(final String key, final Map values) {
        this(values);
        if (!hasNaturalId()) setNaturalId(key);
    }

    public FieldBean(final Field field) {
        Class modelClass = field.getDeclaringClass();
        Class typeClass = field.getType();
        setFieldKey(field.getName());
        setNaturalId(modelClass.getSimpleName() + "." + field.getName());
        setModelKeys(typeClass.getSimpleName());
    }

    public FieldBean(final FieldConfig config) {
        super(config);
        setFieldKey(config.getFieldKey());
        setLength(config.getLength());
        setModelKeys(config.getModelKeys());
    }

    protected FieldBean(final FieldBeanInterface fieldBean) {
        super();
        this.merge((FieldBean) fieldBean);
        setSuper(true);
    }

    public FieldBean get() {
        return this;
    }

    public void merge(final Map configMap) {
        super.merge(configMap);
        setNaturalId((String) configMap.get(NATURAL_ID));
        setFieldKey((String) configMap.get(FIELD_KEY));
        if (!hasFieldKey()) defaultFieldKey();
        mergeFinal(configMap.get(FINAL));
        mergeOverride(configMap.get(OVERRIDE));
        mergeJsonIgnore(configMap.get(JSON_IGNORE));
        mergeTransient(configMap.get(TRANSIENT));
        mergeDefault(configMap.get(DEFAULT));

        setLength(ScalarConverter.toInt(configMap.get(LENGTH)));
        setModelKeys((String) configMap.get(MODEL_KEYS));
    }

    public void merge(final FieldBean fieldBean) {
        super.merge(fieldBean);
        mergeFieldKey(fieldBean.getFieldKey());
        mergeModelKeys(fieldBean.getModelKeys());
        mergeLength(fieldBean.getLength());
        mergeFinal(fieldBean.getFinal());
        mergeProperty(fieldBean.getProperty());
        mergeFieldName(fieldBean.getFieldName());
        mergeGenerated(fieldBean.getGenerated());
        this.merged = true;
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

    public List<String> getModelList() {
        if (!hasModelKeys()) {
            return new ArrayList<>();
        }
        return Arrays.asList(modelKeys.split(","));
    }

    /*=>{javaAccessors}|*/
    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    @Override
    public FieldBean setFieldKey(final String fieldKey) {
        this.fieldKey = fieldKey;
        return this;
    }

    @Override
    public Integer getLength() {
        return this.length;
    }

    @Override
    public FieldBean setLength(final Integer length) {
        this.length = length;
        return this;
    }

    @Override
    public String getModelKeys() {
        return this.modelKeys;
    }

    @Override
    public FieldBean setModelKeys(final String modelKeys) {
        this.modelKeys = modelKeys;
        return this;
    }

    /*=>{}.*/
    public boolean isMerged() {
        return merged;
    }

    void setMerged(boolean merged) {
        this.merged = merged;
    }

    @Override
    public String toString() {
        StringBuilder builder = new StringBuilder();
        if (!hasKey()) return "";
        builder.append(getKey());
        if (this.hasParentModelKey()) builder.insert(0, getParentModel().getModelKey() + ".");
        return hasModelKeys() ?
                "(" + modelKeys + ")" + builder.toString() :
                builder.toString();
    }

    @Override
    public ModelConfigInterface getParentModel() {
        return parentModel;
    }

    @Override
    public void setParentModel(ModelConfigInterface modelBean) {
        this.parentModel = modelBean;
    }

    private void mergeGenerated(final Object value) {
        if (value == null) return;
        if (hasGenerated()) return;
        setGenerated(ScalarConverter.toBoolean(value));
    }

    private void mergeJavascriptType(final Object value) {
        if (value == null) return;
        if (hasJavascriptType()) return;
        setJavascriptType(ScalarConverter.toString(value));
    }

    private void mergeJsonIgnore(final Object value) {
        if (value == null) return;
        if (hasJsonIgnore()) return;
        setJsonIgnore(ScalarConverter.toBoolean(value));
    }

    private void mergeMax(final Object value) {
        if (value == null) return;
        if (hasMax()) return;
        setMax(ScalarConverter.toInteger(value));
    }

    private void mergeMin(final Object value) {
        if (value == null) return;
        if (hasMin()) return;
        setMin(ScalarConverter.toInteger(value));
    }

    private void mergeNotNull(final Object value) {
        if (value == null) return;
        if (hasNotNull()) return;
        setNotNull(ScalarConverter.toBoolean(value));
    }

    private void mergeProperty(final Object value) {
        if (value == null) return;
        if (hasProperty()) return;
        setProperty(ScalarConverter.toBoolean(value));
    }

    private void mergeStaticName(final Object value) {
        if (value == null) return;
        if (hasStaticName()) return;
        setStaticName(ScalarConverter.toBoolean(value));
    }

    private void mergeSuper(final Object value) {
        if (value == null) return;
        if (hasSuper()) return;
        setSuper(ScalarConverter.toBoolean(value));
    }

    private void mergeTransient(final Object value) {
        if (value == null) return;
        if (hasTransient()) return;
        setTransient(ScalarConverter.toBoolean(value));
    }

    private void mergeUnique(final Object value) {
        if (value == null) return;
        if (hasUnique()) return;
        setUnique(ScalarConverter.toBoolean(value));
    }

    private void mergeOverride(final Object value) {
        if (value == null) return;
        if (hasOverride()) return;
        setOverride(ScalarConverter.toBoolean(value));
    }

    private void mergeModelKeys(final Object value) {
        if (value == null) return;
        if (hasModelKeys()) return;
        setModelKeys(ScalarConverter.toString(value));
    }

    private void mergeLength(final Object value) {
        if (value == null) return;
        if (hasLength()) return;
        setLength(ScalarConverter.toInteger(value));
    }

    private void mergeFinal(final Object value) {
        if (value == null) return;
        if (hasFinal()) return;
        setFinal(ScalarConverter.toBoolean(value));
    }

    private void mergeFieldName(final Object value) {
        if (value == null) return;
        if (hasFieldName()) return;
        setFieldName(ScalarConverter.toString(value));
    }

    private void mergeFieldKey(final Object value) {
        if (value == null) return;
        if (hasFieldKey()) return;
        setFieldKey(ScalarConverter.toString(value));
    }

    private void defaultOverride() {
        if (hasOverride()) {
            return;
        }
        getProperties().put(OVERRIDE, false);
    }

    private void defaultSuper() {
        getProperties().put(SUPER, false);
    }


    private void defaultStaticName() {
        getProperties().put(SUPER, true);
    }

    private void defaultGenerated() {
        setGenerated(false);
    }


    private void defaultUnique() {
        setUnique(false);
    }

    private void defaultFieldKey() {
        if (hasFieldKey()) return;
        if (!hasNaturalId()) throw new EoException("Field with neither fieldKey nor naturalId is set");
        setFieldKey(getNaturalId());
    }

    private void defaultNotNull() {
        setNotNull(false);
    }


    private void defaultTransient() {
        setTransient(false);
    }

    private void defaultDefault() {
        setDefault(false);
    }

    private void defaultFinal() {
        if (hasFinal()) {
            return;
        }
        getProperties().put(FINAL, false);
    }
}
