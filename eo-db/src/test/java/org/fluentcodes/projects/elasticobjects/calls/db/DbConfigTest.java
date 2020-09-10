package org.fluentcodes.projects.elasticobjects.calls.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.junit.Test;

/**
 * Created by Werner on 12.10.2016.
 */
public class DbConfigTest {
    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(DbConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbConfig.class);
    }

    @Test
    public void resolveModel()  {
        ConfigModelChecks.resolve(DbConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(HostConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(HostConfig.class);
    }

    /*private static final DbConfig H2_DB = DbProvider.findDbCache(DB_H2_MEM_BASIC);
    private static final ModelInterface SUB_TEST = TestObjectProvider.findModel(SubTest.class);
    private static boolean initFlag = false;

    private static boolean init()  {
        if (initFlag) {
            return true;
        }
        return true;
    }

    @Test
    public void readCache()  {
        DbConfig config = (DbConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(DbConfig.class, DB_H2_MEM);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
        Assert.assertEquals(DB_H2_MEM, config.getDbKey());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(DbConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(DB_H2_MEM));
    }

    @Test
    public void readMapMain()  {
        Map map = TestConfig.readMapFromFile(CONFIG_DB_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(DB_H2_MEM));
    }

    @Test
    public void readConfigMain()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_DB_MAIN, DbConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(DB_H2_MEM));
    }


    @Test
    public void withKeyH2Mem()  {

        final DbConfig h2MemBasic = H2_DB;
        Assert.assertEquals("h2", h2MemBasic.getHostConfig().getHostKey());
        Assert.assertEquals("", h2MemBasic.getHostConfig().getHostName());
        Assert.assertEquals(DB_H2_MEM_BASIC, h2MemBasic.getDbKey());
        String url = h2MemBasic.getUrlPath();
        Connection connection = h2MemBasic.getConnection();
        LOG.info("Created connection " + connection.getMetaData());
        h2MemBasic.closeConnection();
    }

    @Ignore
    @Test
    public void DropBasicTest()  {

        init();
        DbIO dbIO = new DbIO(H2_DB);
        boolean executionFlag = dbIO.execute("Drop Table BasicTest");
        try {
            dbIO = new DbIO(H2_DB);
            List<Map<String, Object>> result = dbIO.readListMap("Select * from  BasicTest");
            Assert.fail("BasicTest should be dropped!");
        } catch (Exception e) {
            LOG.info("Exception thrown since table is dropped: " + e.getMessage());
            Assert.assertTrue(e.getMessage().contains("Table \"BasicTest\" not found"));
        }
        H2_DB.closeConnection();
    }

    @Test
    public void SelectBasicTest()  {

        init();
        DbIO dbIO = new DbIO(H2_DB);
        List<Map<String, Object>> result = dbIO.readListMap("Select * from  BasicTest");
        Assert.assertTrue(result.size() > 0);
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, result);
        H2_DB.closeConnection();
    }

     */
}

