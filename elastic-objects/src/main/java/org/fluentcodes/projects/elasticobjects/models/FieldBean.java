package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.JavascriptFieldTypeCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
/*.{javaHeader}|*/

/**
 * The basic bean container class for the configuration class {@link FieldConfig}. Also used as a base for building source code.
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 09 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 04:43:19 CET 2021
 */
public class FieldBean extends ConfigBean implements FieldInterface {
    /*.{}.*/

    /*.{javaInstanceVars}|*/
    /* fieldKey */
    private String fieldKey;
    /* Length of a field. */
    private Integer length;
    /* A string representation for a list of modelsConfig. */
    private String modelKeys;
    /*.{}.*/
    private boolean merged = false;

    private ModelInterface parentModel;

    public FieldBean() {
        super();
    }

    public FieldBean(String key) {
        super(key);
    }

    public FieldBean(final Map values) {
        super();
        merge(values);
        defaultValues();
    }

    public FieldBean(final String key, final Map values) {
        this(values);
        if (!hasNaturalId()) setNaturalId(key);
    }

    public FieldBean(final Field field) {
        Class<?> modelClass = field.getDeclaringClass();
        Class<?> typeClass = field.getType();
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

    protected FieldBean(final FieldBean fieldBean) {
        super();
        this.merge((FieldBean) fieldBean);
        setSuper(true);
    }

    public FieldBean get() {
        return this;
    }

    @Override
    public void merge(final Map configMap) {
        super.merge(configMap);
        setNaturalId((String) configMap.get(F_NATURAL_ID));
        setFieldKey((String) configMap.get(F_FIELD_KEY));
        mergeFinal(configMap.get(F_FINAL));
        mergeOverride(configMap.get(F_OVERRIDE));
        mergeJsonIgnore(configMap.get(F_JSON_IGNORE));
        mergeTransient(configMap.get(F_TRANSIENT));
        mergeDefault(configMap.get(F_DEFAULT));

        setLength(ScalarConverter.toInt(configMap.get(F_LENGTH)));
        setModelKeys((String) configMap.get(F_MODEL_KEYS));
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

    private void defaultValues() {
        // default values for templates
        defaultOverride();
        defaultNotNull();
        defaultGenerated();
        defaultFinal();
        defaultTransient();
        defaultUnique();
        defaultSuper();
        defaultConfigModelKey();
    }

    public List<String> getModelList() {
        if (!hasModelKeys()) {
            return new ArrayList<>();
        }
        return Arrays.asList(modelKeys.split(","));
    }

    /*.{javaAccessors}|*/
    @Override
    public String getFieldKey() {
        return this.fieldKey;
    }

    public FieldBean setFieldKey(final String fieldKey) {
        this.fieldKey = fieldKey;
        return this;
    }

    @Override
    public Integer getLength() {
        return this.length;
    }

    public FieldBean setLength(final Integer length) {
        this.length = length;
        return this;
    }

    @Override
    public String getModelKeys() {
        return this.modelKeys;
    }


    public FieldBean setModelKeys(final String modelKeys) {
        this.modelKeys = modelKeys;
        return this;
    }

    /*.{}.*/
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
    public ModelInterface getParentModel() {
        return parentModel;
    }

    public void setParentModel(ModelInterface modelBean) {
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
        getProperties().put(F_OVERRIDE, false);
    }

    private void defaultSuper() {
        getProperties().put(F_SUPER, false);
    }

    private void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(FieldConfig.class.getSimpleName());
    }


    private void defaultStaticName() {
        getProperties().put(F_SUPER, true);
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
        getProperties().put(F_FINAL, false);
    }

    public FieldBean setDefault(Boolean value) {
        getProperties().put(F_DEFAULT, value);
        return this;
    }

    private void mergeDefault(final Object value) {
        if (value == null) return;
        if (hasDefault()) return;
        setDefault(ScalarConverter.toBoolean(value));
    }

    public FieldBean setFieldName(String value) {
        getProperties().put(F_FIELD_NAME, value);
        return this;
    }


    public FieldBean setFinal(Boolean value) {
        getProperties().put(F_FINAL, value);
        return this;
    }


    public FieldBean setGenerated(Boolean value) {
        getProperties().put(F_GENERATED, value);
        return this;
    }


    public FieldBean setJavascriptType(String value) {
        getProperties().put(F_JAVASCRIPT_TYPE, value);
        return this;
    }

    public FieldBean setJsonIgnore(Boolean value) {
        getProperties().put(F_JSON_IGNORE, value);
        return this;
    }

    public FieldBean setMax(Integer value) {
        getProperties().put(F_MAX, value);
        return this;
    }


    public FieldBean setMin(Integer value) {
        getProperties().put(F_MIN, value);
        return this;
    }


    public FieldBean setNotNull(Boolean value) {
        getProperties().put(F_NOT_NULL, value);
        return this;
    }


    public FieldBean setOverride(Boolean value) {
        getProperties().put(F_OVERRIDE, value);
        return this;
    }

    public FieldBean setProperty(Boolean value) {
        getProperties().put(F_PROPERTY, value);
        return this;
    }


    public FieldBean setStaticName(Boolean value) {
        getProperties().put(F_STATIC_NAME, value);
        return this;
    }


    public FieldBean setSuper(Boolean value) {
        getProperties().put(F_SUPER, value);
        return this;
    }


    public FieldBean setTransient(Boolean value) {
        getProperties().put(F_TRANSIENT, value);
        return this;
    }


    public FieldBean setUnique(Boolean value) {
        getProperties().put(F_UNIQUE, value);
        return this;
    }


    /*.{}.*/
    private Models getModels(EO eo) {
        return new Models(eo.getConfigsCache(), getModelKeys());
    }

    private Object getFieldValue(final String key) {
        return getProperties().get(key);
    }

    /* no null */

    public void setOverride(String value) {
        getProperties().put(F_OVERRIDE, "true".equals(value));
    }


    public void setMax() {
        if (hasMax()) {
            return;
        }
        getProperties().put(F_MAX, -1);
    }


    public void setJavascriptType() {
        getProperties().put(F_JAVASCRIPT_TYPE, JavascriptFieldTypeCall.createType(getModelKeys()));
    }

}
