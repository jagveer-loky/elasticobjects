package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek 14.8.2020..
 */
public class EoSerializationTypeTest {

    @Test
    public void givenDev_thenSerializationTypeEo()  {
        EO eo = ProviderRootDevScope.createEo();
        Assertions.assertThat(eo.getSerializationType()).isEqualTo(JSONSerializationType.EO);
        Assertions.assertThat(eo.sizeEo()).isEqualTo(0);
    }

    @Test
    public void givenDev_whenSetSerializationTypeStandard_thenSerializationTypeEo()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getSerializationType()).isEqualTo(JSONSerializationType.STANDARD);
        Assertions.assertThat(eo.sizeEo()).isEqualTo(1);
        Assertions.assertThat(((EoChild)eo.getEo(PathElement.SERIALIZATION_TYPE)).isChanged()).isFalse();
    }

    @Test
    public void givenDev_whenSetSerializationTypeStandard2_thenIsChanged()  {
        EO eo = ProviderRootDevScope.createEo();
        eo.setSerializationType(JSONSerializationType.STANDARD);

        eo.setSerializationType(JSONSerializationType.EO);
        Assertions.assertThat(eo.getSerializationType()).isEqualTo(JSONSerializationType.EO);
        Assertions.assertThat(((EoChild)eo.getEo(PathElement.SERIALIZATION_TYPE)).isChanged()).isTrue();
        Assertions.assertThat(eo.sizeEo()).isEqualTo(1);

    }
}
