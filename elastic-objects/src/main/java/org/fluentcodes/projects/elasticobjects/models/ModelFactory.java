package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;
import java.util.Optional;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public abstract class ModelFactory extends ConfigFactory<ModelBean, ModelInterface> {

    protected ModelFactory(final ConfigMaps configMaps) {
        super(configMaps, ModelBean.class, ModelConfig.class);
    }
    /**
     * Create a config map from a bean map.
     * @return the config map
     */
    @Override
    public  Map<String, ModelInterface> createConfigMap() {
        Map<String, ModelInterface> configMap = new TreeMap<>();
        Map<String, ModelBean> beanMap = createBeanMap();
        for (Map.Entry<String, ModelBean> entry: beanMap.entrySet()) {
            try {
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
                        (ModelInterface) bean.createConfig(bean.deriveConfigClass(),
                                getConfigMaps()));
                continue;
            }
            if (shapeType == ShapeTypes.SCALAR || shapeType == ShapeTypes.ENUM) {
                configMap.put(key,
                        (ModelInterface) bean.createConfig(bean.deriveConfigClass(ModelConfigScalar.class.getSimpleName()),
                                getConfigMaps()));
            }
            else if (shapeType == ShapeTypes.MAP) {
                configMap.put(key,
                        (ModelInterface) bean.createConfig(bean.deriveConfigClass(ModelConfigMap.class.getSimpleName()),
                                getConfigMaps()));
            }
            else if (shapeType == ShapeTypes.LIST) {
                configMap.put(key,
                        (ModelInterface) bean.createConfig(bean.deriveConfigClass(ModelConfigList.class.getSimpleName()),
                                getConfigMaps()));
            }
            else {
                configMap.put(key,
                        (ModelInterface) bean.createConfig(
                                bean.deriveConfigClass(ModelConfigObject.class.getSimpleName()),
                                getConfigMaps()));
            }

            }
            catch (Exception e) {
                throw new EoException(e);
            }
        }
        for (Map.Entry<String, ModelInterface> entry: configMap.entrySet()) {
            try {
                ((ModelConfig) entry.getValue()).resolve(configMap);
            }
            catch (Exception e) {
                throw new EoException(e);
            }
        }
        return configMap;
    }

}
