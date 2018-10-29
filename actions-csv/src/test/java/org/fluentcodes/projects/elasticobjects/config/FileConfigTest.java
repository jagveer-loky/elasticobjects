package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.FILE_SOURCE_CSV;
import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.FILE_TARGET_CSV;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_FILE_TEST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;

/**
 * Created by Werner on 14.08.2018.
 */
public class FileConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(FileConfigTest.class);

    @Test
    public void findConfigInCache() throws Exception {
        FileConfig config = TestObjectProvider.EO_CONFIGS_CACHE.findFile(FILE_SOURCE_CSV);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath() throws Exception {
        Map<String, Config> map = TestConfig.readClassPathConfig(FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_SOURCE_CSV));
    }

    @Test
    public void readMapTest() throws Exception {
        Map map = TestConfig.readMapFromFile(CONFIG_FILE_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_TARGET_CSV));
    }

    @Test
    public void readConfigTest() throws Exception {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_FILE_TEST, FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_TARGET_CSV));
    }
}

