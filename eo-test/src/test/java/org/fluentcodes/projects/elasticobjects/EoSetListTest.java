package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderMapJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EoSetListTest {
    private static final Logger LOG = LogManager.getLogger(EoSetListTest.class);

    @Test
    public void givenListEmpty_withJsonListSmall_ok()  {
        final EO eoEmpty = ProviderRootDevScope.createEo();
        eoEmpty.set(ProviderMapJson.LIST_SMALL.content(), S_LEVEL0);
        Assertions.assertThat(eoEmpty.getEo(S_LEVEL0).getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, S1)).isEqualTo(S_INTEGER);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, S0)).isEqualTo(S_STRING);
    }

    @Test
    public void givenListDouble123Dev_hasDoubleValues()  {
        final EO eo0 = ProviderRootTestScope.createEo();
        ProviderRootTestScope.findModel(BasicTest.class);
        final EO eo = ProviderMapJson.LIST_DOUBLE123.createMapEo();
        Assertions.assertThat(eo.getEo("source").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("source", "0").getModelClass()).isEqualTo(Double.class);
    }

    @Test
    public void givenList123_hasIntegerValues()  {
        final EO eo = ProviderMapJson.LIST_123.createMapEo();
        Assertions.assertThat(eo.getEo("source").getModelClass()).isEqualTo(ArrayList.class);
        Assertions.assertThat(eo.getEo("source", "0").getModelClass()).isEqualTo(Integer.class);
    }

    @Test
    public void givenList123Typed_hasDoubleValues()  {
        final EO eo = ProviderMapJson.LIST_123_TYPED.createMapEo();
        Assertions.assertThat(eo.getEo("source").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("source", "0").getModelClass()).isEqualTo(Double.class);
    }
}

