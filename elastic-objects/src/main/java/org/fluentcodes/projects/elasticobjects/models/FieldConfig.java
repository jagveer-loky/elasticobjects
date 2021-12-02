package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.values.StringUpperFirstCharCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

/*.{javaHeader}|*/
/**
 * 
 * Immutabel EO field configuration will be initalized by internal builder using map values. 
 * @author Werner Diwischek
 * @creationDate Wed Oct 17 00:00:00 CEST 2018
 * @modificationDate Thu Jan 14 04:26:27 CET 2021
 */
public class FieldConfig extends ConfigConfig implements FieldInterface {
    public static final String AND_MODEL = "' and model '";
    /*.{}.*/
/*.{javaInstanceVars}|*/
   /* fieldKey */
   private final String fieldKey;
   /* Length of a field. */
   private final Integer length;
   /* A string representation for a list of modelsConfig. */
   private final String modelKeys;
/*.{}.*/
    private boolean resolved;
    private final boolean toSerialize;
    private List<String> modelList;
    private final ModelInterface parentModel;
    private Models models;
    private Method getter;
    private Method setter;

    public FieldConfig(final ModelConfig parentModel, final FieldBean bean) {
        super(bean, parentModel.getConfigMaps());
        this.parentModel = parentModel;
        this.toSerialize = false;
        this.fieldKey = bean.getFieldKey();
        this.modelKeys = bean.getModelKeys();
        this.modelList = Arrays.asList(modelKeys.split(","));
        this.length = bean.getLength();
    }

    public FieldConfig(final ConfigBean bean, final ConfigMaps configMaps) {
        this((FieldBean)bean, configMaps);
    }

    public FieldConfig(final FieldBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
        this.toSerialize = false;
        this.fieldKey = bean.getFieldKey();
        this.modelKeys = bean.getModelKeys();
        this.modelList = Arrays.asList(modelKeys.split(","));
        this.length = bean.getLength();
        parentModel = null;
    }


    protected void resolve(ModelConfig model, Map<String, ModelInterface> modelConfigMap) {
        if (resolved) {
            return;
        }
        resolved = true;
        if (!hasModelKeys()) {
            throw new EoException("Every field needs a model type but '" + getNaturalId() + "' has none!" );
        }
        String[] modelKeyArray = modelKeys.split(",");
        List<ModelConfig> modelConfigList = new ArrayList<>();
        for (String modelKey: modelKeyArray) {
            if (!modelConfigMap.containsKey(modelKey)) {
                throw new EoException("Could not resolve fieldType '" + modelKey + "' for '" + fieldKey + "' (" + getModelKeys() + ").");
            }
            modelConfigList.add((ModelConfig)modelConfigMap.get(modelKey));
        }
        this.models = new Models(modelConfigList);

        if (!model.isObject()) {
            return;
        }

        if (model.getShapeType() == ShapeTypes.INTERFACE &&
                !isDefault() &&
                !hasProperty()) {
                return;
        }

        this.getter = getGetMethod(model, this.fieldKey);

        if (isFinal()) {
            return;
        }

        this.setter = getSetMethod(model, this.fieldKey);
    }

    private Method getGetMethod(final ModelConfig model, final String fieldKey) {
        try {
            return model.getModelClass().getMethod(StringUpperFirstCharCall.getter(fieldKey), null);
        } catch (NoSuchMethodException e) {
            throw new EoException("\nCould not find getter method for '" + fieldKey + AND_MODEL + parentModel.getNaturalId() + "' with input type '" + models.getModelClass().getSimpleName() + "': " + e.getMessage());
        }
    }

    private Method getSetMethod(final ModelConfig model, final String fieldKey) {
        try {
            return model.getModelClass().getMethod(StringUpperFirstCharCall.setter(fieldKey), models.getModelClass());
        } catch (NoSuchMethodException e) {
            throw new EoException("\nCould not find setter method for '" + fieldKey + AND_MODEL + parentModel.getNaturalId() + "' with input type '" + models.getModelClass().getSimpleName() + "': " + e.getMessage());
        }
    }

    protected Object get(Object parent) {
        if (getter==null) {
            /*for (Object key: getProperties().keySet()) {
                System.out.println(key + " " + getProperties().get(key));
            }*/
            throw new EoException("No getter defined '" + getNaturalId() + "' for '" + parent.getClass().getSimpleName() + "'.");
        }
        if (parent==null) {
            throw new EoException("Null parent for get '" + getNaturalId() + "' for '\" + parent.getClass().getSimpleName() + \"'.");
        }
        try {
            return getter.invoke(parent, null);
        } catch (IllegalAccessException | InvocationTargetException e) {
            throw new EoException("Problem invoke getter with '" + getNaturalId() + "' and model '" + parent.getClass().getSimpleName() + "':" + e.getMessage());
        }
    }

    protected void set(Object parent, Object value) {
        if (isFinal()) {
            throw new EoException("Field '" + getNaturalId() + "' marked as final for model '" + parent.getClass().getSimpleName() + "'.");
        }
        if (setter==null) {
            throw new EoException("Setter is null for field '" + getNaturalId() + "' and model '" + parent.getClass().getSimpleName() + "'.");
        }
        if (parent==null) {
            throw new EoException("Null parent for field '" + getNaturalId() + "'.");
        }
        try {
            setter.invoke(parent, value);
        } catch (Exception e) {
            throw new EoException("\nProblem invoke setter with '" + getNaturalId() + "' and model '" + parent.getClass().getSimpleName() + "' with value class'" + value.getClass().getSimpleName() + "':" + e.getMessage());
        }
    }

    public List<String> getModelList() {
        return new ArrayList<>(modelList);
    }

/*.{javaAccessors}|*/
   @Override
   public String getFieldKey() {
      return this.fieldKey;
   }

   @Override
   public Integer getLength() {
      return this.length;
   }

   @Override
   public String getModelKeys() {
      return this.modelKeys;
   }

/*.{}.*/

    public Models getModels() {
        return models;
    }

    public Class<?> getModelClass()  {
        return models.getModelClass();
    }

    public String getModel()  {
        return getModels().getModel().getModelKey();
    }

    public ModelInterface getParentModel()  {
        return parentModel;
    }

    public ModelConfig getChildModel()  {
        return getModels().getChildModel();
    }

    @Override
    public String toString() {
        if (getConfigMaps().isModelFinished()) {
            return super.toString();
        }
        return fieldKey + "(" + modelKeys +")";
    }

    public void populateBean(final FieldBean bean) {
        super.populateBean(bean);
        bean.setFieldKey(fieldKey);
        bean.setOverride(getOverride());
        bean.setFinal(getFinal());
        bean.setModelKeys(modelKeys);
        bean.setLength(getLength());
        bean.setGenerated(getGenerated());
        bean.setNotNull(getNotNull());
        bean.setSuper(getSuper());
        bean.setDefault(getDefault());
        bean.setTransient(getTransient());
        bean.setUnique(getUnique());
        bean.setFieldName(getFieldName());
        bean.setJavascriptType(getJavascriptType());
        bean.setJsonIgnore(bean.getJsonIgnore());
        bean.setMax(getMax());
        bean.setMin(getMin());
        bean.setStaticName(getStaticName());
        bean.setProperty(getProperty());
    }
}
