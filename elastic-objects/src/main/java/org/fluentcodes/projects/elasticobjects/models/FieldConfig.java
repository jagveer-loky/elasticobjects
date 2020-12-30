package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.values.StringUpperFirstCharCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class FieldConfig extends ConfigConfig implements FieldConfigInterface {
    public static final String FIELD_KEY = "fieldKey";
    public static final String MODEL_KEYS = "modelKeys";
    public static final String TO_SERIALIZE = "toSerialize";
    public static final String LENGTH = "length";
    public static final String NAME = "name";
    public static final String DEFAULT_VALUE = "defaultValue";

    private boolean resolved;
    private final Boolean toSerialize;
    private final String fieldKey;
    private final String modelKeys;
    private final Integer length;
    private List<String> modelList;
    private final Object defaultValue;
    private final ModelConfig modelConfig;
    private Models models;
    private Method getter;
    private Method setter;

    public FieldConfig(final ModelConfig modelConfig, final FieldBeanInterface bean) {
        super(bean);
        this.modelConfig = modelConfig;
        this.toSerialize = false;
        this.fieldKey = bean.getFieldKey();
        this.modelKeys = bean.getModelKeys();
        this.modelList = ((FieldBean)bean).getModelList();
        this.length = bean.getLength();
        this.defaultValue = bean.getDefaultValue();
    }

    protected void resolve(ModelConfig model, Map<String, ConfigConfigInterface> modelConfigMap) {
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

        if (model.getShapeType() == ShapeTypes.INTERFACE) {
            String value = getProperties().toString();
            if (!isDefault()) {
                return;
            }
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
            throw new EoException("\nCould not find getter method for '" + fieldKey + "' and model '" + modelConfig.getNaturalId() + "' with input type '" + models.getModelClass().getSimpleName() + "': " + e.getMessage());
        }
    }

    private Method getSetMethod(final ModelConfig model, final String fieldKey) {
        try {
            return model.getModelClass().getMethod(StringUpperFirstCharCall.setter(fieldKey), models.getModelClass());
        } catch (NoSuchMethodException e) {
            throw new EoException("\nCould not find setter method for '" + fieldKey + "' and model '" + modelConfig.getNaturalId() + "' with input type '" + models.getModelClass().getSimpleName() + "': " + e.getMessage());
        }
    }

    protected Object get(Object parent) {
        if (getter==null) {
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

    protected void addModel(final ModelConfig modelConfig) {
        if (modelConfig == null || modelConfig.getNaturalId() == null)  {
            throw new EoInternalException("Problem with modelConfig where naturalId could not be resolved");
        }
        if (modelList.contains(modelConfig.getNaturalId())) {
            return;
        }
        modelList.add(modelConfig.getNaturalId());
        if (getExpose().ordinal() >= modelConfig.getExpose().ordinal()) {
            super.setExpose(modelConfig.getExpose());
        }
    }

    public List<String> getModelList() {
        return new ArrayList<>(modelList);
    }

    public boolean hasModelList() {
        return !modelList.isEmpty();
    }

    @Override
    public Integer getLength() {
        return length;
    }

    @Override
    public Object getDefaultValue() {
        return defaultValue;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    public Models getModels() {
        return models;
    }

    public String getModelKeys() {
        return modelKeys;
    }


    public Class getModelClass()  {
        return getModelConfig().getModelClass();
    }

    public Class getChildClass()  {
        return getChildModel().getModelClass();
    }

    public String getModel()  {
        return getModels().getModel().getModelKey();
    }

    public ModelConfig getModelConfig()  {
        return getModels().getModel();
    }

    public ModelConfig getChildModel()  {
        return getModels().getChildModel();
    }

    @Override
    public String toString() {
        return modelConfig.getNaturalId() + "." + getNaturalId() + "";
    }
}
