package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.tools.xpect.IORuntimeException;
import org.fluentcodes.tools.xpect.IOString;

import java.lang.reflect.Constructor;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.models.ConfigImpl.*;
import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfigProperties.CLASS_PATH;

/**
 * @Author Werner Diwischek
 * @since 12.10.2018.
 */
public class EOConfigMapModels extends EOConfigMap {
    private final static String MODELS_JSON = "Models.json";
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
        String providerSource = MODELS_JSON;
        try {
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
        catch (IORuntimeException e) {
            LOG.debug("Could not find " + MODELS_JSON + " files.");
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
        final String configModelKey = (String)map.get(Config.CONFIG_MODEL_KEY);
        if (configModelKey !=null && !configModelKey.isEmpty()) {
            switch ((String) map.get(Config.CONFIG_MODEL_KEY)) {
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
        Map<String, Object> properties = new HashMap<>();
        properties.put(ModelConfigProperties.SHAPE_TYPE, "MAP");
        properties.put(ModelConfigProperties.DEFAULT_IMPLEMENTATION, "LinkedHashMap");


        Map<String, Object> map = new HashMap<>();
        map.put(PACKAGE_PATH, "java.util");
        // map
        map.put(Base.NATURAL_ID, "Map");
        map.put(ModelConfig.MODEL_KEY, "Map");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(Config.CONFIG_MODEL_KEY, ModelConfigMap.CONFIG_MODEL_KEY);

        map.put(Config.PROPERTIES, properties);
        addConfigByMap(map);


        map.put(Base.NATURAL_ID, "LinkedHashMap");
        map.put(ModelConfig.MODEL_KEY, "LinkedHashMap");
        addConfigByMap(map);

        // list
        map.put(Base.NATURAL_ID, "List");
        map.put(ModelConfig.MODEL_KEY, "List");
        map.put(Config.CONFIG_MODEL_KEY, ModelConfigList.CONFIG_MODEL_KEY);

        properties.put(ModelConfigProperties.SHAPE_TYPE, "LIST");
        properties.put(ModelConfigProperties.DEFAULT_IMPLEMENTATION, "ArrayList");
        addConfigByMap(map);

        map.put(Base.NATURAL_ID, "ArrayList");
        map.put(ModelConfig.MODEL_KEY, "ArrayList");
        addConfigByMap(map);


        properties.put(ModelConfigProperties.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        properties.put(ModelConfigProperties.CREATE, false);

        map.put(PACKAGE_PATH, "java.lang");
        // scalar
        map.put(Base.NATURAL_ID, "Integer");
        map.put(PACKAGE_PATH, "java.lang");
        map.put(ModelConfig.MODEL_KEY, "Integer");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(Config.CONFIG_MODEL_KEY, ModelConfigScalar.CONFIG_MODEL_KEY);
        addConfigByMap(map);

        map.put(Base.NATURAL_ID, "Long");
        map.put(ModelConfig.MODEL_KEY, "Long");
        addConfigByMap(map);

        map.put(Base.NATURAL_ID, "String");
        map.put(ModelConfig.MODEL_KEY, "String");
        addConfigByMap(map);

        map.put(Base.NATURAL_ID, "Double");
        map.put(ModelConfig.MODEL_KEY, "Double");
        addConfigByMap(map);

        map.put(Base.NATURAL_ID, "Float");
        map.put(ModelConfig.MODEL_KEY, "Float");
        addConfigByMap(map);

        map.put(Base.NATURAL_ID, "Boolean");
        map.put(ModelConfig.MODEL_KEY, "Boolean");
        addConfigByMap(map);

        map.put(PACKAGE_PATH, "java.util");
        map.put(Base.NATURAL_ID, "Date");
        map.put(ModelConfig.MODEL_KEY, "Date");
        addConfigByMap(map);

        //
        properties.put(ModelConfigProperties.SHAPE_TYPE, ShapeTypes.ENUM.name());
        properties.put(ModelConfigProperties.CREATE, false);
        properties.put(CLASS_PATH, "src/main/java");

        map.put(PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        map.put(Base.NATURAL_ID, "LogLevel");
        map.put(ModelConfig.MODEL_KEY, "LogLevel");
        map.put(Config.EXPOSE, Expose.NONE.name());
        addConfigByMap(map);

        map.put(Base.NATURAL_ID, "JSONSerializationType");
        map.put(ModelConfig.MODEL_KEY, "JSONSerializationType");
        addConfigByMap(map);

        map.put(Base.NATURAL_ID, "UnmodifiableMap");
        map.put(ModelConfig.MODEL_KEY, "UnmodifiableMap");
        map.put(Config.CONFIG_MODEL_KEY, ModelConfigMap.CONFIG_MODEL_KEY);
        addConfigByMap(map);

        map.put(Base.NATURAL_ID, "UnmodifiableCollection");
        map.put(ModelConfig.MODEL_KEY, "UnmodifiableCollection");
        map.put(Config.CONFIG_MODEL_KEY, ModelConfigList.CONFIG_MODEL_KEY);

        map.put(Base.NATURAL_ID, "UnmodifiableList");
        map.put(ModelConfig.MODEL_KEY, "UnmodifiableList");
        map.put(Config.CONFIG_MODEL_KEY, ModelConfigList.CONFIG_MODEL_KEY);

        addConfigByMap(map);
    }
}
