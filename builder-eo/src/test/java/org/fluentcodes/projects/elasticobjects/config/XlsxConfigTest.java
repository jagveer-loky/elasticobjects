package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.BEO_STATIC.JSON_XLSX;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.CONFIG_XLSX_MAIN;

/**
 * Created by Werner on 04.11.2016.
 */
public class XlsxConfigTest {
    private static final Logger LOG = LogManager.getLogger(XlsxConfigTest.class);

    @Test
    public void findConfigInCache()  {
        final XlsxConfig config = (XlsxConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(XlsxConfig.class, JSON_XLSX);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath()  {
        final Map<String, Config> map = TestConfig.readClassPathConfig(XlsxConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(JSON_XLSX));
    }

    @Test
    public void readMapMain()  {
        final Map map = TestConfig.readMapFromFile(CONFIG_XLSX_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(JSON_XLSX));
    }

    @Test
    public void readConfigMain()  {
        final Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_XLSX_MAIN, XlsxConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(JSON_XLSX));
    }


}
