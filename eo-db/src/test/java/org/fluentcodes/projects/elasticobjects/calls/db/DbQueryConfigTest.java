package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbQueryConfigTest {
    protected static final String DB_QUERY_SUB_TEST = "h2:mem:basic:SubTest";
    protected static final String DB_QUERY_BASIC_TEST = "h2:mem:basic:BasicTest";
    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(DbQueryConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbQueryConfig.class);
    }

    @Test
    public void resolveModel()  {
        ConfigModelChecks.resolve(DbQueryConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(DbQueryConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(DbQueryConfig.class);
    }
    
/*
    protected static final DbQueryConfig DB = DbProvider.findDbCache(DB_H2_MEM_BASIC);
    private static final Logger LOG = LogManager.getLogger(DbQueryConfigTest.class);

    @Test
    public void fromCache()  {

        final DbQueryConfig config = (DbQueryConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(DbQueryConfig.class, DB_QUERY_BASIC_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertEquals(M_BASIC_TEST, config.getModelKey());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(DbQueryConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(DB_QUERY_BASIC_TEST));
    }

    @Test
    public void readMapTest()  {
        Map map = TestConfig.readMapFromFile(CONFIG_DB_QUERY_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(DB_QUERY_BASIC_TEST));
    }

    @Test
    public void readConfigTest()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_DB_QUERY_TEST, DbQueryConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(DB_QUERY_BASIC_TEST));
    }

 */
}

