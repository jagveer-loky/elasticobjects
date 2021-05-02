package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.10.2021.
 */

public class FieldFactory extends ConfigFactory<FieldConfig, FieldBean> {
    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    public Map<String, FieldBean> createBeanMap(ConfigMaps configMaps) {
        EO eoRoot = EoRoot.ofClass(configMaps, readConfigFiles(FieldConfig.class), Map.class, FieldBean.class);
        return (Map<String, FieldBean>)eoRoot.get();
    }

    /**
     * Create a config map from a bean map.
     * @return the config map
     */
    @Override
    public Map<String, FieldConfig> createConfigMap(ConfigMaps configMaps) {
        return new TreeMap<>();
    }
}
