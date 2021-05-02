package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.10.2021.
 */

public class FileFactory extends ConfigFactory<FileConfig, FileBean> {
    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    public Map<String, FileBean> createBeanMap(ConfigMaps configMaps) {
        EO eoRoot = EoRoot.ofClass(configMaps, readConfigFiles(FileConfig.class), Map.class, FileBean.class);
        return (Map<String, FileBean>)eoRoot.get();
    }

    /**
     * Create a config map from a bean map.
     * @return the config map
     */
    @Override
    public Map<String, FileConfig> createConfigMap(ConfigMaps configMaps) {
        Map<String, FileConfig> configMap = new TreeMap<>();
        Map<String, FileBean> beanMap = createBeanMap(configMaps);
        for (String key: beanMap.keySet()) {
            configMap.put(key, new FileConfig(beanMap.get(key)));
        }
        return configMap;
    }
}
