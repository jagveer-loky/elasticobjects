package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

public class ProviderConfigMaps {
    public static final ConfigMaps CONFIG_MAPS = new ConfigMaps(Scope.TEST);
    public static final ConfigMaps CONFIG_MAPS_DEV = new ConfigMaps(Scope.DEV);

    public static final EO createEoWithClasses(Class... classes) {
        return EoRoot.ofClass(CONFIG_MAPS, classes);
    }

    public static final EO createEo() {
        return EoRoot.of(CONFIG_MAPS);
    }

    public static final EO createEo(Object value) {
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

    public static final EoRoot createEoWithClassesDev(Class... classes) {
        return EoRoot.ofClass(CONFIG_MAPS_DEV, classes);
    }

    public static final EoRoot createEoDev() {
        return EoRoot.of(CONFIG_MAPS_DEV);
    }

    public static final EO createEoDev(Object value) {
        return EoRoot.ofValue(CONFIG_MAPS_DEV, value);
    }
}