package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

//import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_FILE_MAIN;

/**
 * Created by Werner on 04.08.2018.
 */
public class FileConfigTest {
    private static final Logger LOG = LogManager.getLogger(FileConfigTest.class);
/*
    @Test
    public void findConfigInCache()  {
        FileConfig config = TestObjectProvider.EO_CONFIGS_CACHE.findFile(FILE_ACTIONS_XLSX);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(FileConfig.class);
        Config fileConfig = map.get(X_CALLS);
        Assert.assertNotNull(fileConfig);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_ACTIONS_XLSX));
    }

    @Test
    public void readMapMain()  {
        Map map = TestConfig.readMapFromFile(CONFIG_FILE_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_ACTIONS_XLSX));
    }

    @Test
    public void readConfigMain()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_FILE_MAIN, FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_ACTIONS_XLSX));
    }

 */
}
