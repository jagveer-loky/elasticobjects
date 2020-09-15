package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EoMapObjectScalarTest {
    private static final Logger LOG = LogManager.getLogger(EoMapObjectScalarTest.class);

    @Test
    public void givenDev_whenMapNull_thenNothingChanged()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject(null);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }

    @Test
    public void givenDevString_whenMapIntegerValue_thenSetAsString()  {
        final EO eo = ProviderRootDevScope.createEo(String.class);
        eo.mapObject(S_INTEGER);
        Assertions.assertThat(eo.get()).isEqualTo(S_INTEGER.toString());
    }

    @Test
    public void givenDevString_withBoolean_ok()  {
        final EO eo = ProviderRootDevScope.createEo(String.class);
        eo.mapObject(S_BOOLEAN);
        Assertions.assertThat(eo.get()).isEqualTo(S_BOOLEAN.toString());
    }

    @Test
    public void givenDevString_whenMapOtherString_thenIsChanged()  {
        final EO eo = ProviderRootDevScope.createEo("");
        eo.mapObject(S_STRING_OTHER);
        Assertions.assertThat(eo.get()).isEqualTo(S_STRING_OTHER);
        Assertions.assertThat(eo.isChanged()).isTrue();
    }

    @Test
    public void givenDevMap_whenMapString_thenHasLog()  {
        final EO eo = ProviderRootDevScope.createEo();
        eo.mapObject(S_STRING);
        Assertions.assertThat(eo.getLog()).isNotEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(Map.class);
    }


    @Test
    public void givenDevList_whenMapBoolean_thenHasErrors()  {
        final EO root = ProviderRootDevScope.createEo(List.class);
        root.mapObject(S_BOOLEAN);
        Assertions.assertThat(root.getLog()).isNotEmpty();
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, root.getLog().isEmpty());
    }

    @Test
    public void givenDevList_WhenMapJsonBoolean_thenValueMappedToZero()  {
        final EO root = ProviderRootDevScope.createEo(List.class);
        root.mapObject(TestProviderBtJson.BOOLEAN.content());
        Assertions.assertThat(root.getLog()).isEmpty();
        Assert.assertEquals(1, root.keysEo().size());
        Assert.assertEquals(1, ((EoChild) root).keysValue().size());
        Assert.assertEquals(S_BOOLEAN, root.get(S0));
    }

    @Test
    public void givenListStringEmpty_withBTBoolean_thenBooleanIsSetAsString()  {
        EO root = ProviderRootDevScope.createEo(List.class, String.class);
        root
                .mapObject(TestProviderBtJson.BOOLEAN.content());
        Assert.assertEquals(1, root.keys().size());
        Assert.assertEquals(1, ((EoChild) root).keysValue().size());
        Assert.assertEquals(S_BOOLEAN.toString(), root.get(S0));
    }


}


