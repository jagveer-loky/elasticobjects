package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.fluentcodes.projects.elasticobjects.models.Config;
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
public class ConfigResourcesFileTest {
    private static final Logger LOG = LogManager.getLogger(ConfigResourcesFileTest.class);

    @Test
    public void findConfigInCache()  {
        FileConfig config = ProviderRootTestScope.EO_CONFIGS.findFile(FILE_SOURCE_CSV);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_SOURCE_CSV));
    }

    @Test
    public void readMapTest()  {
        Map map = TestConfig.readMapFromFile(CONFIG_FILE_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_TARGET_CSV));
    }

    @Test
    public void readConfigTest()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_FILE_TEST, FileConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(FILE_TARGET_CSV));
    }
}

