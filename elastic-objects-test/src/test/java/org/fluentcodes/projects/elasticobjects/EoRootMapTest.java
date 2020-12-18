package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJson;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_KEY;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoRootMapTest {

    @Test
    public void DEV__newEmpty__mapClass()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }


    @Test
    public void DEV_NotEmpty__set_rootModel__exeption()  {
        final EO rootEo = TestProviderJson.MAP_SMALL_WITH_KEY.getEoDev();
        Assertions.assertThatThrownBy(()->{rootEo.set(List.class.getSimpleName(), PathElement.ROOT_MODEL);})
                .hasMessage("Could not change model with a set");
    }

    @Test
    public void DEV_string_key__set_string__exception()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS);
        EO child = eo.set(S_STRING, S_KEY);
        Assertions.assertThatThrownBy(()->{child.set(List.class.getSimpleName(), PathElement.ROOT_MODEL);})
                .hasMessage("Could not change model with a set");
    }

    @Test
    public void DEV__empty__ModelClassMap()  {
        final EO eo = ProviderRootDevScope.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void DEV__LinkedHashMap__ModelClassLinkedHashMap()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS, new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(LinkedHashMap.class);
    }

    @Test
    public void DEV__Map_class__ModelClassMap()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS, Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void DEV__NewWithMapStringClass_thenMapString()  {
        final EO eo = EoRoot.OF_CLASS(ProviderRootDevScope.EO_CONFIGS, Map.class, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(Map.class.getSimpleName() + "," + String.class.getSimpleName());
    }

    @Test
    public void DEV__NewWithMapListClass_thenMapString()  {
        final EO eo = EoRoot.OF_CLASS(ProviderRootDevScope.EO_CONFIGS, Map.class, List.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(Map.class.getSimpleName() + "," + List.class.getSimpleName());
    }

    @Test
    public void DEV__JsonMapEmpty__Map()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS, "{}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        new XpectEo<>().compareAsString(eo);
    }

    @Test
    public void DEV__JsonMap__xpected()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS, "{\"store\":\"value\"}");
        new XpectEo<>().compareAsString(eo);
    }

    @Test
    public void DEV__map_putKeyValue__eoGetKeyValue()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS);
        Map map = (Map) eo.get();
        map.put("key", "value");
        Assertions.assertThat(eo.get("key")).isEqualTo("value");
    }

    @Test
    public void DEV__eo_setKeyValue__mapGetKeyValue()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS);
        eo.set("value","key");
        Assertions.assertThat(((Map)eo.get()).get("key")).isEqualTo("value");
    }

    @Test
    public void DEV_eo_setKeyValue__map_setKeyValue2__mapGetKeyValue()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS);
        eo.set("value","key");
        ((Map)eo.get()).put("key", "value2");
        Assertions.assertThat(eo.get("key")).isEqualTo("value2");
    }


}


