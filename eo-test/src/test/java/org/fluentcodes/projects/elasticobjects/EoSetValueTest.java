package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoSetValueTest {
    private static final Logger LOG = LogManager.getLogger(EoSetValueTest.class);

    @Test
    public void givenDev_whenSetNull_thenNothingChanged()  {
        final EO eo = ProviderRootDevScope.createEo();
        ((EoChild)eo).setValue(null);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenDev_whenSetString_thenHasLog()  {
        final EO eo = ProviderRootDevScope.createEo();
        ((EoChild)eo).setValue(S_STRING);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(((Map)eo.get()).size()).isEqualTo(0);
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenDevString_whenSetMap_thenHasLog()  {
        final EO eo = ProviderRootDevScope.createEo("");
        ((EoChild)eo).setValue(new LinkedHashMap<>());
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.get()).isEqualTo("");
        Assertions.assertThat(eo.getModelClass()).isEqualTo(String.class);
    }

    @Test
    public void givenDevString_whenSetOtherString_thenIsChanged()  {
        final EO eo = ProviderRootDevScope.createEo("");
        Assertions.assertThat(eo.isChanged()).isFalse();
        ((EoChild)eo).setValue(S_STRING);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING);
        Assertions.assertThat(eo.isChanged()).isTrue();
    }

}


