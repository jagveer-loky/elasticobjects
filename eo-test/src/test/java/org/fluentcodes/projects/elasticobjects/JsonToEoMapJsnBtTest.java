package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class JsonToEoMapJsnBtTest {
    private static final Logger LOG = LogManager.getLogger(JsonToEoMapJsnBtTest.class);

    @Test
    public void testDouble()  {
        EO eo = TestProviderBtJson.DOUBLE_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.get(F_TEST_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }
    @Test
    public void testDoubleObject()  {
        BasicTest bt = TestProviderBtJson.DOUBLE_TYPED.createBt();
        Assertions.assertThat(bt.getTestDouble()).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void testFloat()  {
        EO eo = TestProviderBtJson.FLOAT_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(F_TEST_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void givenJsonSmallType_thenSetValues()  {
        EO eo = TestProviderBtJson.SMALL_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(F_TEST_INTEGER)).isEqualTo((S_INTEGER));
    }

    @Test
    public void testAll()  {
        EO eo = TestProviderBtJson.ALL_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, BasicTest.class, eo.getModelClass());
        Assertions.assertThat(eo.get(F_TEST_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(F_TEST_INTEGER)).isEqualTo((S_INTEGER));
    }
}
