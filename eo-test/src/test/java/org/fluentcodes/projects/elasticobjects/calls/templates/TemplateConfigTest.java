package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMap;
import org.fluentcodes.projects.elasticobjects.models.EOConfigMapImmutable;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
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
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(TemplateConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(TemplateConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(TemplateConfig.class);
    }

    @Test
    public void testReadConfig() {
        EOConfigMap map = new EOConfigMapImmutable(ProviderRootTestScope.EO_CONFIGS, TemplateConfig.class);
        TemplateConfig config = (TemplateConfig) map.find(TemplateContentExampleTest.STATIC_TEMPLATE_CONFIG_KEY);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertEquals(INFO_COMPARE_FAILS, TemplateContentExampleTest.STATIC_TEMPLATE_CONFIG_KEY, config.getKey());
    }
}
