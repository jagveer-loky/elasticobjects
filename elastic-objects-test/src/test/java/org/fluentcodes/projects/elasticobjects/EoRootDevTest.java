package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoRootDevTest {

    @Test
    public void __empty__ModelClass_Map()  {
        final EO eo = EoRoot.of(ProviderConfigMapsDev.CONFIG_MAPS_DEV);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void __class_LinkedHashMap__ModelClass_LinkedHashMap()  {
        final EO eo = EoRoot.ofValue(ProviderConfigMapsDev.CONFIG_MAPS_DEV, new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(LinkedHashMap.class);
    }

    @Test
    public void __class_Map__ModelClass_Map()  {
        final EO eo = ProviderConfigMapsDev.createEoWithClasses(Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void __class_Map_String__Models_Map_String()  {
        final EO eo = EoRoot.ofClass(ProviderConfigMapsDev.CONFIG_MAPS_DEV, Map.class, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(Map.class.getSimpleName() + "," + String.class.getSimpleName());
    }

    @Test
    public void __class_Map_List__Models_Map_List()  {
        final EO eo = EoRoot.ofClass(ProviderConfigMapsDev.CONFIG_MAPS_DEV, Map.class, List.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(Map.class.getSimpleName() + "," + List.class.getSimpleName());
    }

    @Test
    public void __value_HashMap__Models_LinkedHashMap()  {
        final EO eo = EoRoot.ofValue(ProviderConfigMapsDev.CONFIG_MAPS_DEV, new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(LinkedHashMap.class.getSimpleName());
    }

    @Test
    public void __constructor_HashMap__Models_LinkedHashMap()  {
        final EO eo = ProviderConfigMapsDev.createEo(new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(LinkedHashMap.class.getSimpleName());
    }

    @Test
    public void __JSONMap_empty__ModelClass_Map()  {
        final EO eo = ProviderConfigMaps.createEo("{}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        XpectEo.assertJunit(eo);
    }

    @Test
    public void __JSONMap_rootmodel_List__get_rootmodel_List()  {
        final EO eo = ProviderConfigMapsDev.createEo("{\"_rootmodel\":\"List\"}");
        Assertions.assertThat(eo.get("_rootmodel")).isEqualTo("List");
        XpectEo.assertJunit(eo);
    }

    @Test
    public void __JSONMap_key_value__get_key_value()  {
        final EO eo = EoRoot.ofValue(ProviderConfigMapsDev.CONFIG_MAPS_DEV, "{\"key\":\"value\"}");
        Assertions.assertThat(eo.get("key")).isEqualTo("value");
        XpectEo.assertJunit(eo);
    }


    @Test
    public void __value_ArrayList_empty__ModelClass_ArrayList()  {
        final EO eo = EoRoot.ofValue(ProviderConfigMapsDev.CONFIG_MAPS_DEV, new ArrayList());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.get().getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(((List)eo.get()).size()).isEqualTo(0);
    }

    @Test
    public void __class_List__ModelClass_List()  {
        final EO eo = ProviderConfigMapsDev.createEoWithClasses(List.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get().getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.isEoEmpty()).isFalse();
        Assertions.assertThat(((List)eo.get()).size()).isEqualTo(0);
    }


    @Test
    public void __class_List_String__Models_List_String()  {
        final EO eo = ProviderConfigMapsDev.createEoWithClasses(List.class, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(List.class.getSimpleName() + "," + String.class.getSimpleName());
    }


    @Test
    public void __class_List_Map__Models_List_Map()  {
        final EO eo = EoRoot.ofClass(ProviderConfigMapsDev.CONFIG_MAPS_DEV, List.class, Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(List.class.getSimpleName() + "," + Map.class.getSimpleName());
        Assertions
                .assertThat(eo.get().getClass())
                .isEqualTo(ArrayList.class);
    }

    @Test
    public void __class_List_Map__class_ArrayList()  {
        final EO eo = ProviderConfigMapsDev.createEoWithClasses(List.class, Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.get().getClass())
                .isEqualTo(ArrayList.class);
    }

    @Test
    public void __JSONList_empty__ModelClass_List()  {
        final EO eo = ProviderConfigMapsDev.createEo("[]");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        XpectEo.assertJunit(eo);
    }

    @Test
    public void __class_String__exception()  {
        Assertions.assertThatThrownBy(()->{EO eo = EoRoot.ofClass(ProviderConfigMapsDev.CONFIG_MAPS_DEV, String.class);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void __class_String_String__exception()  {
        Assertions.assertThatThrownBy(()->{EO eo = EoRoot.ofClass(ProviderConfigMapsDev.CONFIG_MAPS_DEV, String.class, String.class);})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void __value_String__exception()  {
        Assertions.assertThatThrownBy(()->{EO eo = ProviderConfigMapsDev.createEo("test");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void __JSONMap_key0_key1_value__noError()  {
        final String json = "{\"key0\": {\"key1\":\"value\"}}";
        EO rootEo = ProviderConfigMapsDev.createEo(json);
        Assertions.assertThat(rootEo.get("key0","key1"))
                .isEqualTo("value");
    }

    @Test
    public void __JSONMap_key0_key1_key2_value__noError()  {
        final String json = "{\"key0\": {\"key1\": {\"key2\":\"value\"}}}";
        EO rootEo = ProviderConfigMapsDev.createEo( json);
        Assertions.assertThat(rootEo.get("key0", "key1", "key2"))
                .isEqualTo("value");
    }


    @Test
    public void __JSONMap_Models_String_Map_ASubObject__noError()  {
        final String json = "{  \"ASubObject\": {\n" +
                "    \"configModelKey\": \"ModelConfigDbObject\",\n" +
                "    \"module\": \"elastic-objects-test\",\n" +
                "    \"moduleScope\": \"main\",\n" +
                "    \"fieldKeys\": [\n" +
                "      \"id\",\n" +
                "      \"myASubObject\",\n" +
                "      \"myString\",\n" +
                "      \"name\",\n" +
                "      \"naturalId\"\n" +
                "    ],\n" +
                "    \"expose\": \"WEB\",\n" +
                "    \"description\": \"A sub object as an example.\",\n" +
                "    \"packagePath\": \"org.fluentcodes.projects.elasticobjects.domain.test\",\n" +
                "    \"modelKey\": \"ASubObject\",\n" +
                "    \"properties\": {\n" +
                "      \"classPath\": \"src.main.java\",\n" +
                "      \"create\": true,\n" +
                "      \"shapeType\": \"BEAN\"\n" +
                "    },\n" +
                "    \"author\": \"Werner Diwischek\",\n" +
                "    \"rolePermissions\": {\n" +
                "      \"read\": \"guest\"\n" +
                "    }\n" +
                "  }\n" +
                "}";
        EO modelEo = ProviderConfigMapsDev.createEo( json);
    }

    @Test
    public void TEST__ofValue_AnObject_myString_value__get_myString_value() {
        AnObject anObject = new AnObject()
                .setMyString("value");
        EO eoRoot = EoRoot.ofValue(ProviderConfigMaps.CONFIG_MAPS, anObject);
        Assertions.assertThat(eoRoot.get("myString"))
                .isEqualTo("value");
        Assertions.assertThat(eoRoot.get() == anObject)
                .isTrue();
    }
}


