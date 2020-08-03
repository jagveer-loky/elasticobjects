package org.fluentcodes.projects.elasticobjects.calls.json;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.lists.ScsConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMap;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMapImmutable;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.J_SIMPLE_INSERT_WITH_PATH;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;

/**
 * Created by Werner on
 *
 * @author Werner Diwischek
 * @since 15.04.2018.
 */
public class JsonConfigTest {
    private static final Logger LOG = LogManager.getLogger(JsonConfigTest.class);
    @Test
    public void testFindModelConfig()  {
        ModelConfig config = ProviderRootTest.EO_CONFIGS.findModel("JsonConfig");
        Assertions.assertThat(config).isNotNull();
        config.resolve();
    }


    @Test
    public void findConfigInCache()  {
        JsonConfig config = ProviderRootTest.EO_CONFIGS.findJson(J_SIMPLE_INSERT_WITH_PATH);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
        Assert.assertEquals("file:classpath/data/SimpleInsertWithPath.json", config.getUrlPath());
    }

    @Test
    public void testResolveAllConfigs()  {
        Set<String> keys = ProviderRootTest.EO_CONFIGS.getConfigKeys(ScsConfig.class);
        Assertions.assertThat(keys).isNotEmpty();
        for (String key: keys) {
            Assertions.assertThat(key).isNotEmpty();
            JsonConfig config = ProviderRootTest.EO_CONFIGS.findJson(key);
            Assertions.assertThat(config).isNotNull();
            config.resolve();
        }
    }

    @Test
    public void testReadConfig()  {
        EOConfigMap map = new EOConfigMapImmutable(ProviderRootTest.EO_CONFIGS, JsonConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.find(J_SIMPLE_INSERT_WITH_PATH));
    }
}
