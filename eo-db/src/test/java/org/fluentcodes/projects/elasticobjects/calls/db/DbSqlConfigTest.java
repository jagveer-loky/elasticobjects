package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbSqlConfigTest {
    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(DbSqlConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbSqlConfig.class);
    }

    @Test
    public void resolveModel()  {
        ConfigModelChecks.resolve(DbSqlConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(DbSqlConfig.class);
    }

    @Test
    public void compareConfigurations()  {
        ConfigChecks.compareConfigurations(DbSqlConfig.class);
    }
    
    
    /*
    protected static final DbSqlConfig DB_SQL_DROP = DbProvider.findDbSqlCache(DS_H2_MEM_BASIC_TEST_DROP);
    protected static final DbSqlConfig DB_SQL_CREATE = DbProvider.findDbSqlCache(DS_H2_MEM_BASIC_CREATE);

    protected static final DbConfig DB = DbProvider.findDbCache(DB_H2_MEM_BASIC);
    protected static final ModelInterface BASIC_TEST = TestObjectProvider.findModel(M_BASIC_TEST);
    protected static final ModelInterface SUB_TEST = TestObjectProvider.findModel(M_SUB_TEST);
    private static final Logger LOG = LogManager.getLogger(DbSqlConfigTest.class);


    @Test
    public void fromCache()  {

        final DbSqlConfig config = (DbSqlConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(DbSqlConfig.class, DS_H2_MEM_BASIC_TEST_DROP);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
        Assert.assertEquals(DS_H2_MEM_BASIC_TEST_DROP, config.getDbSqlKey());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(DbSqlConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
    }

    @Test
    public void readMapTest()  {
        Map map = TestConfig.readMapFromFile(CONFIG_DB_SQL_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
    }

    @Test
    public void readConfigTest()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_DB_SQL_TEST, DbSqlConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
    }
*/
    /**
     * For debugging purposes.
     *
     * @ on each error

    @Test
    public void pickModelCache()  {

        ModelInterface picked = TestObjectProvider.findModel("DbSqlConfig");
        picked.resolve();
    }


    @Ignore
    @Test
    public void drop_BasicTestByCachedSql()  {

        Assert.assertNotNull(DB_SQL_DROP);
        create_BasicByCachedSql();
        DbIO dbIO = new DbIO(DB_SQL_DROP.getDbCache());
        dbIO.execute(DB_SQL_DROP.getSqlList());
        dbIO = new DbIO(DB);
        List<Map<String, Object>> result = dbIO.readListMap("SELECT * from SubTest");
        Assert.assertTrue(result.size() == 0);
        try {
            dbIO = new DbIO(DB);
            result = dbIO.readListMap("SELECT * from BasicTest");
            Assert.fail("Exception should be thrown when null for adapterValue is used");
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Table \"BasicTest\" not found"));
        }
    }

    @Ignore
    @Test
    public void create_BasicByCachedSql()  {

        Assert.assertNotNull(DB_SQL_CREATE.getDbCache());
        DbIO dbIO = new DbIO(DB_SQL_CREATE.getDbCache());
        dbIO.execute(DB_SQL_CREATE.getSqlList());

        dbIO = new DbIO(DB);
        List<Map<String, Object>> result = dbIO.readListMap("select * from SubTest");
        Assert.assertEquals(0, result.size());

        dbIO = new DbIO(DB);
        result = dbIO.readListMap("Select * from BasicTest");
        Assert.assertEquals(0, result.size());
    }
    */


}

