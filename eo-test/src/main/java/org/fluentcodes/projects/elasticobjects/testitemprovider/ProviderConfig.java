package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.models.*;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class ProviderConfig {
    public static void createConfig(Map<String, Object> map) {
        try {
            addMap(map, Model.ID, 1L,
                    Model.CREATION_DATE, SAMPLE_DATE,
                    Model.DESCRIPTION, Model.DESCRIPTION,
                    Model.NATURAL_ID, Model.NATURAL_ID,
                    F_EXPANDED, S1,
                    ModelConfig.MODULE, ModelConfig.MODULE,
                    ModelConfig.SUB_MODULE, ModelConfig.SUB_MODULE);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Map<String, Object> createModelConfigMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(ModelConfig.MODEL_KEY, ModelConfig.MODEL_KEY);
        map.put(ModelConfig.DB_PARAMS, createDbParams());
        map.put(ModelConfig.EO_PARAMS, createEoParams());
        map.put(ModelConfig.VIEW_PARAMS, null);
        map.put(ModelConfig.CUSTOM_PARAMS, null);
        map.put(ModelConfig.FIELD_KEYS, null);
        map.put(ModelConfig.PACKAGE_PATH, ModelConfig.PACKAGE_PATH);
        map.put(ModelConfig.PACKAGE_GROUP, ModelConfig.PACKAGE_GROUP);
        map.put(Model.AUTHOR, Model.AUTHOR);
        map.put(ModelConfig.SUPER_KEY, ModelConfig.SUPER_KEY);
        map.put(ModelConfig.INTERFACES, ModelConfig.INTERFACES);
        createConfig(map);
        return map;
    }

    public static Map<String, Object> createEoParams() {
        Map<String, Object> map = new HashMap();
        map.put(EOParams.CREATE, true);
        map.put(EOParams.SHAPE_TYPE, ShapeTypes.MAP.name());
        map.put(EOParams.DEFAULT_IMPLEMENTATION, EOParams.DEFAULT_IMPLEMENTATION);
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
