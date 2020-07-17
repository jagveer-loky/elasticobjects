package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.config.Scope;
import org.fluentcodes.projects.elasticobjects.config.ShapeTypes;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class TestProviderConfig {
    public static void createConfig(Map<String, Object> map) {
        try {
            addMap(map, F_ID, 1L,
                    F_CREATION_DATE, SAMPLE_DATE,
                    F_DESCRIPTION, F_DESCRIPTION,
                    F_NATURAL_ID, F_NATURAL_ID,
                    F_EXPANDED, S1,
                    F_SCOPE, "ALL",
                    F_MODULE, F_MODULE,
                    F_SUB_MODULE, F_SUB_MODULE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Map<String, Object> createModelConfigMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(F_MODEL_KEY, F_MODEL_KEY);
        map.put(F_DB_PARAMS, createDbParams());
        map.put(F_EO_PARAMS, createEoParams());
        map.put(F_VIEW_PARAMS, null);
        map.put(F_CUSTOM_PARAMS, null);
        map.put(F_FIELD_KEYS, null);
        map.put(F_PACKAGE_PATH, F_PACKAGE_PATH);
        map.put(F_PACKAGE_GROUP, F_PACKAGE_GROUP);
        map.put(F_AUTHOR, F_AUTHOR);
        map.put(F_SUPER_KEY, F_SUPER_KEY);
        map.put(F_INTERFACES, F_INTERFACES);
        createConfig(map);
        return map;
    }

    public static Map<String, Object> createEoParams() {
        Map<String, Object> map = new HashMap();
        map.put(F_CREATE, S1);
        map.put(F_SHAPE_TYPE, ShapeTypes.MAP.name());
        map.put(F_SCOPE, Scope.ALL.name());
        map.put(F_METHODS, F_METHODS);
        map.put(F_ATTRIBUTE_LIST, F_ATTRIBUTE_LIST);
        map.put(F_MODEL_CONFIG_KEY, F_MODEL_CONFIG_KEY);
        map.put(F_DEFAULT_IMPLEMENTATION, F_DEFAULT_IMPLEMENTATION);
        map.put(F_PATH_PATTERN, F_PATH_PATTERN);
        return map;
    }

    public static Map<String, Object> createDbParams() {
        Map<String, Object> map = new HashMap();
        map.put(F_TABLE, F_TABLE);
        map.put(F_ID_KEY, F_ID_KEY);
        map.put(F_NATURAL_KEYS, F_NATURAL_KEYS);
        map.put(F_HIBERNATE_ANNOTATIONS, S1);
        return map;
    }
}
