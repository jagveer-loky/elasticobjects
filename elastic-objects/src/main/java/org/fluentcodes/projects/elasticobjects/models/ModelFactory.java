package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public abstract class ModelFactory extends ConfigFactory<ModelBean, ModelInterface> {

    protected ModelFactory(Scope scope) {
        super(scope, ModelBean.class, ModelConfig.class);
    }
    /**
     * Create a config map from a bean map.
     * @return the config map
     */
    @Override
    public  Map<String, ModelInterface> createConfigMap(ConfigMaps configMaps) {
        Map<String, ModelInterface> configMap = new TreeMap<>();
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
            if (bean.hasConfigModelKey()) {
                configMap.put(key,
                        (ModelInterface) bean.createConfig(bean.deriveConfigClass()));
                continue;
            }
            if (shapeType == ShapeTypes.SCALAR || shapeType == ShapeTypes.ENUM) {
                configMap.put(key,
                        (ModelInterface) bean.createConfig(bean.deriveConfigClass(ModelConfigScalar.class.getSimpleName())));
            }
            else if (shapeType == ShapeTypes.MAP) {
                configMap.put(key,
                        (ModelInterface) bean.createConfig(bean.deriveConfigClass(ModelConfigMap.class.getSimpleName())));
            }
            else if (shapeType == ShapeTypes.LIST) {
                configMap.put(key,
                        (ModelInterface) bean.createConfig(bean.deriveConfigClass(ModelConfigList.class.getSimpleName())));
            }
            else {
                configMap.put(key,
                        (ModelInterface) bean.createConfig(bean.deriveConfigClass(ModelConfigObject.class.getSimpleName())));
            }
        }
        for (Map.Entry<String, ModelInterface> entry: configMap.entrySet()) {
            ((ModelConfig)entry.getValue()).resolve(configMap);
        }
        return configMap;
    }

    protected Map<String, ModelInterface> createConfigMap() {
        return createConfigMap(new ConfigMaps(Scope.DEV));
    }
}
