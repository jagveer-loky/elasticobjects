package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.CONFIG_FIELD_MAIN;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 04.11.2016.
 */
public class FieldConfig2Test extends TestHelper {

    @Test
    public void findFieldConfigInModelCache() throws Exception {
        TestHelper.printStartMethod();
        final ModelInterface fieldModel = TestEOProvider.EO_CONFIGS.findModel(FieldConfig.class);
        Assert.assertEquals(FieldConfig.class.getSimpleName(), fieldModel.getModelKey());
        Assert.assertEquals(FieldConfig.class, fieldModel.getModelClass());
    }

    @Test
    public void findFieldConfigInCache() throws Exception {
        TestHelper.printStartMethod();
        final FieldConfig fieldConfig = TestEOProvider.EO_CONFIGS.findField(F_SUB_TEST_MAP);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, fieldConfig);
        Assert.assertNull(INFO_NULL_FAILS, fieldConfig.getPathPattern());
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS, fieldConfig.isFilterNothing());
    }

    @Test
    public void readConfigClassPath() throws Exception {
        Map<String, Config> map = TestConfig.readClassPathConfig(FieldConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_MODEL_KEY));
    }

    @Test
    public void readConfigMain() throws Exception {
        Map<String, Config> map = TestConfig.readConfigMapFromFile(CONFIG_FIELD_MAIN, FieldConfig.class);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertTrue(map.containsKey(F_TEST_LONG));
    }

    @Test
    public void readMapMain() throws Exception {
        Map map = TestConfig.readMapFromFile(CONFIG_FIELD_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_TEST_STRING));
    }

}
