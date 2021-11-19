package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

/**
 * Created by werner.diwischek 14.8.2020..
 */
public class EoSerializationTypeTest {

    @Test
    public void givenDev_thenSerializationTypeEo()  {
        EO eo = ProviderConfigMaps.createEoDev();
        Assertions.assertThat(eo.getSerializationType()).isEqualTo(JSONSerializationType.EO);
        Assertions.assertThat(eo.size()).isEqualTo(0);
    }

    @Test
    public void givenDev_whenSetSerializationTypeStandard_thenSerializationTypeEo()  {
        EO eo = ProviderConfigMaps.createEoDev();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getSerializationType()).isEqualTo(JSONSerializationType.STANDARD);
        Assertions.assertThat(eo.size()).isEqualTo(0);
        Assertions.assertThat(((EoChild)eo.getEo(PathElement.SERIALIZATION_TYPE)).isChanged()).isFalse();
    }

    @Test
    public void givenDev_whenSetSerializationTypeStandard2_thenIsChanged()  {
        EO eo = ProviderConfigMaps.createEoDev();
        eo.setSerializationType(JSONSerializationType.STANDARD);

        eo.setSerializationType(JSONSerializationType.EO);
        Assertions.assertThat(eo.getSerializationType()).isEqualTo(JSONSerializationType.EO);
        Assertions.assertThat(((EoChild)eo.getEo(PathElement.SERIALIZATION_TYPE)).isChanged()).isTrue();
        Assertions.assertThat(eo.size()).isEqualTo(0);

    }
}
