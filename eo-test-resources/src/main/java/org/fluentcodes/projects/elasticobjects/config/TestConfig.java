package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.JSONReader;
import org.junit.Assert;

import java.util.Map;

/**
 * created 22.7.2018
 */
public class TestConfig {

    public static Map readMapFromFile(final String configFile) throws Exception {
        Map map = JSONReader.readMapBean(TestEOProvider.EO_CONFIGS, configFile, null);
        Assert.assertNotNull(map);
        for (Object key : map.keySet()) {
            Assert.assertNotNull(map.get(key));
            new ModelConfig.Builder()
                    .build(TestEOProvider.EO_CONFIGS, (Map) map.get(key));
        }
        return map;
    }

    public static Map<String, Config> readConfigMapFromFile(final String configFile, final Class<? extends Config> configClass) throws Exception {
        Map<String, Config> configMap = new EOConfigReader(TestEOProvider.EO_CONFIGS, configClass)
                .read(configFile);
        Assert.assertNotNull(configMap);
        return configMap;
    }


    public static Map<String, Config> readClassPathConfig(final Class<? extends Config> configClass) throws Exception {
        Map<String, Config> configMap = new EOConfigReader(TestEOProvider.EO_CONFIGS, configClass)
                .read();
        Assert.assertNotNull(configMap);
        return configMap;
    }
}
