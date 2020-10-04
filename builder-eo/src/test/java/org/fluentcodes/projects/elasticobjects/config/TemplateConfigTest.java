package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
//import org.fluentcodes.projects.elasticobjects.testitemprovider.TestObjectProvider;

//import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_TEMPLATE_MAIN;
//import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_TEMPLATE_TEST;


/**
 * Created by Werner on 04.11.2016.
 */
public class TemplateConfigTest {
    private static final Logger LOG = LogManager.getLogger(FileConfigTest.class);
/*
    @Test
    public void findConfigInCache()  {
        final TemplateConfig config = TestObjectProvider.EO_CONFIGS_CACHE.findTemplate(T_STATIC_FROM_XLSX_TPL);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, config.getDescription());
    }

    @Test
    public void readConfigClassPath()  {
        final Map<String, Config> map = TestConfig.readClassPathConfig(TemplateConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(T_STATIC_FROM_XLSX_TPL));
    }

    @Test
    public void readMapMain()  {
        final Map map = TestConfig.readMapFromFile(CONFIG_TEMPLATE_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(T_STATIC_FROM_XLSX_TPL));
    }

    @Test
    public void readConfigMain()  {
        final Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_TEMPLATE_MAIN, TemplateConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(T_STATIC_FROM_XLSX_TPL));
    }

    @Test
    public void readMapTest()  {
        final Map map = TestConfig.readMapFromFile(CONFIG_TEMPLATE_TEST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(T_TEST_MODELS_CONTROL));
    }

    @Test
    public void readConfigTest()  {
        final Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_TEMPLATE_TEST, TemplateConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(T_TEST_MODELS_CONTROL));
    }

 */
}
