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
                    F_EXPANDED, S1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Map<String, Object> createModelConfigMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(ModelConfig.MODEL_KEY, ModelConfig.MODEL_KEY);
        map.put(ModelConfig.FIELD_KEYS, null);
        map.put(Model.AUTHOR, Model.AUTHOR);
        map.put(ModelConfig.SUPER_KEY, ModelConfig.SUPER_KEY);
        map.put(ModelConfig.INTERFACES, ModelConfig.INTERFACES);
        createConfig(map);
        return map;
    }

    public static Map<String, Object> createEoParams() {
        Map<String, Object> map = new HashMap();
        map.put(PropertiesModelAccessor.CREATE, true);
        map.put(PropertiesModelAccessor.SHAPE_TYPE, ShapeTypes.MAP.name());
        map.put(PropertiesModelAccessor.DEFAULT_IMPLEMENTATION, PropertiesModelAccessor.DEFAULT_IMPLEMENTATION);
        return map;
    }

    public static Map<String, Object> createDbParams() {
        Map<String, Object> map = new HashMap();
        map.put(PropertiesFieldAccessor.TABLE, PropertiesFieldAccessor.TABLE);
        map.put(PropertiesModelAccessor.ID_KEY, PropertiesModelAccessor.ID_KEY);
        map.put(PropertiesModelAccessor.NATURAL_KEYS, PropertiesModelAccessor.NATURAL_KEYS);
        map.put(PropertiesModelAccessor.HIBERNATE_ANNOTATIONS, S1);
        return map;
    }
}
