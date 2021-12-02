package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;

public class EoSetTest {
    @Test
    public void DEV__key0_value__get_key0_value()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        eo.set("value", S_LEVEL0);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S_LEVEL0).getModelClass()).isEqualTo(String.class);
        Assertions.assertThat(eo.getEo().getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.get(S_LEVEL0)).isEqualTo("value");
        Assertions.assertThat(eo.isContainer()).isTrue();
        Assertions.assertThat(eo.getEo(S_LEVEL0).isContainer()).isFalse();
    }

    @Test
    public void DEV__key0_key1_value__getPathAsString_key0_key1()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        final EO child = eo.set("value", "key0", "key1");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(child.getModelClass()).isEqualTo(String.class);
        Assertions.assertThat(child.getPathAsString()).isEqualTo(Path.DELIMITER + "key0" + Path.DELIMITER + "key1");
    }

    @Test
    public void DEV_key0_value__child_set__exception()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        EO childEo = eo.set("value", "key0");
        Assertions
                .assertThatThrownBy(()->{childEo.set("value", "keyOther");})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not set value because no field defined for scalar models");
    }

    @Test
    public void DEV__key0_true__get_key0_true()  {
        final EO root = ProviderConfigMapsDev.createEo( List.class);
        root.set(true, "key0");
        Assert.assertEquals(true, root.get("0"));
    }

    /**
     * Basic wiki example
     */
    @Test
    public void DEV__key0_key1_key2_key3_asString_value__get_String_key0_key1_key2_key3_asString_value() {
        final EO eo = ProviderConfigMapsDev.createEo();
        EO child = eo.set("value","key0/key1/key2/key3");
        Assertions.assertThat(child.get()).isEqualTo("value");
        Assertions.assertThat(eo.get("key0/key1/key2/key3")).isEqualTo("value");
    }


    @Test
    public void DEV__key0_key1_key2_key3_value__get_key0_key1_key2_key3_value()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        final EO child = eo.set("value", "key0","key1","key2", "key3");
        assertEquals("value", eo.get("key0","key1","key2", "key3"));

        assertEquals(Map.class, eo.getModelClass());
        assertEquals(LinkedHashMap.class, eo.get().getClass());

        assertEquals(Map.class, eo.getEo("key0").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("key0").getClass());

        assertEquals(Map.class, eo.getEo("key0","key1").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("key0","key1").getClass());

        assertEquals(Map.class, eo.getEo("key0","key1","key2").getModelClass());
        assertEquals(LinkedHashMap.class, eo.get("key0","key1","key2").getClass());

        assertEquals(String.class, eo.getEo("key0","key1","key2", "key3").getModelClass());
        assertEquals(String.class, eo.get("key0","key1","key2", "key3").getClass());

        assertEquals(Map.class,child.getEo("..").getModelClass());
        assertEquals(LinkedHashMap.class, child.get("..").getClass());

        assertEquals(Map.class,child.getRoot().getModelClass());
        assertEquals(LinkedHashMap.class, child.getRoot().get().getClass());
        assertEquals("/", child.getRoot().getPathAsString());
    }

    @Test
    public void DEV__key0_JSONList_value_1__get_key0_0_value_get_key0_1_1()  {
        final EO eoEmpty = ProviderConfigMapsDev.createEo();
        eoEmpty.set("[\"value\", 1]", "key0");
        Assertions.assertThat(eoEmpty.getEo("key0").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eoEmpty.get("key0", "1")).isEqualTo(1);
        Assertions.assertThat(eoEmpty.get("key0", "0")).isEqualTo("value");
    }

    @Test
    public void DEV__JSON_List_Double_source_0_1_1_2_2_3__getModelClass_source_0_Double()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        eo.set("{\"(List,Double)source\": {\"0\": 1,\"1\": 2,\"2\": 3}}");
        Assertions.assertThat(eo.getEo("source").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("source", "0").getModelClass()).isEqualTo(Double.class);
    }

    @Test
    public void DEV__JSONList_1_2_3__getModelClass_source_0_Integer()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        eo.set("{\"source\": [ 1, 2, 3]}");
        Assertions.assertThat(eo.getEo("source").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("source", "0").getModelClass()).isEqualTo(Integer.class);
    }

    @Test
    public void TEST_AnObject__set_key_value__exception()  {
        final EO eo = ProviderConfigMaps.createEo(new AnObject());
        Assertions.assertThatThrownBy(
                ()->{ eo.set("value", "key");}
        )
                .hasMessageContaining("No fieldConfig 'key' defined in model '" + AnObject.class.getSimpleName() + "' ! ");
    }

    @Test
    public void TEST_AnObject__set_myString_AnObject__exception()  {
        final EO eo = ProviderConfigMaps.createEo(new AnObject());
        Assertions
                .assertThatThrownBy(
                        ()->{eo.set(new AnObject(), AnObject.MY_STRING);}
                )
                .hasMessageContaining("Exception for 'myString': Mismatch for String AnObject");
    }

    @Test
    public void TEST__set_key_AnObject__getModelClass_key_AnObject()  {
        final EO eo = ProviderConfigMaps.createEo();
        eo.set(new AnObject(), "key");
        Assertions.assertThat(eo.getEo("key").getModelClass()).isEqualTo(AnObject.class);
    }
}

