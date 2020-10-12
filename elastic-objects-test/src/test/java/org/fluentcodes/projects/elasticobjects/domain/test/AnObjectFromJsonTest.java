package org.fluentcodes.projects.elasticobjects.domain.test;


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

public class AnObjectFromJsonTest {

    @Test
    public void givenJsonTypedDouble_thenDouble()  {
        EO eo = TestProviderAnObjectJson.DOUBLE_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(AnObject.MY_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }
    @Test
    public void givenJsonUntypedDouble_thenDouble()  {
        AnObject bt = TestProviderAnObjectJson.DOUBLE.createBt();
        Assertions.assertThat(bt.getMyDouble()).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void testFloat()  {
        EO eo = TestProviderAnObjectJson.FLOAT.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(AnObject.MY_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void AnObjectSmall____$()  {
        EO eo = TestProviderAnObjectJson.SMALL.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo((S_INTEGER));
    }

    @Test
    public void givenAllUnTyped_thenNoLog()  {
        EO eo = TestProviderAnObjectJson.ALL.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, AnObject.class, eo.getModelClass());
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo((S_INTEGER));
    }

    @Test
    public void scope_dev__ASubObjectJson__thenNoLog()  {
        EO eo = TestProviderAnObjectJson.SUB_TEST.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, AnObject.class, eo.getModelClass());
        Assertions.assertThat(eo.get(AnObject.MY_ASUB_OBJECT, AnObject.MY_STRING)).isEqualTo((S_STRING));
    }
}
