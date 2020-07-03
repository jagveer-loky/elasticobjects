package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.M_CSV_CALL;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_MODEL_MAIN;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;

/**
 * Created by werner.diwischek on 09.12.17.
 */
public class ModelConfigsTest {
    private static final Logger LOG = LogManager.getLogger(ModelConfigsTest.class);

    @Test
    public void findConfigInCache()  {
        ModelConfig config = TestEOProvider.EO_CONFIGS.findModel(M_CSV_CALL);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(ModelConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(M_CSV_CALL));
    }

    @Test
    public void readMapMain()  {
        Map map = TestConfig.readMapFromFile(CONFIG_MODEL_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(M_CSV_CALL));
    }

    @Test
    public void readConfigMain()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_MODEL_MAIN, ModelConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(M_CSV_CALL));
    }

}
