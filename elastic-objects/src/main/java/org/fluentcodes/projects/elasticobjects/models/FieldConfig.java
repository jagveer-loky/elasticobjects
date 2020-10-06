package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NAME;

/**
 * Created by Werner on 09.10.2016.
 */
public class FieldConfig extends ConfigImpl implements PropertiesFieldAccessor {
    public static final String FIELD_KEY = "fieldKey";
    public static final String MODEL_KEYS = "modelKeys";
    public static final String TO_SERIALIZE = "toSerialize";
    public static final String LENGTH = "length";
    private final Boolean toSerialize;
    private final String fieldKey;
    private final String modelKeys;
    private final Integer length;
    private List<String> modelList;
    private Models models;

    public FieldConfig(EOConfigsCache configsCache, Map map) {
        super(configsCache, map);
        try {
            this.toSerialize = map.containsKey(TO_SERIALIZE) ? ScalarConverter.toBoolean(TO_SERIALIZE) : false;
            this.fieldKey = (String) map.get(FIELD_KEY);
            this.modelKeys = (String) map.get(MODEL_KEYS);
            this.modelList = new ArrayList<>();
            this.length = map.containsKey(LENGTH) ? ScalarConverter.toInt(map.get(LENGTH)) : null;
            super.setExpose(Expose.NONE);
        }
        catch (Exception e) {
            throw new EoInternalException("Problem setting field with " + map.get(NATURAL_ID));
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

    protected final static void addByClassField(EOConfigsCache configsCache, Field field)  {
        Class modelClass = field.getDeclaringClass();
        Class typeClass = field.getType();
        Map map = new HashMap();

        map.put(FIELD_KEY, field.getName());
        map.put(NATURAL_ID, modelClass.getSimpleName() + "." + field.getName());
        map.put(F_NAME, field.getName());
        map.put(MODEL_KEYS, typeClass.getSimpleName());
        FieldConfig config = new FieldConfig(configsCache, map);
        configsCache.add(FieldConfig.class, config);
        if (!configsCache.hasConfigKey(ModelConfig.class, typeClass.getSimpleName())) {
            ModelConfig.addByClassName(configsCache, typeClass.getName());
        }
    }

    @Override
    public void resolve()  {
        super.resolve();
        this.models = new Models(getConfigsCache(), modelKeys.split(","));
    }

    @Override
    public String getKey() {
        return fieldKey;
    }

    public Integer getLength() {
        return length;
    }

    public Boolean getToSerialize() {
        return toSerialize;
    }

    public String getFieldKey() {
        return this.fieldKey;
    }

    //<call keep="JAVA" templateKey="CacheGetter.tpl" }
    public Models getModels() {
        resolve();
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

    public ModelConfigInterface getModelConfig()  {
        return getModels().getModel();
    }

    public ModelConfigInterface getChildModel()  {
        return getModels().getChildModel();
    }
}
