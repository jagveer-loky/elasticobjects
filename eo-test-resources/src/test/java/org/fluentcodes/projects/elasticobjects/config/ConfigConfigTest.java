package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_CONFIG_MAIN;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.MODEL;

/**
 * Created by Werner on 1o.6.2018.
 */
public class ConfigConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ConfigConfigTest.class);

    @Test
    public void readConfigConfigMain() throws Exception {
        Map configMap = TestConfig.readConfigMapFromFile(CONFIG_CONFIG_MAIN, ConfigConfig.class);
        Assert.assertTrue(configMap.containsKey(MODEL));
    }
}
