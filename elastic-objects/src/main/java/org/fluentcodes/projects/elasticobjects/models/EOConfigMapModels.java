package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.tools.xpect.IOString;

import java.util.*;

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

    protected void addBasicConfigs()  {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> eoParamsMap = new HashMap<>();
        map.put(Model.NATURAL_ID, "Map");
        map.put(ModelConfig.MODEL_KEY, "Map");
        map.put(ModelConfig.PACKAGE_PATH, "java.util");
        map.put(Config.EXPOSE, Expose.NONE.name());
        eoParamsMap.put(EOParams.SHAPE_TYPE, "MAP");
        eoParamsMap.put(EOParams.DEFAULT_IMPLEMENTATION, "LinkedHashMap");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "LinkedHashMap");
        map.put(ModelConfig.MODEL_KEY, "LinkedHashMap");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(ModelConfig.PACKAGE_PATH, "java.util");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        eoParamsMap = new HashMap<>();
        map.put(Model.NATURAL_ID, "List");
        map.put(ModelConfig.JSON_TYPE, "list");
        map.put(ModelConfig.MODEL_KEY, "List");
        map.put(ModelConfig.PACKAGE_PATH, "java.util");
        eoParamsMap.put(EOParams.SHAPE_TYPE, "LIST");
        eoParamsMap.put(EOParams.DEFAULT_IMPLEMENTATION, "ArrayList");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "ArrayList");
        map.put(ModelConfig.JSON_TYPE, "list");
        map.put(ModelConfig.MODEL_KEY, "ArrayList");
        map.put(ModelConfig.PACKAGE_PATH, "java.util");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);


        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Integer");
        map.put(ModelConfig.MODEL_KEY, "Integer");

        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);

        map.put(ModelConfig.PACKAGE_PATH, "java.lang");
        map.put(Config.EXPOSE, Expose.NONE.name());
        eoParamsMap.put(EOParams.SHAPE_TYPE, "SCALAR");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);


        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Long");
        map.put(ModelConfig.MODEL_KEY, "Long");

        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        eoParamsMap.put(ModelConfig.JSON_TYPE, "integer");
        eoParamsMap.put(ModelConfig.JSON_FORMAT, "int64");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);

        map.put(ModelConfig.PACKAGE_PATH, "java.lang");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);


        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "String");
        map.put(ModelConfig.MODEL_KEY, "String");

        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        eoParamsMap.put(ModelConfig.JSON_TYPE, "string");
        eoParamsMap.put(ModelConfig.JSON_FORMAT, "");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);

        map.put(ModelConfig.PACKAGE_PATH, "java.lang");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Double");
        map.put(ModelConfig.MODEL_KEY, "Double");


        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        eoParamsMap.put(ModelConfig.JSON_TYPE, "number");
        eoParamsMap.put(ModelConfig.JSON_FORMAT, "double");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);

        map.put(ModelConfig.JSON_TYPE, "number");
        map.put(ModelConfig.JSON_FORMAT, "double");
        map.put(ModelConfig.PACKAGE_PATH, "java.lang");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);


        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Float");
        map.put(ModelConfig.MODEL_KEY, "Float");

        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        eoParamsMap.put(ModelConfig.JSON_TYPE, "number");
        eoParamsMap.put(ModelConfig.JSON_FORMAT, "float");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);

        map.put(ModelConfig.PACKAGE_PATH, "java.lang");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);


        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Boolean");
        map.put(ModelConfig.MODEL_KEY, "Boolean");

        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        eoParamsMap.put(ModelConfig.JSON_TYPE, "boolean");
        eoParamsMap.put(ModelConfig.JSON_FORMAT, "boolean");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);

        map.put(ModelConfig.PACKAGE_PATH, "java.lang");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);


        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "Date");
        map.put(ModelConfig.MODEL_KEY, "Date");
        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        eoParamsMap.put(ModelConfig.JSON_TYPE, "date");
        eoParamsMap.put(ModelConfig.JSON_FORMAT, "date-time");
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        map.put(ModelConfig.PACKAGE_PATH, "java.util");
        map.put(Config.EXPOSE, Expose.NONE.name());
        map.put(ModelConfig.EO_PARAMS, eoParamsMap);
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "LogLevel");
        map.put(ModelConfig.MODEL_KEY, "LogLevel");
        map.put(ModelConfig.PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, ShapeTypes.SCALAR.name());
        super.addConfigByMap(map);

        map = new HashMap<>();
        map.put(Model.NATURAL_ID, "JSONSerializationType");
        map.put(ModelConfig.MODEL_KEY, "JSONSerializationType");
        map.put(ModelConfig.PACKAGE_PATH, "org.fluentcodes.projects.elasticobjects");
        eoParamsMap = new HashMap<>();
        eoParamsMap.put(EOParams.SHAPE_TYPE, "SCALAR");
        super.addConfigByMap(map);
    }
}
