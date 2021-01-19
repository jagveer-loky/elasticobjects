package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public abstract class ModelConfig extends ConfigConfig implements ModelConfigInterfaceMethods {
    public static final String MODEL_KEY = "modelKey";
    public static final String INTERFACES = "interfaces";
    public static final String SUPER_KEY = "superKey";
    public static final String PACKAGE_PATH = "packagePath";
    public static final String FIELD_CONFIG_MAP = "fieldConfigMap";

    private static final Logger LOG = LogManager.getLogger(ModelConfig.class);
    private boolean resolved = false;
    private boolean resolvedFields = false;
    private final String modelKey;
    private final String packagePath;
    private final String superKey;
    private final String interfaces;

    private Class modelClass;
    private ModelConfig superModel;
    private ModelConfig defaultImplementationModel;

    //resolved
    private final Map<String, FieldConfig> fieldConfigMap;
    private final Map<String, ModelConfig> interfacesMap;

    public ModelConfig(final ModelBean bean) {
        super(bean);
        modelKey = bean.getModelKey();
        packagePath = bean.getPackagePath();
        superKey = bean.getSuperKey();
        interfaces = bean.getInterfaces();
        this.fieldConfigMap = new LinkedHashMap<>();
        this.interfacesMap = new LinkedHashMap<>();
        if (bean.hasFieldBeans()) {
            for (FieldBeanInterface fieldBean : bean.getFieldBeans().values()) {
                fieldConfigMap.put(fieldBean.getFieldKey(), new FieldConfig(this, fieldBean));
            }
        }
        setModelClass();
    }

    public ModelConfig(Map map) {
        this(new ModelBean(map));
    }

    /**
     * The model name for the actions
     */
    public String getModelKey() {
        return this.modelKey;
    }
    //<call keep="JAVA" configKey="CacheGetter.tpl" }

    public String getPackagePath() {
        return this.packagePath;
    }

    /**
     * A key for the super class of a class.
     */
    public String getSuperKey() {
        return this.superKey;
    }

    /**
     * A comma separeted list of interface keys.
     */
    public String getInterfaces() {
        return this.interfaces;
    }

    public Class getModelClass() {
        if (modelClass == null) {
            return Object.class;
        }
        return modelClass;
    }

    @Override
    public Map<String, FieldConfig> getFieldMap() {
        return fieldConfigMap;
    }

    public Map<String, FieldConfig> getFieldConfigMap() {
        return fieldConfigMap;
    }

    public void setModelClass()  {
        if (getModelKey() == null) {
            throw new EoException("No modelkey defined. No model class could be derived!");
        }
        String packagePath = getPackagePath();
        if (packagePath == null) {
            throw new EoException("No packagePath for " + modelKey + "defined.");
        }
        try {
            this.modelClass = (Class.forName(packagePath + "." + modelKey));
        } catch (Exception e) {
            throw new EoException("Class not resolved with packagePath " + packagePath + " and modelKey " + modelKey, e);
        }
    }

    private final void setSuperModel(Map<String, ConfigConfigInterface> modelConfigMap) {
        if (!hasSuperKey()) {
            return;
        }
        if (!modelConfigMap.containsKey(getSuperKey())) {
            throw new EoException("Could not find modelConfig for super with key '" + superKey + "' for '" + getNaturalId() + "'.");
        }
        this.superModel = (ModelConfig) modelConfigMap.get(superKey);
    }



    private final void setDefaultImplementationModel(Map<String, ConfigConfigInterface> modelConfigMap) {
        if (!hasDefaultImplementation()) {
            return;
        }
        if (!modelConfigMap.containsKey(getDefaultImplementation())) {
            throw new EoException("Could not find modelConfig for default implementation with key '" + getDefaultImplementation() + "' for '" + getNaturalId() + "'.");
        }
        this.defaultImplementationModel = (ModelConfig) modelConfigMap.get(getDefaultImplementation());
    }

    public final ModelConfigInterfaceMethods getDefaultImplementationModel() {
        return defaultImplementationModel;
    }

    private final void setInterfacesMap(Map<String, ConfigConfigInterface> cache) {
        if (interfaces == null || interfaces.isEmpty()) {
            return;
        }
        String[] interfaceArray = interfaces.split(",");
        for (String interfaceEntry : interfaceArray) {
            interfacesMap.put(interfaceEntry, (ModelConfig)cache.get(interfaceEntry));
        }
    }

    private void setFieldConfigMap(final Map<String, ConfigConfigInterface> modelConfigMap) {
        if (!hasFieldConfigMap()) {
            return;
        }
        for (FieldConfig fieldConfig: fieldConfigMap.values()) {
            fieldConfig.resolve(this, modelConfigMap);
        }
    }



    @Override
    public Map getKeyValues(final Object object, final PathPattern pathPattern) {
        Set<String> keySet = keys(object);
        Map keyValues = new LinkedHashMap();
        List<String> keys;
        if (pathPattern == null || pathPattern.get().size() == 0) {
            keys = new ArrayList(keySet);
        } else {
            keys = pathPattern.filter(keySet);
        }
        for (String key : keys) {
            try {
                /*if (fieldConfigMap.get(key).isTransient()) {
                    continue;
                }*/
                Object value = get(key, object);
                if (value == null) {
                    continue;
                }
                keyValues.put(key, value);
            } catch (Exception e) {
                LOG.warn("Problem getting value for " + key + ": " + e.getMessage());
            }
        }
        return keyValues;
    }

    public void resolve(Map<String, ConfigConfigInterface> modelConfigMap)  {
        if (resolved) {
            return;
        }
        setSuperModel(modelConfigMap);
        setDefaultImplementationModel(modelConfigMap);
        setInterfacesMap(modelConfigMap);
        resolved = true;
        if (!hasFieldConfigMap()) {
            return;
        }
        setFieldConfigMap(modelConfigMap);
        resolved = true;
    }


    protected void resolveSuper() {
        if (resolvedFields) {
            return;
        }
        try {
            if (this.hasSuperKey()) {
                if (getSuperKey().equals(modelKey)) {
                    throw new EoInternalException("Recursive super '" + getSuperKey() + "'!");
                }
                superModel.resolveSuper();
                for (String key : superModel.getFieldKeys()) {
                    if (fieldConfigMap.containsKey(key)) {
                        continue;
                    }
                    fieldConfigMap.put(key, (FieldConfig)superModel.getField(key));
                }
            }
            for (ModelConfig interfaceModel : interfacesMap.values()) {
                if (interfaceModel == null) {
                    continue;
                }
                if (interfaceModel.getModelKey().equals(modelKey)) {
                    throw new EoInternalException("Recursive super '" + interfaceModel.getModelKey() + "'!");
                }
                interfaceModel.resolveSuper();
                for (String key : interfaceModel.getFieldKeys()) {
                    if (fieldConfigMap.containsKey(key)) {
                        continue;
                    }
                    fieldConfigMap.put(key, (FieldConfig) interfaceModel.getField(key));
                }
            }
        }
        catch(Exception e) {
            e.printStackTrace();
        }
        resolvedFields = true;
    }

    public boolean equals(ModelConfig modelCache) {
        if (modelKey == null) {
            return false;
        }
        if (modelCache == null) {
            return false;
        }
        return modelKey.equals(modelCache.getModelKey());
    }

    @Override
    public String toString() {
        return getShapeType() + " " + getNaturalId() ;
    }
}
