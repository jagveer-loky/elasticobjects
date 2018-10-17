package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_TEMPLATE_TEST;

/**
 * Created by werner.diwischek on 16.05.18.
 */
public class TemplateConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(TemplateConfigTest.class);

    @Test
    public void createTemplateCacheTest() throws Exception {
        TestHelper.printStartMethod();
        Map<String, Config> configMap = TestConfig.readConfigMapFromFile(CONFIG_TEMPLATE_TEST, TemplateConfig.class);
        TemplateConfig config = (TemplateConfig) configMap.get(EO_STATIC_TEST.T_SIMPLE_INSERT_WITH_PATH);
        Assert.assertNotNull(config);
        Assert.assertEquals(EO_STATIC_TEST.T_SIMPLE_INSERT_WITH_PATH, config.getTemplateKey());
    }

    @Test
    public void readFieldConfigMainAsMap() throws Exception {
        TestConfig.readMapFromFile(CONFIG_TEMPLATE_TEST);
    }
}
