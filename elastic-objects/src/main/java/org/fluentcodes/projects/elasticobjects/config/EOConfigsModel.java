package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * @Author Werner Diwischek
 * @since 12.10.2018.
 */
public class EOConfigsModel extends EOConfigs {
    public static final Logger LOG = LogManager.getLogger(EOConfigsModel.class);

    public Map<String,String> classPathMap;
    public EOConfigsModel(final EOConfigsCache eoConfigsCache, final Scope scope) throws Exception {
        super(eoConfigsCache, ModelConfig.class, scope);
    }

    private void initClassMapPath() throws IOException {
        final ClassFinder classFinder = new ClassFinder();
        classFinder
                .addPathFilter("java/util/[^/]*")
                .addPathFilter("java/lang/[^/]*")
                .addPathFilter("org/fluentcodes/.*")
                .addJarFilter(".*/rt.jar")
                .addFileExcludeFilter(".*/test/.*")
                .addPathExcludeFilter(".*Exception.class");
        //.addPathExcludeFilter(".*Test.class")
        classFinder.find();
        this.classPathMap = classFinder.getMapKeyClass();
    }

    @Override
    public Config find(final String key) throws Exception {
        final String simple = key.replaceAll(".*\\.","");
        try {
            return super.find(simple);
        }
        catch (Exception e) {
            try {
                return ModelConfig.add(getConfigsCache(), key);
            }
            catch (Exception e1) {
                if (classPathMap == null) {
                    initClassMapPath();
                }
                if (classPathMap.get(simple) == null) {
                    throw new Exception("Could not find the " + simple + " in the class path map");
                }
                String classPath =  classPathMap.get(simple).replaceAll("/",".").replaceAll("\\.class$","");
                return ModelConfig.add(getConfigsCache(), classPath);
            }
        }
    }
    
    protected void init() throws Exception {
        Map<String, Object> map = new HashMap<>();
        Map<String, Object> eoParamsMap = new HashMap<>();
        map.put(F_NATURAL_ID, "Map");
        map.put(F_MODEL_KEY, "Map");
        map.put(F_PACKAGE_PATH, "java.util");
        eoParamsMap.put(F_SHAPE_TYPE, "MAP");
        eoParamsMap.put(F_DEFAULT_IMPLEMENTATION, "LinkedHashMap");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));

        map = new HashMap<>();
        map.put(F_NATURAL_ID, "LinkedHashMap");
        map.put(F_MODEL_KEY, "LinkedHashMap");
        map.put(F_PACKAGE_PATH, "java.util");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));

        map = new HashMap<>();
        eoParamsMap = new HashMap<>();
        map.put(F_NATURAL_ID, "List");
        map.put(F_MODEL_KEY, "List");
        map.put(F_PACKAGE_PATH, "java.util");
        eoParamsMap.put(F_SHAPE_TYPE, "LIST");
        eoParamsMap.put(F_DEFAULT_IMPLEMENTATION, "ArrayList");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));

        map = new HashMap<>();
        map.put(F_NATURAL_ID, "ArrayList");
        map.put(F_MODEL_KEY, "ArrayList");
        map.put(F_PACKAGE_PATH, "java.util");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));

        map = new HashMap<>();
        eoParamsMap = new HashMap<>();
        map.put(F_NATURAL_ID, "Integer");
        map.put(F_MODEL_KEY, "Integer");
        map.put(F_PACKAGE_PATH, "java.lang");
        eoParamsMap.put(F_SHAPE_TYPE, "SCALAR");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));

        map = new HashMap<>();
        map.put(F_NATURAL_ID, "Long");
        map.put(F_MODEL_KEY, "Long");
        map.put(F_PACKAGE_PATH, "java.lang");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));

        map = new HashMap<>();
        map.put(F_NATURAL_ID, "String");
        map.put(F_MODEL_KEY, "String");
        map.put(F_PACKAGE_PATH, "java.lang");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));

        map = new HashMap<>();
        map.put(F_NATURAL_ID, "Double");
        map.put(F_MODEL_KEY, "Double");
        map.put(F_PACKAGE_PATH, "java.lang");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));

        map = new HashMap<>();
        map.put(F_NATURAL_ID, "Boolean");
        map.put(F_MODEL_KEY, "Boolean");
        map.put(F_PACKAGE_PATH, "java.lang");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));

        map = new HashMap<>();
        map.put(F_NATURAL_ID, "Date");
        map.put(F_MODEL_KEY, "Date");
        map.put(F_PACKAGE_PATH, "java.util");
        map.put(F_EO_PARAMS, eoParamsMap);
        super.add(new ModelConfig.Builder().build(getConfigsCache(), map));
    }
}
