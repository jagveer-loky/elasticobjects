package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.models.*;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_EXPANDED;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.addMap;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S1;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_DATE;

public class ProviderConfig {
    public static void createConfig(Map<String, Object> map) {
        try {
            addMap(map, Base.ID, 1L,
                    Base.CREATION_DATE, SAMPLE_DATE,
                    Base.DESCRIPTION, Base.DESCRIPTION,
                    Base.NATURAL_ID, Base.NATURAL_ID,
                    F_EXPANDED, S1);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static final Map<String, Object> createModelConfigMap() {
        Map<String, Object> map = new HashMap<>();
        map.put(ModelConfig.MODEL_KEY, ModelConfig.MODEL_KEY);
        map.put(ModelConfig.FIELD_KEYS, null);
        map.put(Base.AUTHOR, Base.AUTHOR);
        map.put(ModelConfig.SUPER_KEY, ModelConfig.SUPER_KEY);
        map.put(ModelConfig.INTERFACES, ModelConfig.INTERFACES);
        createConfig(map);
        return map;
    }

    public static Map<String, Object> createEoParams() {
        Map<String, Object> map = new HashMap();
        map.put(ModelProperties.CREATE, true);
        map.put(ModelProperties.SHAPE_TYPE, ShapeTypes.MAP.name());
        map.put(ModelProperties.DEFAULT_IMPLEMENTATION, ModelProperties.DEFAULT_IMPLEMENTATION);
        return map;
    }

    public static Map<String, Object> createDbParams() {
        Map<String, Object> map = new HashMap();
        map.put(FieldProperties.TABLE, FieldProperties.TABLE);
        map.put(ModelProperties.ID_KEY, ModelProperties.ID_KEY);
        map.put(ModelProperties.NATURAL_KEYS, ModelProperties.NATURAL_KEYS);
        map.put(ModelProperties.HIBERNATE_ANNOTATIONS, S1);
        return map;
    }
}
