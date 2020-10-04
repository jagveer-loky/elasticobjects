package org.fluentcodes.projects.elasticobjects.assets;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.assets.AnObject.MY_OBJECT;

/**
 * Created by Werner on 04.11.2016.
 */
public class EoAnObjectSetTest {
    private static final Logger LOG = LogManager.getLogger(EoAnObjectSetTest.class);

    @Test
    public void fromEoConfigsCache()  {
        ModelInterface cache = ProviderRootTestScope.EO_CONFIGS.findModel(AnObject.class);
        Assert.assertNotNull(cache.getFieldConfig(AnObject.MY_STRING));
        Assert.assertEquals(AnObject.MY_STRING, cache.getFieldConfig(AnObject.MY_STRING).getFieldKey());
        ModelInterface aSubObject = cache.getFieldModel(AnObject.MY_ASUB_OBJECT);
        Assert.assertEquals(ASubObject.class.getSimpleName(), aSubObject.getModelKey());
        Assert.assertEquals(AnObject.MY_STRING, aSubObject.getFieldConfig(AnObject.MY_STRING).getFieldKey());
    }

    @Test
    public void TEST__find_AnObject_get_myString__$()  {
        FieldConfig fieldConfig = ProviderRootTestScope.EO_CONFIGS
                .findModel(AnObject.class)
                .getFieldConfig(AnObject.MY_STRING);
        Assert.assertNotNull(fieldConfig);
        Assert.assertFalse(fieldConfig.getUnique());
        Assert.assertFalse(fieldConfig.getNotNull());
        //Assert.assertTrue(fieldDefinitions.isElementary());
        //Assert.assertTrue(fieldDefinitions.isScalar());
        Assert.assertEquals(new Integer(20), fieldConfig.getLength());
    }

    @Test
    public void testMap_forWiki()  {
        final EO eo = ProviderRootTestScope.createEo();

        final Map map = new HashMap();
        map.put(AnObject.MY_STRING, "value");
        map.put(AnObject.MY_FLOAT, 1.1D);

        final EO child = eo.set( map, "(" + AnObject.class.getSimpleName() + ")level0");
        Assert.assertEquals(AnObject.class, child.getModelClass());
        assertEquals("value", child.get(AnObject.MY_STRING));
        assertEquals(1.1F, child.get(AnObject.MY_FLOAT));
        assertEquals("value", eo.get("level0", AnObject.MY_STRING));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(AnObject.class, eo.getEo("level0").getModelClass());
        assertEquals(AnObject.class, eo.get("level0").getClass());
        assertEquals(AnObject.class, child.get().getClass());
        assertEquals(Float.class, eo.getEo("level0", AnObject.MY_FLOAT).getModelClass());
    }

    @Test
    public void bean() {
        AnObject test = new AnObject();
        test.setMyInt(1);
        Assert.assertEquals(new Integer(1), test.getMyInt());
    }

    @Test
    public void givenModelFromString_notNull()  {
        ModelInterface model = ProviderRootTestScope.EO_CONFIGS.findModel(AnObject.class.getSimpleName());
        Assertions.assertThat(model).isNotNull();
    }

    @Test
    public void givenModelFromClass_createAndSetModelFieldsWith_noError()  {
        ModelInterface model = ProviderRootTestScope.EO_CONFIGS.findModel(AnObject.class);
        Assert.assertEquals(ShapeTypes.BEAN, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertFalse(model.isMap());
        Assert.assertFalse(model.isSet());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertTrue(model.isObject());

        AnObject object = (AnObject) model.create();
        Assertions.assertThat(model).isNotNull();
        model.set(AnObject.MY_STRING, object, S_STRING);
        Assert.assertEquals(S_STRING, model.get(AnObject.MY_STRING, object));
        Assert.assertEquals(S_STRING, object.getMyString());

        model.set(AnObject.MY_DATE, object, SAMPLE_DATE);
        Assert.assertEquals(SAMPLE_DATE, model.get(AnObject.MY_DATE, object));
        Assert.assertEquals(SAMPLE_DATE, object.getMyDate());

        Map map = new HashMap();
        model.set(MY_OBJECT, object, map);
        Assert.assertEquals(map, object.getMyObject());
        Assert.assertEquals(map, model.get(MY_OBJECT, object));
        Assert.assertTrue(map == model.get(MY_OBJECT, object));
    }

    @Test
    public void testAnObjectSmall()  {
        AnObject bt = TestProviderAnObjectJson.SMALL.createBt();
        Assertions.assertThat(bt.getMyString()).isEqualTo(S_STRING);
    }

    @Test
    public void assertAnObjectFieldTest()  {
        ModelInterface model = ProviderRootTestScope.EO_CONFIGS.findModel(AnObject.class);

        FieldConfig field = model.getFieldConfig(AnObject.MY_STRING);
        Assert.assertEquals(String.class, field.getModelClass());

        field = model.getFieldConfig(MY_OBJECT);
        Assert.assertEquals(Object.class, field.getModelClass());

    }

    @Test
    public void getFields()  {
        ModelInterface cache = ProviderRootTestScope.findModel(AnObject.class);
        Assert.assertEquals(AnObject.class.getSimpleName(), cache.getModelKey());
        AnObject anObject = (AnObject) cache.create();
        cache.set(AnObject.MY_STRING, anObject, S_STRING);
        Assert.assertEquals(S_STRING, cache.get(AnObject.MY_STRING, anObject));
        cache.set(AnObject.MY_INT, anObject, S_INTEGER);
        Assert.assertEquals(S_INTEGER, cache.get(AnObject.MY_INT, anObject));
        cache.set(AnObject.MY_LONG, anObject, SAMPLE_LONG);
        Assert.assertEquals(SAMPLE_LONG, cache.get(AnObject.MY_LONG, anObject));
        cache.set(AnObject.MY_FLOAT, anObject, SAMPLE_FLOAT);
        Assert.assertEquals(SAMPLE_FLOAT, cache.get(AnObject.MY_FLOAT, anObject));
        cache.set(AnObject.MY_DOUBLE, anObject, SAMPLE_DOUBLE);
        Assert.assertEquals(SAMPLE_DOUBLE, cache.get(AnObject.MY_DOUBLE, anObject));
        cache.set(AnObject.MY_DATE, anObject, SAMPLE_DATE);
        Assert.assertEquals(SAMPLE_DATE, cache.get(AnObject.MY_DATE, anObject));
        cache.set(AnObject.MY_BOOLEAN, anObject, S_BOOLEAN);
        Assert.assertEquals(S_BOOLEAN, cache.get(AnObject.MY_BOOLEAN, anObject));
    }

    @Test
    public void testAnObject_ok()  {
        final EO root = ProviderRootTestScope.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("testObject");
        root.set( anObject, "test","test2");
        Assert.assertEquals("testObject", root.get("test","test2", AnObject.MY_STRING));
        Assert.assertEquals(AnObject.class, root.getEo("test","test2").getModelClass());
    }

    @Test
    public void givenEo_setAnObjectPathTestAndTestString_thenValueAndModelIsSet()  {
        final EO root = ProviderRootTestScope.createEo();
        root.set("testObject", "(" + AnObject.class.getSimpleName() + ")test", AnObject.MY_STRING);
        Assert.assertEquals("testObject", root.get("test", AnObject.MY_STRING));
        Assert.assertEquals(AnObject.class, root.getEo("test").getModelClass());
    }

    @Test
    public void TEST_path_model_AnObject__set_myInt__class_is_AnObject()  {
        final EO root = ProviderRootTestScope.createEo();
        root.set("testObject", "(" + AnObject.class.getSimpleName() + ")test", AnObject.MY_STRING);
        root.set(1, "test", AnObject.MY_INT);
        Assert.assertEquals(1, root.get("test", AnObject.MY_INT));
        Assertions.assertThat(root.getLog()).isEmpty();
        Assert.assertEquals(AnObject.class, root.getEo("test").getModelClass());
    }
    
    @Test
    public void givenBt_whenSetStringField_ok()  {
        final EO eo = ProviderRootTestScope.createEo(new AnObject());
        eo.set(S_STRING_OTHER, AnObject.MY_STRING);
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
    }

    @Test
    public void givenBt_whenSetNotExistingField_thenExceptionThrown()  {
        final EO eo = ProviderRootTestScope.createEo(new AnObject());
        Assertions.assertThatThrownBy(
                ()->{ eo.set(S_STRING_OTHER, S_KEY1);}
        )
                .hasMessage("No fieldName defined key1(" + AnObject.class.getSimpleName() + ").");
    }

    @Test
    public void givenBt_whenSetScalarFieldWithObject_thenExeptionThrown()  {
        final EO eo = ProviderRootTestScope.createEo(new AnObject());
        Assertions
                .assertThatThrownBy(
                        ()->{eo.set(new AnObject(), AnObject.MY_STRING);}
                )
                .hasMessage("Problem setting non scalar value (" + AnObject.class.getSimpleName() + ") for field name '" + AnObject.MY_STRING + "'. Expected is String!");
    }

    @Test
    public void givenTest_whenSetPathWithAnObjectDirective_thenModelIsAnObject()  {
        final EO eo = ProviderRootTestScope.createEo();
        eo.set(new AnObject(), S_LEVEL0);
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(AnObject.class);
    }



    @Test
    public void givenTestEmpty_whenSetAnObjectDirectiveAtEndOfLongPath_thenIsAnObjectClass()  {
        final EO eo = ProviderRootTestScope.createEo();
        eo.setEmpty(S_LEVEL0, S_LEVEL1, S_LEVEL2, "(" + AnObject.class.getSimpleName() + ")" + S_LEVEL3);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL2, S_LEVEL3).getModelClass()).isEqualTo(AnObject.class);
    }

}
