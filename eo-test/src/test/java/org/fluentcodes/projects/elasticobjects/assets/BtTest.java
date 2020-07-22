package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.config.FieldConfig;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.config.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.test.TestProviderMapJson;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_OBJECT;

/**
 * Created by Werner on 04.11.2016.
 */
public class BtTest {
    private static final Logger LOG = LogManager.getLogger(BtTest.class);

    @Test
    public void bean() {
        BasicTest test = new BasicTest();
        test.setTestInt(1);
        Assert.assertEquals(new Integer(1), test.getTestInt());
    }

    @Test
    public void givenModelFromString_notNull()  {
        ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(BasicTest.class.getSimpleName());
        Assertions.assertThat(model).isNotNull();
    }

    @Test
    public void givenModelFromClass_createAndSetModelFieldsWith_noError()  {
        ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(BasicTest.class);
        Assert.assertEquals(ShapeTypes.BEAN, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertFalse(model.isMap());
        Assert.assertFalse(model.isSet());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertTrue(model.isObject());

        BasicTest object = (BasicTest) model.create();
        Assertions.assertThat(model).isNotNull();
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
    public void readDataFile()  {
        /* TODOList list = JacksonHelperObsolete.readListFromDataClassPath(BasicTest.class);
        Assert.assertNotNull(list);
        Assert.assertTrue(list.size()>0);*/
        //AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, list);
    }


    @Test
    public void readBasicTestSimple()  {
        /* TODOList<Object> map = JacksonHelperObsolete.readListFromUrl("BasicTestSimple.json", BasicTest.class);
        Assert.assertNotNull(map);
        Assert.assertTrue(map.size()==3);
        BasicTest test = (BasicTest) map.find(0);
        Assert.assertEquals("testString1", test.getTestString());*/
    }

    @Test
    public void testBTSmall()  {
        BasicTest bt = TestProviderMapJson.SMALL.createBt();
        Assertions.assertThat(bt.getTestString()).isEqualTo(S_STRING);
    }

    @Test
    public void assertBTFieldTest()  {
        ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(BasicTest.class);

        FieldConfig field = model.getFieldConfig(F_TEST_STRING);
        Assert.assertEquals(String.class, field.getModelClass());

        field = model.getFieldConfig(F_TEST_OBJECT);
        Assert.assertEquals(Object.class, field.getModelClass());

    }

    @Test
    public void getFields()  {
        ModelInterface cache = TestProviderRootTest.findModel(BasicTest.class);
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



}
