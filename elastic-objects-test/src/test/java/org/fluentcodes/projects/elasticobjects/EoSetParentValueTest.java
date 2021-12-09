package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoSetParentValueTest {

    @Test
    public void DEV__null__exception() {
        final EO rootEo = ProviderConfigMapsDev.createEo();
        Assertions.assertThatThrownBy(() -> {
            ((EoChild) rootEo).setParentValue(null);
        })
                .isInstanceOf(EoInternalException.class)
                .hasMessageContaining("Root has no parent!");
    }

    @Test
    public void DEV_root__exception() {
        final EO rootEo = ProviderConfigMapsDev.createEo();
        Assertions.assertThatThrownBy(() -> {
            ((EoChild) rootEo).setParentValue("value");
        })
                .isInstanceOf(EoInternalException.class)
                .hasMessageContaining("Root has no parent!");
    }


    @Test
    public void givenDevString_whenSetOtherString_thenIsChanged() {
        final EO eo = ProviderConfigMapsDev.createEo();
        final IEOScalar childEo = eo.set("value", "key");
        Assertions.assertThat(childEo.isChanged()).isFalse();
        ((EoChild) childEo).setParentValue("valueOther");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(childEo.get()).isEqualTo("valueOther");
        Assertions.assertThat(childEo.isChanged()).isTrue();
    }

}


