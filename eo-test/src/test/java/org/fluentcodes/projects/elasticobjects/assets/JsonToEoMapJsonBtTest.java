package org.fluentcodes.projects.elasticobjects.assets;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderMapJson;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class JsonToEoMapJsonBtTest {
    private static final Logger LOG = LogManager.getLogger(JsonToEoMapJsonBtTest.class);

    @Test
    public void testDouble()  {
        EO eo = ProviderMapJson.DOUBLE.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.get(F_TEST_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }
    @Test
    public void testDoubleObject()  {
        BasicTest bt = ProviderMapJson.DOUBLE.createBt();
        Assertions.assertThat(bt.getTestDouble()).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void testFloat()  {
        EO eo = ProviderMapJson.FLOAT.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(F_TEST_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void testSmall()  {
        EO eo = ProviderMapJson.SMALL.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(F_TEST_INTEGER)).isEqualTo((S_INTEGER));
    }

    @Test
    public void testAll()  {
        EO eo = ProviderMapJson.ALL.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, BasicTest.class, eo.getModelClass());
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(F_TEST_INTEGER)).isEqualTo((S_INTEGER));
    }
}
