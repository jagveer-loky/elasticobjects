package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.tools.xpect.IOString;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * @Author Werner Diwischek
 * @since 12.10.2018.
 */
public class EOConfigMapModels extends EOConfigMap {
    public static final Logger LOG = LogManager.getLogger(EOConfigMapModels.class);
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
                    throw new EoException("Could not find Class " + key + " neither in cache nor classPath! " + e.getMessage());
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
            EO eo = EoRoot.ofValue(getConfigsCache(), classNameJson);
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

    protected void addBasicConfigs()  {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> eoParamsMap = new HashMap<>();
        map.put(Model.NATURAL_ID, "Map");
        map.put(F_MODEL_KEY, "Map");
        map.put(F_PACKAGE_PATH, "java.util");
        eoParamsMap.put(F_SHAPE_TYPE, "MAP");
        eoParamsMap.put(F_DEFAULT_IMPLEMENTATION, "LinkedHashMap");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "LinkedHashMap");
        map.put(F_MODEL_KEY, "LinkedHashMap");
        map.put(F_PACKAGE_PATH, "java.util");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        eoParamsMap = new HashMap<>();
        map.put(Model.NATURAL_ID, "List");
        map.put(F_MODEL_KEY, "List");
        map.put(F_PACKAGE_PATH, "java.util");
        eoParamsMap.put(F_SHAPE_TYPE, "LIST");
        eoParamsMap.put(F_DEFAULT_IMPLEMENTATION, "ArrayList");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "ArrayList");
        map.put(F_MODEL_KEY, "ArrayList");
        map.put(F_PACKAGE_PATH, "java.util");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        eoParamsMap = new HashMap<>();
        map.put(Model.NATURAL_ID, "Integer");
        map.put(F_MODEL_KEY, "Integer");
        map.put(F_PACKAGE_PATH, "java.lang");
        eoParamsMap.put(F_SHAPE_TYPE, "SCALAR");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Long");
        map.put(F_MODEL_KEY, "Long");
        map.put(F_PACKAGE_PATH, "java.lang");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "String");
        map.put(F_MODEL_KEY, "String");
        map.put(F_PACKAGE_PATH, "java.lang");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Double");
        map.put(F_MODEL_KEY, "Double");
        map.put(F_PACKAGE_PATH, "java.lang");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Float");
        map.put(F_MODEL_KEY, "Float");
        map.put(F_PACKAGE_PATH, "java.lang");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Boolean");
        map.put(F_MODEL_KEY, "Boolean");
        map.put(F_PACKAGE_PATH, "java.lang");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Date");
        map.put(F_MODEL_KEY, "Date");
        map.put(F_PACKAGE_PATH, "java.util");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "LogLevel");
        map.put(F_MODEL_KEY, "LogLevel");
        map.put(F_PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        eoParamsMap = new HashMap<>();
        eoParamsMap.put(F_SHAPE_TYPE, "SCALAR");
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "JSONSerializationType");
        map.put(F_MODEL_KEY, "JSONSerializationType");
        map.put(F_PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        eoParamsMap = new HashMap<>();
        eoParamsMap.put(F_SHAPE_TYPE, "SCALAR");
        super.addConfigByMap(map);
    }
}
