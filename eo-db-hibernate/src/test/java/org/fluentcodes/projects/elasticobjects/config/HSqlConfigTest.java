package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TestHProvider;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC.CONFIG_H_SQL_TEST;
import static org.fluentcodes.projects.elasticobjects.H_EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 28.1.2018.
 */
public class HSqlConfigTest {
    protected static final HSqlConfig HIBERNATE_SQL_DROP = TestHProvider.findHibernateSqlCache(HS_H2_BASIC_TEST_DROP);
    protected static final HSqlConfig HIBERNATE_SQL_CREATE = TestHProvider.findHibernateSqlCache(HS_H2_BASIC_CREATE);

    protected static final HConfig HIBERNATE = TestHProvider.findHibernateCache(H_H2_FILE_BASIC);
    protected static final ModelInterface BASIC_TEST = TestObjectProvider.findModel(M_BASIC_TEST);
    protected static final ModelInterface SUB_TEST = TestObjectProvider.findModel(M_SUB_TEST);
    private static final Logger LOG = LogManager.getLogger(HSqlConfigTest.class);


    @Test
    public void drop_BasicTestByCachedSql()  {

        Assert.assertNotNull(HIBERNATE_SQL_DROP);
        HIBERNATE_SQL_DROP.execute();
        List subTestList = HIBERNATE.readAll(SUB_TEST);
        Assert.assertTrue(subTestList.size() > 0);
        try {
            HIBERNATE.readAll(BASIC_TEST);
            Assert.fail("Exception should be thrown when null for adapterValue is used");
        } catch (Exception e) {
            Assert.assertEquals("could not prepare statement", e.getMessage());
        }
        HIBERNATE.init();
    }

    @Test
    public void create_BasicByCachedSql()  {

        Assert.assertNotNull(HIBERNATE_SQL_CREATE);
        HIBERNATE_SQL_CREATE.execute();
        List subTestList = HIBERNATE.readAll(SUB_TEST);
        Assert.assertEquals(0, subTestList.size());
        List basicTestList = HIBERNATE.readAll(BASIC_TEST);
        Assert.assertEquals(0, basicTestList.size());
        HIBERNATE.init();
    }

    @Test
    public void findConfigInCache()  {
        HSqlConfig config = (HSqlConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(HSqlConfig.class, HS_H2_BASIC_CREATE);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
        Assert.assertEquals(H_H2_MEM_BASIC, config.getHConfig().getDbConfig().getDbKey());
        Assert.assertEquals(H_H2_MEM_BASIC, config.getHConfig().getHibernateKey());
        Assert.assertEquals("sa", config.getHConfig().getDbConfig().getHostConfig().getUser());
        Assert.assertEquals(DbTypes.H2, config.getHConfig().getDbConfig().getDbType());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(HSqlConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(HS_H2_BASIC_CREATE));
    }

    @Test
    public void readMapTest()  {
        Map map = TestConfig.readMapFromFile(CONFIG_H_SQL_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(HS_H2_BASIC_CREATE));
    }

    @Test
    public void readConfigTest()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_H_SQL_TEST, HSqlConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(HS_H2_BASIC_CREATE));
    }


}

