package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMap;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMapImmutable;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
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
    public void givenFoundModel_whenCreateInstance_thenExceptionThrown()  {
        ConfigChecks.findModelAndCreateInstanceExceptionThrown(TemplateConfig.class);
    }

    @Test
    public void whenResolveConfigEntries_thenNoError()  {
        ConfigChecks.resolveConfigEntries(TemplateConfig.class);
    }

    @Test
    public void whenCompareConfigurations_thenEqual()  {
        ConfigChecks.compareConfigurations(TemplateConfig.class);
    }

    @Test
    public void testReadConfig() {
        EOConfigMap map = new EOConfigMapImmutable(ProviderRootTest.EO_CONFIGS, TemplateConfig.class);
        TemplateConfig config = (TemplateConfig) map.find(TemplateContentExampleTest.STATIC_TEMPLATE_CONFIG_KEY);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertEquals(INFO_COMPARE_FAILS, TemplateContentExampleTest.STATIC_TEMPLATE_CONFIG_KEY, config.getTemplateKey());
    }
}
