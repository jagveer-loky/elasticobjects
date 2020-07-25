package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.config.FieldConfig;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.config.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.test.TestProviderMapJson;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootDev;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_OBJECT;

/**
 * Created by Werner on 04.11.2016.
 */
public class EoSetBtTest {
    private static final Logger LOG = LogManager.getLogger(EoSetBtTest.class);

    @Test
    public void fromEoConfigsCache()  {
        ModelInterface cache = TestProviderRootTest.EO_CONFIGS.findModel(BasicTest.class);
        Assert.assertNotNull(cache.getFieldConfig(F_TEST_STRING));
        Assert.assertEquals(F_TEST_STRING, cache.getFieldConfig(F_TEST_STRING).getFieldKey());
        ModelInterface subTest = cache.getFieldModel(F_SUB_TEST);
        Assert.assertEquals(M_SUB_TEST, subTest.getModelKey());
        Assert.assertEquals(F_TEST_STRING, subTest.getFieldConfig(F_TEST_STRING).getFieldKey());
    }

    @Test
    public void testBasicTestWithTestString()  {
        FieldConfig fieldCacheDefinitions = TestProviderRootTest.EO_CONFIGS
                .findModel(BasicTest.class)
                .getFieldConfig(F_TEST_STRING);
        Assert.assertNotNull(fieldCacheDefinitions);
        Assert.assertFalse(fieldCacheDefinitions.isUnique());
        Assert.assertFalse(fieldCacheDefinitions.isNotNull());
        //Assert.assertTrue(fieldDefinitions.isElementary());
        //Assert.assertTrue(fieldDefinitions.isScalar());
        Assert.assertEquals(new Integer(20), fieldCacheDefinitions.getLength());
    }

    @Test
    public void testMap_forWiki()  {
        final EO eo = TestProviderRootTest.createEo();

        final Map map = new HashMap();
        map.put("testString", "value");
        map.put("testFloat", 1.1D);

        final EO child = eo.set( map, "(BasicTest)level0");
        Assert.assertEquals(BasicTest.class, child.getModelClass());
        assertEquals("value", child.get("testString"));
        assertEquals(1.1F, child.get("testFloat"));
        assertEquals("value", eo.get("level0","testString"));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(BasicTest.class, eo.getEo("level0").getModelClass());
        assertEquals(BasicTest.class, eo.get("level0").getClass());
        assertEquals(BasicTest.class, child.get().getClass());
        assertEquals(Float.class, eo.getEo("level0/testFloat").getModelClass());
    }

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

    @Test
    public void testBT_ok()  {
        final EO root = TestProviderRootTest.createEo();
        BasicTest basicTest = new BasicTest();
        basicTest.setTestString("testObject");
        root.set( basicTest, "test","test2");
        Assert.assertEquals("testObject", root.get("test","test2","testString"));
        Assert.assertEquals(BasicTest.class, root.getEo("test","test2").getModelClass());
    }

    @Test
    public void giventestBTByPath_ok()  {
        final EO root = TestProviderRootTest.createEo();
        root.set("testObject", "(BasicTest)test", "testString");
        Assert.assertEquals("testObject", root.get("test"," testString"));
        Assert.assertEquals(BasicTest.class, root.getEo("test").getModelClass());
    }

    @Test
    public void testBTByPathAddInteger_ok()  {
        final EO root = TestProviderRootTest.createEo();
        root.set("testObject", "(BasicTest)test","testString");
        root.set(1, "test","testInt");
        Assert.assertEquals(1, root.get("test","testInt"));
        Assertions.assertThat(root.getLog()).isEmpty();
        Assert.assertEquals(BasicTest.class, root.getEo("test").getModelClass());
    }

    @Test
    public void givenBT_whenSetPathNotExisting_hasLog()  {
        final EO root = TestProviderRootTest.createEo();
        root.set("testObject", "(BasicTest)test","testString");
        root.set(1, "test", "nonsense");
        Assert.assertNull(root.get("test","nonsense"));
        Assertions.assertThat(root.getLog()).isNotEmpty();
        Assert.assertEquals(BasicTest.class, root.getEo("test").getModelClass());
    }

    @Test
    public void givenTestEmpty_whenSetEmptytestBTByPathWithEmpty_ok()  {
        final EO root = TestProviderRootTest.createEo();
        root.setEmpty("(BasicTest)test");
        Assert.assertEquals(BasicTest.class, root.getEo("test").getModelClass());
    }

    @Test
    public void givenBtEmpty_whenSetStringFieldWithString_ok()  {
        final EO eo = TestProviderRootTest.createEo(new BasicTest());
        eo.set(S_STRING_OTHER, F_TEST_STRING);
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void givenBtEmpty_whenSetNotExistingPath_thenLogErrors()  {
        final EO eo = TestProviderRootTest.createEo(new BasicTest());
        eo.set(S_STRING_OTHER, S_KEY1);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
    }

    @Test
    public void givenBtEmpty_whenSetScalarFieldWithBT_thenLogErrors()  {
        final EO eo = TestProviderRootTest.createEo(new BasicTest());
        eo.set(new BasicTest(), F_TEST_STRING);
        Assertions.assertThat(eo.getLog()).contains("Problem setting non scalar value ");
    }

    @Test
    public void givenBtEmpty_whenSetBTFieldWithScalar_thenLogErrors()  {
        final EO eo = TestProviderRootTest.createEo(new BasicTest());
        eo.set(S_STRING, F_BASIC_TEST);
        Assertions.assertThat(eo.getLog()).contains("Problem setting scalar value ");
    }

    @Test
    public void givenTestEmpty_whenSetPathWithBTDirective_thenModelIsBT()  {
        final EO eo = TestProviderRootTest.createEo();
        eo.setEmpty("(BasicTest)" + S_LEVEL0);
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(BasicTest.class);
    }

    @Test
    public void givenTestEmpty_whenSetBTOnExistingModelMap_thenModelIsMap()  {
        final EO eo = TestProviderRootTest.createEo();
        eo.setEmpty(S_LEVEL0);
        eo.setEmpty("(BasicTest)" + S_LEVEL0);
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void givenTestEmpty_whenSetBTDirectiveAtEndOfLongPath_thenIsBTClass()  {
        final EO eo = TestProviderRootTest.createEo();
        eo.setEmpty(S_LEVEL0, S_LEVEL1, S_LEVEL2, "(BasicTest)" + S_LEVEL3);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3).getModelClass()).isEqualTo(BasicTest.class);
    }

    @Test
    public void givenTestEmpty_whenSetSmallJsonBtOnIntegerField_hasError()  {
        final EO eoMap = TestProviderRootTest.createEo();
        eoMap.set(TestProviderMapJson.SMALL.createBt(), S_LEVEL0);
        eoMap.set(TestProviderMapJson.SMALL.createBt(), S_LEVEL0, F_TEST_INTEGER);
        Assertions.assertThat(eoMap.getLog()).contains("Tried to map scalar child");
    }

}
