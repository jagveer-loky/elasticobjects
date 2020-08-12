package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoListRootTest {
    private static final Logger LOG = LogManager.getLogger(EoListRootTest.class);

    @Test
    public void givenDev_whenNewWithArrayListValue_thenMap()  {
        final EO eo = ProviderRootDevScope.createEo(new ArrayList<>());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(ArrayList.class);
    }

    @Test
    public void givenDev_whenNewWithListClass_thenMap()  {
        final EO eo = ProviderRootDevScope.createEo(List.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
    }

    @Test
    public void givenDev_whenNewWithListStringClass_thenListString()  {
        final EO eo = ProviderRootDevScope.createEo(List.class, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(List.class.getSimpleName() + "," + String.class.getSimpleName());
    }

    @Test
    public void givenDev_whenNewWithListMapClass_thenListMap()  {
        final EO eo = ProviderRootDevScope.createEo(List.class, Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(List.class.getSimpleName() + "," + Map.class.getSimpleName());
    }
}


