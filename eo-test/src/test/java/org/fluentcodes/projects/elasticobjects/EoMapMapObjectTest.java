package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_LEVEL0;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoMapMapObjectTest {
    @Test
    public void givenDev_whenMapStringValue_thenLogIsNotEmpty()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject(S_STRING);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenDev_whenMapNull_thenStillEmptyNoLogs()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject(null);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
    }

    @Test
    public void givenDev_whenMapJson_thenValueSet()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject("{\"" + S_LEVEL0 + "\":1}");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(S_LEVEL0)).isEqualTo(1);
    }

}


