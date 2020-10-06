package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.TestProviderAnObjectJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoMapObjectMapTest {

    @Test
    public void givenDevString_whenMapWithMap_fails()  {
        final EO eo = ProviderRootDevScope.createEo(S_STRING);
        eo.mapObject(new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
    }


    @Test
    public void givenDev_whenMapMapWithString_thenValueIsSet()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject(TestProviderAnObjectJson.STRING.createMapDev());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo(S_STRING);
    }
}


