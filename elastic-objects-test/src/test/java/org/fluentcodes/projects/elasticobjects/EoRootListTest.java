package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoRootListTest {

    @Test
    public void givenDev_whenNewWithArrayListValue_thenMap()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, new ArrayList());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.get().getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(((List)eo.get()).size()).isEqualTo(0);
    }

    @Test
    public void givenDev_whenNewWithListClass_thenMap()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS,List.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get().getClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.isEoEmpty()).isTrue();
        Assertions.assertThat(((List)eo.get()).size()).isEqualTo(0);
    }

    @Test
    public void givenDev_whenNewWithListStringClass_thenListString()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, List.class, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(List.class.getSimpleName() + "," + String.class.getSimpleName());
    }

    @Test
    public void givenDev_whenNewWithListMapClass_thenListMap()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, List.class, Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(List.class.getSimpleName() + "," + Map.class.getSimpleName());
    }

    @Test
    public void givenDev_whenJsonListEmpty_thenXpected()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS, "[]");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        new XpectEo<>().compareAsString(eo);
    }

    @Test
    public void givenDev_whenListDoubleWithRootModel_thenXpected()  {
        final EO eo = new EoRoot(ProviderRootDevScope.EO_CONFIGS);
        eo.mapObject("{\"_rootmodel\":\"List,Double\", \"0\":1,\"1\":2}");
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        new XpectEo<>().compareAsString(eo);
    }
}


