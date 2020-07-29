package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.fileprovider.*;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.assertEquals;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EoSetListTest {
    private static final Logger LOG = LogManager.getLogger(EoSetListTest.class);

    @Test
    public void givenListEmpty_withJsonListSmall_ok()  {
        final EO eoEmpty = TestProviderRootDev.createEo();
        eoEmpty.set(TestProviderListJson.JSON_SMALL.content(), S_LEVEL0);
        Assertions.assertThat(eoEmpty.getEo(S_LEVEL0).getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, S1)).isEqualTo(S_INTEGER);
        Assertions.assertThat(eoEmpty.get(S_LEVEL0, S0)).isEqualTo(S_STRING);
    }

    @Test
    public void givenList123Dev_hasIntegerValues()  {
        final EO eo = TestProviderMapJson.LIST_DOUBLE123.createMapEo();
        Assertions.assertThat(eo.getEo("source").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("source", "0").getModelClass()).isEqualTo(Integer.class);
    }
    @Test
    public void givenList123Test_hasDoubleValues()  {
        final EO eo = TestProviderMapJsn.LIST_DOUBLE123.createMapEo();
        Assertions.assertThat(eo.getEo("source").getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.getEo("source", "0").getModelClass()).isEqualTo(Double.class);
    }
}

