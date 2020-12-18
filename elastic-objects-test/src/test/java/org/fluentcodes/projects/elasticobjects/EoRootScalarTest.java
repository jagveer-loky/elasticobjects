package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoRootScalarTest {

    @Test
    public void givenDev_whenNewStringClass_thenValueIsNull()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get()).isNull();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(String.class);
    }

    @Test
    public void givenDev_whenNewStringValue_thenValueIsSet()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS, "test");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get()).isEqualTo("test");
        Assertions.assertThat(eo.getModelClass()).isEqualTo(String.class);
    }

    @Test
    public void givenDev_whenNewIntegerClass_thenValueIsSet()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS, Integer.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get()).isNull();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Integer.class);
    }

    @Test
    public void givenDev_whenNewIntegerValue_thenValueIsSet()  {
        final EO eo = EoRoot.OF(ProviderRootDevScope.EO_CONFIGS, 1);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get()).isEqualTo(1);
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Integer.class);
    }



}


