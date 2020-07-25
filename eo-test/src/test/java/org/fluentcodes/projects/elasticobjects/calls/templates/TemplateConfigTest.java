package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;

import org.fluentcodes.projects.elasticobjects.config.EOConfigMap;
import org.fluentcodes.projects.elasticobjects.config.EOConfigMapImmutable;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
/**
 * @author Werner Diwischek
 * @since 16.5.18.
 */
public class TemplateConfigTest {
    private static final Logger LOG = LogManager.getLogger(TemplateConfigTest.class);

    @Test
    public void findCache() {
        final TemplateConfig config = TestProviderRootTest.EO_CONFIGS
                .findTemplate(T_SIMPLE_INSERT_WITH_PATH);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertEquals(INFO_COMPARE_FAILS, T_SIMPLE_INSERT_WITH_PATH, config.getTemplateKey());
    }

    @Test
    public void testReadConfig() {
        EOConfigMap map = new EOConfigMapImmutable(TestProviderRootTest.EO_CONFIGS, TemplateConfig.class);
        TemplateConfig config = (TemplateConfig) map.get(T_SIMPLE_INSERT_WITH_PATH);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertEquals(INFO_COMPARE_FAILS, T_SIMPLE_INSERT_WITH_PATH, config.getTemplateKey());
    }
}
