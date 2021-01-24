package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

/**
 * Created by Werner on 22.1.2021.
 */
public class ModelConfigsMap extends ConfigConfigMap {
    public static final ModelConfig DEFAULT_MODEL = new ModelConfigMap(new ModelBean(Map.class, ShapeTypes.MAP));
    protected ModelConfigsMap(Scope scope)  {
        super(scope, ModelConfigObject.class);
    }

    @Override
    protected Map<String, ConfigConfigInterface> initMap() {
        Map<String, ConfigConfigInterface> modelConfigMap = new ModelBeanMap(getScope()).createConfigMap();
        if (getScope() == Scope.DEV) return modelConfigMap;
        for (ConfigConfigInterface modelConfig: modelConfigMap.values()) {
            ((ModelConfig)modelConfig).resolve(modelConfigMap);
        }
        for (ConfigConfigInterface modelConfig: modelConfigMap.values()) {
            ((ModelConfig)modelConfig).resolveSuper();
        }
        return modelConfigMap;
    }
}
