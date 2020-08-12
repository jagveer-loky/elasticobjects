package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.*;
import org.junit.Test;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 11.8.2020
 */

public class EoMapRootTest {

    @Test
    public void givenDev_whenNewEmpty_thenMap()  {
        final EO eo = ProviderRootDevScope.createEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.isEmpty()).isTrue();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenDev_whenNewWithLinkedHashMapValue_thenMap()  {
        final EO eo = ProviderRootDevScope.createEo(new LinkedHashMap());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(LinkedHashMap.class);
    }

    @Test
    public void givenDev_whenNewWithMapClass_thenMap()  {
        final EO eo = ProviderRootDevScope.createEo(Map.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenDev_whenNewWithMapStringValue_thenMapString()  {
        final EO eo = ProviderRootDevScope.createEo(Map.class, String.class);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getModels().toString())
                .isEqualTo(Map.class.getSimpleName() + "," + String.class.getSimpleName());
    }

    @Test
    public void givenEoMap_whenMapObjectString_fails()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject(S_STRING);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenMap_withNull_ok_nothingChanged()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject(null);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

}


