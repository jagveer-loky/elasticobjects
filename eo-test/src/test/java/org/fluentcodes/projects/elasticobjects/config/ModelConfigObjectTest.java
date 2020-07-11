package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutorResource;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_DESCRIPTION;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigObjectTest {
    private static final Logger LOG = LogManager.getLogger(ModelConfigObjectTest.class);

    @Test
    public void createExecutorAction()  {
        
        final ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(CallExecutorResource.class);
        Assert.assertEquals(ShapeTypes.BEAN, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
        try {
            CallExecutorResource CallExecutor = (CallExecutorResource) model.create();
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void assertBTTypeTest()  {
        
        ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(BasicTest.class);
        Assert.assertEquals(ShapeTypes.BEAN, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertFalse(model.isMap());
        Assert.assertFalse(model.isSet());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertTrue(model.isObject());
    }

    @Test
    public void assertBT()  {
        
        ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(BasicTest.class.getSimpleName());
        Assert.assertEquals(ShapeTypes.BEAN, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertFalse(model.isMap());
        Assert.assertFalse(model.isSet());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertTrue(model.isObject());

        BasicTest object = (BasicTest) model.create();

        model.set(F_TEST_STRING, object, S_STRING);
        Assert.assertEquals(S_STRING, model.get(F_TEST_STRING, object));
        Assert.assertEquals(S_STRING, object.getTestString());

        model.set(F_TEST_DATE, object, SAMPLE_DATE);
        Assert.assertEquals(SAMPLE_DATE, model.get(F_TEST_DATE, object));
        Assert.assertEquals(SAMPLE_DATE, object.getTestDate());

        Map map = new HashMap();
        model.set(F_TEST_OBJECT, object, map);
        Assert.assertEquals(map, object.getTestObject());
        Assert.assertEquals(map, model.get(F_TEST_OBJECT, object));
        Assert.assertTrue(map == model.get(F_TEST_OBJECT, object));
    }

    @Test
    public void getModel()  {
        
        ModelInterface cache = TestProviderRootTest.EO_CONFIGS.findModel(M_BASIC_TEST);
        Assert.assertEquals(M_BASIC_TEST, cache.getModelKey());
        BasicTest basicTest = (BasicTest) cache.create();
        cache.set(F_TEST_STRING, basicTest, S_STRING);
        Assert.assertEquals(S_STRING, cache.get(F_TEST_STRING, basicTest));
        cache.set(F_TEST_INTEGER, basicTest, S_INTEGER);
        Assert.assertEquals(S_INTEGER, cache.get(F_TEST_INTEGER, basicTest));
        cache.set(F_TEST_LONG, basicTest, SAMPLE_LONG);
        Assert.assertEquals(SAMPLE_LONG, cache.get(F_TEST_LONG, basicTest));
        cache.set(F_TEST_FLOAT, basicTest, SAMPLE_FLOAT);
        Assert.assertEquals(SAMPLE_FLOAT, cache.get(F_TEST_FLOAT, basicTest));
        cache.set(F_TEST_DOUBLE, basicTest, SAMPLE_DOUBLE);
        Assert.assertEquals(SAMPLE_DOUBLE, cache.get(F_TEST_DOUBLE, basicTest));
        cache.set(F_TEST_DATE, basicTest, SAMPLE_DATE);
        Assert.assertEquals(SAMPLE_DATE, cache.get(F_TEST_DATE, basicTest));
        cache.set(F_TEST_BOOLEAN, basicTest, S_BOOLEAN);
        Assert.assertEquals(S_BOOLEAN, cache.get(F_TEST_BOOLEAN, basicTest));
    }

    @Test
    public void assertST()  {
        
        ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(SubTest.class);
        Assert.assertEquals(ShapeTypes.BEAN, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertFalse(model.isMap());
        Assert.assertFalse(model.isSet());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertTrue(model.isObject());
    }

    @Test
    public void getST()  {
        final ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(M_SUB_TEST);
        Assert.assertEquals(M_SUB_TEST, model.getModelKey());
        SubTest subTest = (SubTest) model.create();
        model.set(F_TEST_STRING, subTest, S_STRING);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, model.get(F_TEST_STRING, subTest));
    }

    @Test
    public void assertBTFieldTest()  {
        ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(BasicTest.class);

        FieldConfig field = model.getField(F_TEST_STRING);
        Assert.assertEquals(String.class, field.getModelClass());

        field = model.getField(F_TEST_OBJECT);
        Assert.assertEquals(Object.class, field.getModelClass());

    }

    @Test
    public void assertScsConfigTest()  {
        ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(ScsConfig.class);

        FieldConfig field = model.getField(F_DESCRIPTION);
        Assert.assertEquals(String.class, field.getModelClass());
    }
}
