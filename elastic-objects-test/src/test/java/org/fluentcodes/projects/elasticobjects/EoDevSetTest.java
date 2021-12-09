package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJson;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL1;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL2;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL3;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL4;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_LEVEL5;


public class EoDevSetTest {

    @Test
    public void mapSmall__set_model_List__Exception()  {
        final EO rootEo = TestProviderJson.MAP_SMALL_WITH_KEY.getEoDev();
        Assertions.assertThatThrownBy(()->{rootEo.set(List.class.getSimpleName(), PathElement.ROOT_MODEL);})
                .hasMessage("Could not change model with a set");
    }

    @Test
    public void empty__eo_set_key_value__map_get_key_value()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        eo.set("value","key");
        Assertions.assertThat(((Map)eo.get()).get("key")).isEqualTo("value");
    }

    @Test
    public void constructor_LinkedHashMap__eo_set_key_value__map_get_key_value()  {
        final EO eo = ProviderConfigMapsDev.createEo(new LinkedHashMap());
        eo.set("value","key");
        Assertions.assertThat(((Map)eo.get()).get("key")).isEqualTo("value");
    }

    @Test
    public void key_value__set_model_List__exception()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        IEOScalar child = eo.set("value", "key");
        Assertions.assertThatThrownBy(()->{eo.set(List.class.getSimpleName(), PathElement.ROOT_MODEL);})
                .hasMessage("Could not change model with a set");
    }

    @Test
    public void empty__setEmpty_key__ModelClass_Map()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        eo.createChild("key");
        Assertions.assertThat(eo.getEo("key").getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void empty__set_longPath1_overLappingPath2__expected()  {
        final EO eo = ProviderConfigMaps.createEo();
        eo.createChild(S_LEVEL0, S_LEVEL1, S_LEVEL2,  S_LEVEL3);
        eo.createChild(S_LEVEL0, S_LEVEL1, S_LEVEL4,  S_LEVEL5);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL2,  S_LEVEL3).getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eo.getEo(S_LEVEL0, S_LEVEL1, S_LEVEL4,  S_LEVEL5).getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void empty__set_key_null__Exception()  {
        final EO eo = ProviderConfigMapsDev.createEo();
        Assertions.assertThatThrownBy(()->{eo.set(null, "key");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void empty_class_Map_Map__set_key_value__Exception()  {
        final EO eo = ProviderConfigMapsDev.createEoWithClasses(Map.class, Map.class);
        Assertions.assertThatThrownBy(()->{eo.set("value", "key");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void empty__set_test_test2_map_test_3_value__child_get_test3_value()  {
        final EoRoot root = ProviderConfigMapsDev.createEo();
        Map<String,String> map = new LinkedHashMap<>();
        map.put("test3","value");
        EO child = (EO)root.set(map,"test","test2");
        Assert.assertEquals("value", root.get("test","test2","test3"));
        Assert.assertEquals("value", child.get("test3"));
    }

    @Test
    public void empty__setMapComplex__getComplexValues()  {
        final EoRoot root = ProviderConfigMapsDev.createEo();

        Map map = new LinkedHashMap<>();
        map.put("test3","value1");

        Map<String,String> subMap = new LinkedHashMap<>();
        subMap.put("test5","value2");
        map.put("test4", subMap);

        EO child = (EO)root.set(map,"test1","test2");
        Assert.assertEquals("value1", root.get("test1","test2","test3"));
        Assert.assertEquals("value2", child.get("test4","test5"));
    }

    /**
     * Given eo with no Model
     * when adding json map small to a path
     * values will be added with the correct type to the path.
     */
    @Test
    public void empty__setJsonSmall__getJsonSmall()  {
        final EO eoEmpty = ProviderConfigMapsDev.createEo();
        eoEmpty.set("{\"myString\": \"test\", \"myInt\": 1}", "test1");
        Assertions.assertThat(eoEmpty.getEo("test1").getModelClass()).isEqualTo(Map.class);
        Assertions.assertThat(eoEmpty.get("test1", AnObject.MY_INT)).isEqualTo(1);
        Assertions.assertThat(eoEmpty.get("test1", AnObject.MY_STRING)).isEqualTo("test");
    }
}