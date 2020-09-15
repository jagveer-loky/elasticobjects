package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.tools.xpect.IOString;

import java.lang.reflect.Constructor;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.models.ConfigImpl.CONFIG_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.models.Model.NATURAL_ID;

/**
 * @Author Werner Diwischek
 * @since 12.10.2018.
 */
public class EOConfigMapModels extends EOConfigMap {
    private Set<String> callSet = new TreeSet<>();

    public EOConfigMapModels(final EOConfigsCache eoConfigsCache)  {
        super(eoConfigsCache, ModelConfig.class);
        addBasicConfigs();
    }

   @Override
    public Config find(final String key)  {
        try {
            return super.find(key);
        } catch (EoException e) {
            try {
                return super.find(key.replaceAll(".*\\.", ""));
            }
            catch (EoException e1) {
                try {
                    return ModelConfig.addByClassName(getConfigsCache(), key);
                }
                catch(EoException e2) {
                    throw new EoException("Could not find Class '" + key + "' neither in cache nor classPath! " + e.getMessage());
                }
            }
        }
    }

    protected void addJsonClassNames()  {
        String providerSource = "Models.json";
        List<String> classNameJsons = new IOString()
                .setFileName(providerSource)
                .readStringList();
        for (String classNameJson : classNameJsons) {
            EO eo = new EoRoot(getConfigsCache(), classNameJson);
            List classNames = (List) eo.get();
            if (classNames == null) {
                continue;
            }
            for (Object className: classNames) {
                if ("List".equals(className)) {
                    continue;
                }
                ModelConfig modelConfig = ModelConfig.addByClassName(getConfigsCache(), (String) className);
                super.addConfig(modelConfig);
            }
        }
    }

    protected void addConfigByMap(final Map map) {
        String naturalId = (String)map.get(NATURAL_ID);
        if (naturalId == null) {
            throw new EoInternalException("No naturalid provided for FileConfig");
        }
        if (hasKey(naturalId)) {
            throw new EoInternalException("NaturalId " + naturalId + " already exists FileConfig.");
        }
        Class configurationClass = ModelConfigObject.class;
        final String configModelKey = (String)map.get(CONFIG_MODEL_KEY);
        if (configModelKey !=null && !configModelKey.isEmpty()) {
            switch ((String) map.get(CONFIG_MODEL_KEY)) {
                case ModelConfigMap.CONFIG_MODEL_KEY:
                    configurationClass = ModelConfigMap.class;
                    break;
                case ModelConfigList.CONFIG_MODEL_KEY:
                    configurationClass = ModelConfigList.class;
                    break;
                case ModelConfigNone.CONFIG_MODEL_KEY:
                    configurationClass = ModelConfigNone.class;
                    break;
                case ModelConfigScalar.CONFIG_MODEL_KEY:
                    configurationClass = ModelConfigScalar.class;
                    break;
                default:
                    break;
            }
        }
        try {
        Constructor configurationConstructor = configurationClass.getConstructor(EOConfigsCache.class, Map.class);
            try {
                addConfig((Config)configurationConstructor.newInstance(getConfigsCache(), map));
            } catch (Exception e) {
                throw new EoInternalException("Problem with '" + naturalId + "'/'" + configurationClass.getSimpleName() + "' in ModelConfig", e);
            }
        }
        catch (Exception e) {
            throw new EoInternalException("Problem with '" + naturalId + "'/'" + configurationClass.getSimpleName() + "' in ModelConfig", e);
        }
    }

    protected void addBasicConfigs()  {
        Map<String, Object> map = new HashMap<>();


        // map
        map.put(Model.NATURAL_ID, "Map");
        map.put(ModelConfig.MODEL_KEY, "Map");
        map.put(ModelConfig.PACKAGE_PATH, "java.util");
        map.put(ConfigImpl.EXPOSE, Expose.NONE.name());
        map.put(CONFIG_MODEL_KEY, ModelConfigMap.CONFIG_MODEL_KEY);

        Map<String, Object> eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, "MAP");
        eoParamsMap.put(EOParams.DEFAULT_IMPLEMENTATION, "LinkedHashMap");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        addConfigByMap(map);


        map.put(Model.NATURAL_ID, "LinkedHashMap");
        map.put(ModelConfig.MODEL_KEY, "LinkedHashMap");
        addConfigByMap(map);


        // list
        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "List");
        map.put(ModelConfig.MODEL_KEY, "List");
        map.put(ModelConfig.PACKAGE_PATH, "java.util");
        map.put(ConfigImpl.EXPOSE, Expose.NONE.name());
        map.put(CONFIG_MODEL_KEY, ModelConfigList.CONFIG_MODEL_KEY);

        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, "LIST");
        eoParamsMap.put(EOParams.DEFAULT_IMPLEMENTATION, "ArrayList");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        addConfigByMap(map);


        map.put(Model.NATURAL_ID, "ArrayList");
        map.put(ModelConfig.MODEL_KEY, "ArrayList");
        addConfigByMap(map);


        // scalar
        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Integer");
        map.put(ModelConfig.MODEL_KEY, "Integer");
        map.put(ModelConfig.PACKAGE_PATH, "java.lang");
        map.put(ConfigImpl.EXPOSE, Expose.NONE.name());
        map.put(CONFIG_MODEL_KEY, ModelConfigScalar.CONFIG_MODEL_KEY);

        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        addConfigByMap(map);

        map.put(Model.NATURAL_ID, "Long");
        map.put(ModelConfig.MODEL_KEY, "Long");
        addConfigByMap(map);

        map.put(Model.NATURAL_ID, "String");
        map.put(ModelConfig.MODEL_KEY, "String");
        addConfigByMap(map);

        map.put(Model.NATURAL_ID, "Double");
        map.put(ModelConfig.MODEL_KEY, "Double");
        addConfigByMap(map);

        map.put(Model.NATURAL_ID, "Float");
        map.put(ModelConfig.MODEL_KEY, "Float");
        addConfigByMap(map);

        map.put(Model.NATURAL_ID, "Boolean");
        map.put(ModelConfig.MODEL_KEY, "Boolean");
        addConfigByMap(map);

        map.put(Model.NATURAL_ID, "Date");
        map.put(ModelConfig.MODEL_KEY, "Date");
        map.put(ModelConfig.PACKAGE_PATH, "java.util");
        addConfigByMap(map);

        map.put(Model.NATURAL_ID, "LogLevel");
        map.put(ModelConfig.MODEL_KEY, "LogLevel");
        map.put(ModelConfig.PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        map.put(ConfigImpl.EXPOSE, Expose.INFO.name());
        addConfigByMap(map);

        map.put(Model.NATURAL_ID, "JSONSerializationType");
        map.put(ModelConfig.MODEL_KEY, "JSONSerializationType");
        map.put(ModelConfig.PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        addConfigByMap(map);
    }
}
