package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

public class ProviderRootTestScope {
    public static final ConfigMaps EO_CONFIGS = new ConfigMaps(Scope.TEST);

    public static final EO createEoWithClasses(Class... classes) {
        return EoRoot.ofClass(EO_CONFIGS, classes);
    }

    public static final EO createEo() {
        return EoRoot.of(EO_CONFIGS);
    }

    public static final EO createEo(Object value) {
        return EoRoot.ofValue(EO_CONFIGS, value);
    }

    public static final ModelConfig findModel (final Class eoClass) {
        return EO_CONFIGS.findModel(eoClass);
    }

    public static final ModelBean findModelBean(final Class eoClass) {
        Map map = findModelMap(eoClass);
        return new ModelBean(map);
    }

    public static final Map findModelMap(final Class eoClass) {
        ModelConfig config = EO_CONFIGS.findModel(eoClass);
        EO eo = EoRoot.ofClass(EO_CONFIGS, Map.class);
        eo.setSerializationType(JSONSerializationType.STANDARD);
        eo.mapObject(config);
        return (Map) eo.get();
    }
}