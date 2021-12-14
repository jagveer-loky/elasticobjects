package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

public class ProviderConfigMaps {
    public static final ConfigMaps CONFIG_MAPS = new ConfigMaps(Scope.TEST);

    public static final EoRoot createEoWithClasses(Class... classes) {
        return EoRoot.ofClass(CONFIG_MAPS, classes);
    }

    public static final EoRoot createEo() {
        return EoRoot.of(CONFIG_MAPS);
    }

    public static final EoRoot createEo(Object value) {
        return EoRoot.ofValue(CONFIG_MAPS, value);
    }

    public static final ModelConfig findModel(final Class eoClass) {
        return CONFIG_MAPS.findModel(eoClass);
    }

    public static final ModelBean findModelBean(final Class eoClass) {
        Map map = findModelMap(eoClass);
        return new ModelBean(map);
    }

    public static final Map findModelMap(final Class eoClass) {
        ModelConfig config = CONFIG_MAPS.findModel(eoClass);
        EO eo = EoRoot.ofClass(CONFIG_MAPS, Map.class);
        eo.setSerializationType(JSONSerializationType.STANDARD);
        eo.mapObject(config);
        return (Map) eo.get();
    }

    public static final Models createModels(Class... classes) {
        return new Models(CONFIG_MAPS, classes);
    }
}