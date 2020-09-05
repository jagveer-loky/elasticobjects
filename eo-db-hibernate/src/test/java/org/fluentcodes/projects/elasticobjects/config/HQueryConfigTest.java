package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC.CONFIG_H_QUERY_TEST;
import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC_TEST.HQ_H2_FILE_BASIC_TEST;
import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC_TEST.H_H2_FILE_BASIC;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;

/**
 * Created by Werner on 11.10.2016.
 */
public class HQueryConfigTest {
    private static final Logger LOG = LogManager.getLogger(HQueryConfigTest.class);

    @Test
    public void findConfigInCache()  {
        HQueryConfig config = (HQueryConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(HQueryConfig.class, HQ_H2_FILE_BASIC_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
        Assert.assertEquals(HQ_H2_FILE_BASIC_TEST, config.getHibernateModelKey());
        Assert.assertEquals(H_H2_FILE_BASIC, config.getHConfig().getDbConfig().getDbKey());
        Assert.assertEquals(H_H2_FILE_BASIC, config.getHConfig().getHibernateKey());
        Assert.assertEquals("sa", config.getHConfig().getDbConfig().getHostConfig().getUser());
        Assert.assertEquals(DbTypes.H2, config.getHConfig().getDbConfig().getDbType());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(HQueryConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(HQ_H2_FILE_BASIC_TEST));
    }

    @Test
    public void readMapTest()  {
        Map map = TestConfig.readMapFromFile(CONFIG_H_QUERY_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(HQ_H2_FILE_BASIC_TEST));
    }

    @Test
    public void readConfigTest()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_H_QUERY_TEST, HQueryConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(HQ_H2_FILE_BASIC_TEST));
    }

}
