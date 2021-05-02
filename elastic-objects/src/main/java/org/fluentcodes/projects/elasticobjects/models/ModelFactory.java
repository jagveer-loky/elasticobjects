package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.UnmodifiableCollection;
import org.fluentcodes.projects.elasticobjects.UnmodifiableList;
import org.fluentcodes.projects.elasticobjects.UnmodifiableMap;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public abstract class ModelFactory extends ConfigFactory<ModelConfigInterface, ModelBean> {
    /**
     * Create a config map from a bean map.
     * @return the config map
     */
    @Override
    public Map<String, ModelConfigInterface> createConfigMap(ConfigMaps configMaps) {
        Map<String, ModelConfigInterface> configMap = new TreeMap<>();
        Map<String, ModelBean> beanMap = createBeanMap(configMaps);
        for (String key: beanMap.keySet()) {
            ModelBean bean = beanMap.get(key);
            if (bean.isScalar()) {
                configMap.put(key, new ModelConfigScalar(beanMap.get(key)));
            }
            else if (bean.isMap()) {
                configMap.put(key, new ModelConfigMap(beanMap.get(key)));
            }
            else if (bean.isList()) {
                configMap.put(key, new ModelConfigList(beanMap.get(key)));
            }
            else if (bean.isObject()) {
                configMap.put(key, new ModelConfigObject(beanMap.get(key)));
            }
        }
        return configMap;
    }

    void resolveSuper(Map<String, ModelBean> modelMap) {
        for (ModelBean modelBean: modelMap.values()) {
            modelBean.resolveSuper(modelMap, true);
        }
    }
}
