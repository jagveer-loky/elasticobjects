package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public abstract class ModelFactory extends ConfigFactory<ModelBean, ModelConfigInterface> {

    protected ModelFactory(Scope scope) {
        super(scope, ModelBean.class, ModelConfig.class);
    }
    /**
     * Create a config map from a bean map.
     * @return the config map
     */
    @Override
    public  Map<String, ModelConfigInterface> createConfigMap(ConfigMaps configMaps) {
        Map<String, ModelConfigInterface> configMap = new TreeMap<>();
        Map<String, ModelBean> beanMap = createBeanMap(configMaps);
        for (Map.Entry<String, ModelBean> entry: beanMap.entrySet()) {

            Optional<String> filterScope = getScope().filter(entry.getKey());
            if (!filterScope.isPresent()) {
                continue;
            }
            final String key = filterScope.get();
            ModelBean bean = entry.getValue();
            bean.resolveSuper(beanMap, true);
            ShapeTypes shapeType = bean.getShapeType();
            if (shapeType == ShapeTypes.SCALAR || shapeType == ShapeTypes.ENUM) {
                configMap.put(key, new ModelConfigScalar(entry.getValue()));
            }
            else if (shapeType == ShapeTypes.MAP) {
                configMap.put(key, new ModelConfigMap(entry.getValue()));
            }
            else if (shapeType == ShapeTypes.LIST) {
                configMap.put(key, new ModelConfigList(entry.getValue()));
            }
            else {
                if (bean.hasConfigModelKey() && !bean.getConfigModelKey().equals(ModelConfigObject.class.getSimpleName())) {
                    configMap.put(key, (ModelConfigInterface) bean.createConfig(bean.deriveConfigClass()));
                }
                else {
                    configMap.put(key, new ModelConfigObject(entry.getValue()));
                }
            }
        }
        for (Map.Entry<String, ModelConfigInterface> entry: configMap.entrySet()) {
            ((ModelConfig)entry.getValue()).resolve(configMap);
        }
        return configMap;
    }

    protected Map<String, ModelConfigInterface> createConfigMap() {
        return createConfigMap(new ConfigMaps(Scope.DEV));
    }
}
