package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoSetParentValueTest {

    @Test
    public void DEV__null__exception()  {
        final EO rootEo = ProviderRootDevScope.createEo();
        Assertions.assertThatThrownBy(()->{((EoChild)rootEo).setParentValue(null);})
            .isInstanceOf(EoException.class)
                .hasMessageContaining("Root has no parent!");
    }

    @Test
    public void DEV__value_exception()  {
        final EO rootEo = ProviderRootDevScope.createEo();
        Assertions.assertThatThrownBy(()->{((EoChild)rootEo).setParentValue("value");})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Root has no parent!");
    }


    @Test
    public void givenDevString_whenSetOtherString_thenIsChanged()  {
        final EO eo = ProviderRootDevScope.createEo();
        final EO childEo = eo.set("value", "key");
        Assertions.assertThat(childEo.isChanged()).isFalse();
        ((EoChild)childEo).setParentValue("valueOther");
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(childEo.get()).isEqualTo("valueOther");
        Assertions.assertThat(childEo.isChanged()).isTrue();
    }

}


