package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.DB_EO_STATIC.CONFIG_DB_MAIN;
import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC_TEST.DB_H2_MEM_RIGHT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;

/**
 * Created by Werner on 12.08.2018.
 */
public class DbConfigTest {
    private static final Logger LOG = LogManager.getLogger(DbConfigTest.class);

    @Test
    public void readCache()  {
        DbConfig config = (DbConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(DbConfig.class, DB_H2_MEM_RIGHT);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
        Assert.assertEquals(DB_H2_MEM_RIGHT, config.getDbKey());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(DbConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(DB_H2_MEM_RIGHT));
    }

    @Test
    public void readMapMain()  {
        Map map = TestConfig.readMapFromFile(CONFIG_DB_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(DB_H2_MEM_RIGHT));
    }

    @Test
    public void readConfigMain()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_DB_MAIN, DbConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(DB_H2_MEM_RIGHT));
    }
}

