package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.B_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_TEMPLATE_MAIN;

/**
 * Created by Werner on 04.11.2016.
 */
public class TemplateConfigTest {
    private static final Logger LOG = LogManager.getLogger(TemplateConfigTest.class);

    @Test
    public void readTemplateConfigMain()  {
        Map<String, Config> configMap = TestConfig.readConfigMapFromFile(CONFIG_TEMPLATE_MAIN, TemplateConfig.class);
        Assert.assertNotNull(configMap.get(T_CONFIG_BUILDER_SETTER_TPL));
        Assert.assertNotNull(configMap.get(T_CONSTANTS_LOOP_TPL));
        Assert.assertNotNull(configMap.get(T_CONSTANTS_CREATE_TPL));
    }

    @Test
    public void readJsonConfigTestAsMap()  {
        Map map = TestConfig.readMapFromFile(CONFIG_TEMPLATE_MAIN);
    }


}
