package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.*;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Test;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_KEY;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoRootMapTest {

    @Test
    public void givenDev_whenNewEmpty_thenMap()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenDev_whenSetRootModelList_thenList()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS);
        eo.set(List.class.getSimpleName(), PathElement.ROOT_MODEL);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
    }

    @Test
    public void givenDevNotEmpty_whenSetRootModelList_thenExceptionThrown()  {
        final EO eo = TestProviderJson.MAP_SMALL_WITH_KEY.getEoDev();
        Assertions.assertThatThrownBy(()->{eo.set(List.class.getSimpleName(), PathElement.ROOT_MODEL);})
                .hasMessage("Non empty root element, no models could be changed");
    }

    @Test
    public void givenDevChild_whenSetRootModelList_thenExceptionThrown()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS);
        EO child = eo.set(S_STRING, S_KEY);
        Assertions.assertThatThrownBy(()->{child.set(List.class.getSimpleName(), PathElement.ROOT_MODEL);})
                .hasMessage("No Root element, no models could be changed");

    }

    @Test
    public void givenDev_whenNewNull_thenValueIsNull()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, null);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenDev_whenNewWithLinkedHashMapValue_thenMap()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(LinkedHashMap.class);
    }

    @Test
    public void givenDev_whenNewWithMapClass_thenMap()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenDev_whenNewWithMapStringClass_thenMapString()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, Map.class, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(Map.class.getSimpleName() + "," + String.class.getSimpleName());
    }

    @Test
    public void givenDev_whenNewWithMapListClass_thenMapString()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, Map.class, List.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(Map.class.getSimpleName() + "," + List.class.getSimpleName());
    }

    @Test
    public void givenDev_whenJsonMapEmpty_thenMap()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, "{}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
        new XpectEo<>().compareAsString(eo);
    }

    @Test
    public void givenDev_whenJsonMap_thenXpected()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, "{\"store\":\"value\"}");
        new XpectEo<>().compareAsString(eo);
    }

}


