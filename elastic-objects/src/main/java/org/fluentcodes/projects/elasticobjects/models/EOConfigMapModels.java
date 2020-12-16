package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.UnmodifiableCollection;
import org.fluentcodes.projects.elasticobjects.UnmodifiableList;
import org.fluentcodes.projects.elasticobjects.UnmodifiableMap;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

/**
 * @Author Werner Diwischek
 * @since 12.10.2018.
 */
public class EOConfigMapModels extends EOConfigMap {
    private final static String MODELS_JSON = "Models.json";
    private Set<String> callSet = new TreeSet<>();

    public static final ModelConfig DEFAULT_MODEL = new ModelConfigMap(new ModelBean(Map.class, ShapeTypes.MAP));

    public EOConfigMapModels(EOConfigsCache cache, final Scope scope)  {
        super(cache, scope, ModelConfig.class);
    }

    @Override
    protected Map<String, ConfigConfigInterface> initMap() {
        Map<String, ModelBean> modelBeanMap = createBaseModelBeans();
        if (getScope() == Scope.DEV) {
            return createModelConfigMap(modelBeanMap);
        }
        // reading all ModelConfig.json
        String configModelsString = readConfigFiles();

        final EOConfigsCache baseCache = new EOConfigsCache();
        Map<String, Map> modelMap = (Map<String, Map>) new EoRoot(baseCache, configModelsString).get();
        Map<String, Map> fieldMap = readFieldMap(baseCache);
        for (String naturalId: modelMap.keySet() ) {
            if (modelBeanMap.containsKey(naturalId)) {
                throw new EoInternalException("Bean with naturalId '" + naturalId + "' already exists");
            }
            ModelBean modelBean = new ModelBean(modelMap.get(naturalId));
            if (!modelBean.hasNaturalId()) {
                modelBean.setNaturalId(naturalId);
            }
            modelBean.mergeFieldDefinition(fieldMap);
            modelBeanMap.put(modelBean.getNaturalId(), modelBean);
        }
        addJsonClassNames(modelBeanMap);

        Map<String, ConfigConfigInterface> modelConfigMap = createModelConfigMap(modelBeanMap);
        for (ConfigConfigInterface modelConfig: modelConfigMap.values()) {
            ((ModelConfig)modelConfig).resolve(modelConfigMap);
        }
        for (ConfigConfigInterface modelConfig: modelConfigMap.values()) {
            ((ModelConfig)modelConfig).resolveSuper();
        }
        return modelConfigMap;
    }



    private static final Map<String, ConfigConfigInterface> createModelConfigMap(Map<String, ModelBean> modelBeanMap) {
        Map<String, ConfigConfigInterface> modelConfigMap = new TreeMap<>();
        for (ModelBean modelBean: modelBeanMap.values()) {
            modelConfigMap.put(modelBean.getNaturalId(), modelBean.createConfig());
        }
        return modelConfigMap;
    }

    protected Map<String, Map> readFieldMap(final EOConfigsCache baseCache) {
        String configFieldsString = readConfigFiles("FieldConfig.json");
        Map<String, Map> configFieldMap = (Map<String, Map>) new EoRoot(baseCache, configFieldsString).get();
        return configFieldMap;
    }

    protected void addJsonClassNames(Map<String, ModelBean> modelBeanMap)  {
        String modelListString = readConfigFiles(MODELS_JSON);
        if (modelListString == null || modelListString.isEmpty()) {
            return;
        }
        String[] modelClasses = modelListString.split("\n");
        for (String modelClass: modelClasses) {
            addModelForClasses(modelClass, modelBeanMap);
        }
    }

    private void addModelForClasses(String modelClass, Map<String, ModelBean> modelBeanMap) {
        try {
            addModelForClasses(Class.forName(modelClass), modelBeanMap);
        } catch (Exception e) {
            throw new EoException("Could not find Class " + modelClass + ": " + e.getMessage());
        }
    }

    private void addModelForClasses(Class modelClass, Map<String, ModelBean> modelBeanMap) {
        ModelBeanForClasses modelBean = new ModelBeanForClasses(modelClass, modelBeanMap);
        if (modelBeanMap.containsKey(modelClass.getSimpleName())) {
            LOG.info("Already defined '" + modelBeanMap + "'");
            return;
        }
        for (FieldBeanInterface fieldBean: modelBean.getFieldBeans().values()) {
            String typeKey = ((FieldBeanForClasses)fieldBean).getTypeKey();
            if (!modelBeanMap.containsKey(typeKey)) {
                addModelForClasses(((FieldBeanForClasses) fieldBean).getTypeClass().getTypeName(), modelBeanMap);
            }
        }
        modelBeanMap.put(modelBean.getNaturalId(), modelBean);
        if (modelBean.hasSuperClass()) {
            addModelForClasses(modelBean.getSuperClass(), modelBeanMap);
        }
    }

    private static void addModelBeanMap(final HashMap<String, ModelBean> modelMap, Class modelClass) {
        modelMap.put(modelClass.getSimpleName(), new ModelBean(modelClass, ShapeTypes.MAP));
    }
    private static void addModelBeanList(final HashMap<String, ModelBean> modelMap, Class modelClass) {
        modelMap.put(modelClass.getSimpleName(), new ModelBean(modelClass, ShapeTypes.LIST));
    }
    private static void addModelBeanScalar(final HashMap<String, ModelBean> modelMap, Class modelClass) {
        modelMap.put(modelClass.getSimpleName(), new ModelBean(modelClass, ShapeTypes.SCALAR));
    }

    private static final Map<String, ModelBean> createBaseModelBeans() {
        HashMap<String, ModelBean> modelMap = new LinkedHashMap<>();
        addModelBeanMap(modelMap, LinkedHashMap.class);
        addModelBeanMap(modelMap, Map.class);
        addModelBeanMap(modelMap, UnmodifiableMap.class);

        addModelBeanList(modelMap, UnmodifiableCollection.class);
        addModelBeanList(modelMap, UnmodifiableList.class);
        addModelBeanList(modelMap, List.class);
        addModelBeanList(modelMap, ArrayList.class);

        addModelBeanScalar(modelMap, Integer.class);
        addModelBeanScalar(modelMap, Long.class);
        addModelBeanScalar(modelMap, String.class);
        addModelBeanScalar(modelMap, Float.class);
        addModelBeanScalar(modelMap, Double.class);
        addModelBeanScalar(modelMap, Boolean.class);
        addModelBeanScalar(modelMap, Date.class);
        addModelBeanScalar(modelMap, LogLevel.class);
        addModelBeanScalar(modelMap, JSONSerializationType.class);
        return modelMap;
    }

}
