package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_FIELD_MAIN;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_EMPTY_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_NOT_NULL_FAILS;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.F_SHEET_NAME;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC.F_XLSX_KEY;


/**
 * Created by werner.diwischek on 09.12.17.
 */
public class FieldConfigsTest {
    private static final Logger LOG = LogManager.getLogger(FieldConfigsTest.class);

    @Test
    public void findConfigInCache()  {
        FieldConfig config = ProviderRootTestScope.EO_CONFIGS.findField(F_XLSX_KEY);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath()  {
        Map<String, Config> map = TestConfig.readClassPathConfig(FieldConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_SHEET_NAME));
    }

    @Test
    public void readMapMain()  {
        Map map = TestConfig.readMapFromFile(CONFIG_FIELD_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_XLSX_KEY));
    }

    @Test
    public void readConfigMain()  {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_FIELD_MAIN, FieldConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_SHEET_NAME));
    }
}
