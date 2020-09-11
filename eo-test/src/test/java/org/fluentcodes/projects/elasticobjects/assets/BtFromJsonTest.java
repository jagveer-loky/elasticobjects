package org.fluentcodes.projects.elasticobjects.assets;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class BtFromJsonTest {
    private static final Logger LOG = LogManager.getLogger(BtFromJsonTest.class);

    @Test
    public void givenJsonTypedDouble_thenDouble()  {
        EO eo = TestProviderBtJson.DOUBLE_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.get(BasicTest.TEST_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }
    @Test
    public void givenJsonUntypedDouble_thenDouble()  {
        BasicTest bt = TestProviderBtJson.DOUBLE.createBt();
        Assertions.assertThat(bt.getTestDouble()).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void testFloat()  {
        EO eo = TestProviderBtJson.FLOAT.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(BasicTest.TEST_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void testSmall()  {
        EO eo = TestProviderBtJson.SMALL.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(BasicTest.class);
        Assertions.assertThat(eo.get(BasicTest.TEST_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(BasicTest.TEST_INTEGER)).isEqualTo((S_INTEGER));
    }

    @Test
    public void givenAllUnTyped_thenNoLog()  {
        EO eo = TestProviderBtJson.ALL.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, BasicTest.class, eo.getModelClass());
        Assertions.assertThat(eo.get(BasicTest.TEST_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(BasicTest.TEST_INTEGER)).isEqualTo((S_INTEGER));
    }

    @Test
    public void givenSubTestUnTyped_thenNoLog()  {
        EO eo = TestProviderBtJson.SUB_TEST.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, BasicTest.class, eo.getModelClass());
        Assertions.assertThat(eo.get(BasicTest.SUB_TEST, BasicTest.TEST_STRING)).isEqualTo((S_STRING));
    }
}
