package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;
import java.util.TreeMap;

/**
 * Created by Werner on 21.1.2021.
 */

public class DbSqlFactory extends ConfigFactory<DbSqlConfig, DbSqlBean> {

    /**
     * Default init map.
     * @return the expanded final configurations.
     */
    public Map<String, DbSqlBean> createBeanMap(ConfigMaps configMaps) {
        EO eoRoot = EoRoot.ofClass(configMaps, readConfigFiles(DbSqlConfig.class), Map.class, DbSqlBean.class);
        return (Map<String, DbSqlBean>)eoRoot.get();
    }

    /**
     * Create a config map from a bean map.
     * @return the config map
     */
    @Override
    public Map<String, DbSqlConfig> createConfigMap(ConfigMaps configMaps) {
        Map<String, DbSqlConfig> configMap = new TreeMap<>();
        Map<String, DbSqlBean> beanMap = createBeanMap(configMaps);
        for (String key: beanMap.keySet()) {
            configMap.put(key, new DbSqlConfig(beanMap.get(key)));
        }
        return configMap;
    }
}
