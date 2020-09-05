package org.fluentcodes.projects.elasticobjects.config;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC.CONFIG_H_TEST;
import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC_TEST.H_H2_MEM_RIGHT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;

/**
 * https://stackoverflow.com/questions/7267790/does-junit-execute-test-cases-sequentially
 * https://stackoverflow.com/questions/9667944/how-to-make-junit-test-cases-to-run-in-sequential-order
 * http://junit.org/junit4/faq.html
 * http://www.marcphilipp.de/blog/2011/12/22/junit-rules/
 * Created by Werner on 11.10.2016.
 */

public class HConfigTest {
    private static final Logger LOG = LogManager.getLogger(HConfigTest.class);

    @Test
    public void readCache()  {
        DbConfig config = (DbConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(DbConfig.class, H_H2_MEM_RIGHT);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
        Assert.assertEquals(H_H2_MEM_RIGHT, config.getDbKey());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(HConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(H_H2_MEM_RIGHT));
    }

    @Test
    public void readMapTest()  {
        Map map = TestConfig.readMapFromFile(CONFIG_H_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(H_H2_MEM_RIGHT));
    }

    @Test
    public void readConfigTest()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_H_TEST, HConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(H_H2_MEM_RIGHT));
    }
}
