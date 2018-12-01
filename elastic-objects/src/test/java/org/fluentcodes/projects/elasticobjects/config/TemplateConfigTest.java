package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;

import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
/**
 * @author Werner Diwischek
 * @since 16.5.18.
 */
public class TemplateConfigTest {
    private static final Logger LOG = LogManager.getLogger(TemplateConfigTest.class);

    @Test
    public void findCache() throws Exception {
        final TemplateConfig config = TestObjectProvider.EO_CONFIGS_CACHE
                .findTemplate(T_SIMPLE_INSERT_WITH_PATH);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertEquals(INFO_COMPARE_FAILS, T_SIMPLE_INSERT_WITH_PATH, config.getTemplateKey());
    }

    @Test
    public void readCacheTest() throws Exception {
        Map<String, Config> configMap = TestConfig.readConfigMapFromFile(CONFIG_TEMPLATE_TEST, TemplateConfig.class);
        TemplateConfig config = (TemplateConfig) configMap.get(T_SIMPLE_INSERT_WITH_PATH);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertEquals(INFO_COMPARE_FAILS, T_SIMPLE_INSERT_WITH_PATH, config.getTemplateKey());
    }

    @Test
    public void readMapTest() throws Exception {
        Map configMap = TestConfig.readMapFromFile(CONFIG_TEMPLATE_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, configMap);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS, configMap.size()>0);
    }

    @Test
    public void readMapMain() throws Exception {
        Map configMap = TestConfig.readMapFromFile(CONFIG_TEMPLATE_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, configMap);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS, configMap.size()>0);
    }
}
