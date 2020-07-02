package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 27.8.2018.
 */
public class FieldConfigTest extends TestHelper {

    @Test
    public void findFieldConfigInModelCache() throws Exception {
        TestHelper.printStartMethod();
        final ModelInterface fieldModel = TestEOProvider.EO_CONFIGS.findModel(FieldConfig.class.getSimpleName());
        Assert.assertEquals(FieldConfig.class.getSimpleName(), fieldModel.getModelKey());
        Assert.assertEquals(FieldConfig.class, fieldModel.getModelClass());
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
        Assert.assertTrue(map.containsKey(F_MODEL_KEYS));
    }

    @Test
    public void readMapMain() throws Exception {
        Map map = TestConfig.readMapFromFile(CONFIG_FIELD_MAIN);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_ID_KEY));
    }


    @Test
    public void getId() throws Exception {
        TestHelper.printStartMethod();
        final FieldConfig idConfig = TestEOProvider.EO_CONFIGS.findField(F_ID);
        Assert.assertEquals(Long.class, idConfig.getModelConfig().getModelClass());
        Assert.assertNotNull(idConfig.getEoFieldParams());
        Assert.assertFalse(idConfig.getEoFieldParams().isDeliverAction());
        Assert.assertEquals(Path.DELIMITER + Path.MATCHER, idConfig.getEoFieldParams().getPathPatternAsString());
    }

    @Test
    public void testBasicTestWithTestString() throws Exception {
        TestHelper.printStartMethod();
        FieldConfig fieldCacheDefinitions = TestEOProvider.EO_CONFIGS
                .findModel(BasicTest.class)
                .getField(F_TEST_STRING);
        Assert.assertNotNull(fieldCacheDefinitions);
        Assert.assertFalse(fieldCacheDefinitions.isUnique());
        Assert.assertFalse(fieldCacheDefinitions.isNotNull());
        //Assert.assertTrue(fieldDefinitions.isElementary());
        //Assert.assertTrue(fieldDefinitions.isScalar());
        Assert.assertEquals(new Integer(20), fieldCacheDefinitions.getLength());
    }

    @Test
    public void assertId() throws Exception {
        TestHelper.printStartMethod();
        FieldConfig field = TestEOProvider.EO_CONFIGS.findField(F_ID);
        Assert.assertEquals(F_ID, field.getFieldKey());
        Assert.assertEquals(F_ID, field.getFieldName());
        Assert.assertEquals(S_BOOLEAN, field.isUnique());
        Assert.assertEquals(S_BOOLEAN, field.isNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Long.class, field.getModelClass());
    }

    /**
     * Will test the alias thing to id.
     *
     * @throws Exception
     */
    @Test
    public void assertID() throws Exception {
        TestHelper.printStartMethod();
        FieldConfig field = TestEOProvider.EO_CONFIGS.findField(F_UPPER_ID_KEY);
        Assert.assertEquals(F_UPPER_ID, field.getFieldKey());
        Assert.assertEquals(F_UPPER_ID, field.getFieldName());
        Assert.assertEquals(S_BOOLEAN, field.isUnique());
        Assert.assertEquals(S_BOOLEAN, field.isNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Long.class, field.getModelClass());
    }

    /**
     * Will test the alias thing to id.
     *
     * @throws Exception
     */
    @Test
    public void assertTestObject() throws Exception {
        TestHelper.printStartMethod();
        FieldConfig field = TestEOProvider.EO_CONFIGS.findField(F_TEST_OBJECT);
        Assert.assertEquals(F_TEST_OBJECT, field.getFieldKey());
        Assert.assertEquals(F_TEST_OBJECT, field.getFieldName());
        Assert.assertEquals(false, field.isUnique());
        Assert.assertEquals(false, field.isNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Object.class, field.getModelClass());
    }

}
