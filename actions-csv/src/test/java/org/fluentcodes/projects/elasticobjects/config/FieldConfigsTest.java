package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.F_CSV_KEY;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_FIELD_MAIN;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;


/**
 * Created by werner.diwischek on 09.12.17.
 */
public class FieldConfigsTest {
    private static final Logger LOG = LogManager.getLogger(FieldConfigsTest.class);

    @Test
    public void findConfigInCache() throws Exception {
        FieldConfig config = TestObjectProvider.EO_CONFIGS_CACHE.findField(F_CSV_KEY);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath() throws Exception {
        Map<String, Config> map = TestConfig.readClassPathConfig(FieldConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_CSV_KEY));
    }

    @Test
    public void readMapMain() throws Exception {
        Map map = TestConfig.readMapFromFile(CONFIG_FIELD_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_CSV_KEY));
    }

    @Test
    public void readConfigMain() throws Exception {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_FIELD_MAIN, FieldConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_CSV_KEY));
    }
}
