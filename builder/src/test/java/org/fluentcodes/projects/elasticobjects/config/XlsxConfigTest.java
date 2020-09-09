package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.fluentcodes.projects.elasticobjects.models.Config;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.CONFIG_XLSX_MAIN;

/**
 * Created by Werner on 04.11.2016.
 */
public class XlsxConfigTest {
    private static final Logger LOG = LogManager.getLogger(XlsxConfigTest.class);
/*
    @Test
    public void readConfigMain()  {
        Map<String, Config> configMap = TestConfig.readConfigMapFromFile(CONFIG_XLSX_MAIN, XlsxConfig.class);
    }

    @Test
    public void readConfigsMainAsMap()  {
        Map map = TestConfig.readMapFromFile(CONFIG_XLSX_MAIN);
    }
*/
}
