package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class HostFactory extends ConfigFactory<HostConfig, HostBean> {
    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    public Map<String, HostBean> createBeanMap(ConfigMaps configMaps) {
        EO eoRoot = EoRoot.ofClass(configMaps, readConfigFiles(HostConfig.class), Map.class, HostBean.class);
        return (Map<String, HostBean>)eoRoot.get();
    }

    /**
     * Create a config map from a bean map.
     * @return the config map
     */
    @Override
    public Map<String, HostConfig> createConfigMap(ConfigMaps configMaps) {
        Map<String, HostConfig> configMap = new TreeMap<>();
        Map<String, HostBean> beanMap = createBeanMap(configMaps);
        for (String key: beanMap.keySet()) {
            configMap.put(key, new HostConfig(beanMap.get(key)));
        }
        return configMap;
    }
}
