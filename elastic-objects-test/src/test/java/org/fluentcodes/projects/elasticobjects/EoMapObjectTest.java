package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.TestProviderAnObjectJson;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.assertj.core.api.Assertions.assertThat;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_DOUBLE;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoMapObjectTest {
    @Test
    public void DEV__Boolean__exception()  {
        final EO root = ProviderRootDevScope.createEo();
        Assertions.assertThatThrownBy(() ->{root.mapObject(true);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not map scalar");
    }

    @Test
    public void DEV__JSON_List_Double_0_1_1_2__xpected()  {
        final EO eo = EoRoot.of(ProviderRootDevScope.EO_CONFIGS);
        eo.mapObject("{\"" + PathElement.ROOT_MODEL + "\":\"List,Double\", \"0\":1,\"1\":2}");
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("0").getModelClass()).isEqualTo(Double.class);
        new XpectEo<>().compareAsString(eo);
    }

    @Test
    public void DEV_List__JSON_0_true__get_0_true()  {
        final EO root = ProviderRootDevScope.createEo(List.class);
        root
                .mapObject("[true]");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assert.assertEquals(Boolean.class, root.getEo("0").getModelClass());
    }


    @Test
    public void DEV__JSON_List_value_1__get_List_0__1()  {
        final EO root = ProviderRootDevScope.createEo();
        root
                .mapObject("{\"_rootmodel\": \"List\",\"0\": 1}");
        Assertions.assertThat(root.getLog())
                .isEmpty();
        Assertions.assertThat(root.getModelClass())
                .isEqualTo(List.class);
        Assertions.assertThat(root.getEo("0").getModelClass())
                .isEqualTo(Integer.class);
        Assertions.assertThat(root.get("0"))
                .isEqualTo(1);
    }

    @Test
    public void DEV__JSON_key_value__get_key_value()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{\"key\": \"value\"}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get("key")).isEqualTo("value");
    }

    @Test
    public void DEV__JSON_key1_1_key2_2_key3_3__get_key1_1() {
        final EO eo = ProviderRootDevScope.createEo();
        final String mapJson = "{\"key1\": 1,\"key2\": 2,\"key3\": 3}";
        eo.mapObject(mapJson);
        Assertions.assertThat(eo.get("key1")).isEqualTo(1);
    }

    @Test
    public void DEV__JSON_empty__eo_isEmpty()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{}");
        Assert.assertTrue(eo.isEmpty());
    }


    @Test
    public void DEV__JSON_key_true__get_key_true()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject("{\"key\":true}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo(true);
    }

    @Test
    public void DEV__JSON_key_1__get_key_1()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject("{\"key\":1}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo(1);
    }

    /**
     * Wiki example
     */
    @Test
    public void DEV__JSON_key1_key2_value__expected()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{\"key1\":{\"key2\":\"value\"}}");
        assertThat(eo.get("key1/key2")).isEqualTo("value");
    }

    @Test
    public void DEV__JSON_2_key_value__expected()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{\"key1\":\"value1\",\"key2\":\"value2\"}");
        assertThat(eo.get("key1")).isEqualTo("value1");
        assertThat(eo.get("key2")).isEqualTo("value2");
    }

    @Test
    public void DEV__JSON_small__expected()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject("{\"myString\": \"test\", \"myInt\": 1}");
        assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo(S_INTEGER);
    }

    @Test
    public void DEV__JSON_all__expected()  {
        final EO eo = ProviderRootDevScope.createEo();;
        eo.mapObject(TestProviderAnObjectJson.ALL.content());
        assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo(S_INTEGER);
    }

    @Test
    public void TEST__JSON_Double_key_2_2__get_key_2_2()  {
        EO eo = ProviderRootTestScope.createEo("{\"(Double)key\": 2.2}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void DEV__JSON_Float_key_1_1__get_key_1_1()  {
        EO eo = ProviderRootTestScope.createEo("{\"key\": 1.1}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo((1.1F));
    }

    @Test
    public void TEST__JSON_Float_key_1_1__get_key_1_1()  {
        EO eo = ProviderRootTestScope.createEo("{\"(Float)key\": 1.1}");
        assertThat(eo.getLog()).isEmpty();
        assertThat(eo.get("key")).isEqualTo((1.1F));
    }

    @Test
    public void TEST__JSON_all_typed__expected()  {
        EO eo = TestProviderAnObjectJson.ALL_TYPED.createEoTest();
        assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo(S_STRING);
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo(S_INTEGER);
    }

    @Test
    public void DEV__null__size_0()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject(null);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.size()).isEqualTo(0);
    }

    @Test
    public void DEV_List__String_value__exception()  {
        final EO root = ProviderRootDevScope.createEo(List.class);
        Assertions.assertThatThrownBy(()->{root.mapObject("value");})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not map scalar");
    }

    @Test
    public void DEV_List__true__exception()  {
        final EO root = ProviderRootDevScope.createEo(List.class);
        Assertions.assertThatThrownBy(()->{root.mapObject(true);})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not map scalar");
    }

    @Test
    public void DEV_List__JSONList_empty__size_0()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[]");
        Assert.assertEquals(List.class, eo.getModelClass());
        Assertions.assertThat(eo.size()).isEqualTo(0);
    }

    @Test
    public void DEV_List__JSONList_0_value__get_0_value()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[\"value\"]");
        Assertions.assertThat(eo.get("0")).isEqualTo("value");
    }

    @Test
    public void DEV_List__JSONList_0_0_value__get_0_0_value()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[[\"testObject\"]]");
        Assertions.assertThat(eo.getEo("0").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get("0/0")).isEqualTo("testObject");
    }

    @Test
    public void DEV_List__JSONList_0_1__get_0_1()    {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[1]");
        Assertions.assertThat(eo.get("0")).isEqualTo(1);
    }

    @Test
    public void DEV_List__JSONList_0_value_1_2__get_0_value_get_1_2()  {
        EO eo = ProviderRootDevScope.createEo(List.class);
        eo.mapObject("[\"value\",2]");
        Assertions.assertThat(eo.get("0")).isEqualTo("value");
        Assertions.assertThat(eo.get("1")).isEqualTo(2);
    }

    @Test
    public void DEV__JSON_rootmodle_List_Float_0_1_1__get_0_1_1()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{\"_rootmodel\": \"List\",\"(Float)0\": 1.1}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get("0")).isEqualTo(1.1F);
    }

    @Test
    public void DEV__JSON_rootmodel_List_0_value_1_1__get_0_value_1_1()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{" +
                "\n\"_rootmodel\":\"List\"," +
                "\n\"0\":\"value\"," +
                "\n\"1\":1\n}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get("0")).isEqualTo("value");
        Assertions.assertThat(eo.get("1")).isEqualTo(1);
    }

    @Test
    public void DEV_List_String__JSON_key_true__get_0_String_true()  {
        EO root = ProviderRootDevScope.createEoWithClasses(List.class, String.class);
        root
                .mapObject("{\"key\": true}");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.get("0")).isEqualTo("true");
    }

    @Test
    public void DEV__Scalar_value__exception()  {
        final EO rootEo = ProviderRootDevScope.createEo();
        Assertions.assertThatThrownBy(()->{rootEo.mapObject("value");})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("");
    }

    @Test
    public void DEV_List__JSON_key_true__get_0_true()  {
        final EO root = ProviderRootDevScope.createEoWithClasses(List.class);
        root.mapObject("{\"key\": true}");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.get("0")).isEqualTo(true);
    }

    @Test
    public void DEV__JSON_rootmodel_List_key_1__get_0_1()  {
        final EO root = ProviderRootDevScope.createEo();
        root
                .mapObject("{\"_rootmodel\": \"List\",\"key\": 1}");
        Assertions.assertThat(root.getLog())
                .isEmpty();
        Assertions.assertThat(root.getModelClass())
                .isEqualTo(List.class);
        Assertions.assertThat(root.get("0"))
                .isEqualTo(1);
    }

    @Test
    public void DEV__JSONList_1__get_0_1()  {
        final EO root = ProviderRootDevScope.createEo();
        root
                .mapObject("[1]");
        Assertions.assertThat(root.getLog()).isEmpty();
        Assertions.assertThat(root.getEo("0").getModelClass()).isEqualTo(Integer.class);
        Assertions.assertThat(root.get("0")).isEqualTo(1);
    }

    @Test
    public void DEV__JSON_List_Double_key_0_1_1_2__get_key_1_2()  {
        final EO eo = EoRoot.of(ProviderRootDevScope.EO_CONFIGS);
        eo.mapObject("{\"(List,Double)key\":{\"0\":1,\"1\":2}}");
        Assertions.assertThat(eo.getEo("key").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get("key/1")).isEqualTo(2.0);
        new XpectEo<>().compareAsString(eo);
    }

    /**
     * Wiki example
     */
    @Test
    public void TEST__JSON_AnObject_myString_value__get_myString_value() {
        final EO eo = ProviderRootTestScope.createEo();
        final String jsonString = "{\n" +
                "\t\"(" + AnObject.class.getSimpleName() + ")key0\":{\n" +
                "\t\t\"" + AnObject.MY_STRING + "\":\"value\"\n" +
                "    }\n" +
                "}";
        eo.mapObject(jsonString);
        assertThat(eo.get("key0", AnObject.MY_STRING)).isEqualTo("value");
        assertThat(((AnObject)eo.get("key0")).getMyString()).isEqualTo("value");
        assertThat(eo.getEo("key0").getModelClass()).isEqualTo(AnObject.class);
    }

    /**
     * Wiki example
     */
    @Test
    public void TEST__JSON_AnObject_comment_text__get_comment_text() {
        final EO eo = ProviderRootTestScope.createEo();
        final String jsonString = "{\n" +
                "\t\"(" + AnObject.class.getSimpleName() + ")level0\":{\n" +
                "\t\t\"" + AnObject.MY_STRING + "\":\"value\",\n" +
                "\"_comment\":\"_comment is not a field of the " + AnObject.class.getSimpleName() + ".class\"" +
                "    }\n" +
                "}";
        eo.mapObject(jsonString);
        assertThat(eo.get("level0/_comment")).isEqualTo("_comment is not a field of the "  + AnObject.class.getSimpleName() + ".class");
        assertThat(((AnObject)eo.get("level0")).getMyString()).isEqualTo("value");
    }

    /**
     * Basic Wiki Example
     */
    @Test
    public void TEST__AnObject_myString_value__get_myString_value()  {
        final EO eo = ProviderRootTestScope.createEo();
        AnObject bt = new AnObject()
                .setMyString("value");
        eo.mapObject(bt);
        assertThat(eo.get(AnObject.MY_STRING)).isEqualTo("value");
        assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }


    @Test
    public void TEST_key_value__Child_AnObject__exception()  {
        final EO eo = ProviderRootTestScope.createEo();
        EO child = eo.set("key", "value");
        Assertions.assertThatThrownBy(()->{child.mapObject(new AnObject());})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not create 'String' value from");
    }

    @Test
    public void TEST_AnObject_JSON_myString_value__JSON_myInt_1__get_myInt_1()  {
        final EO eo = ProviderRootTestScope.createEoWithClasses(AnObject.class);
        eo.mapObject("{\"myString\": \"value\"}");
        eo.mapObject("{\"myInt\": 1}");
        assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        assertThat(eo.get(AnObject.MY_STRING)).isEqualTo("value");
        assertThat(eo.get(AnObject.MY_INT)).isEqualTo(1);
    }

}


