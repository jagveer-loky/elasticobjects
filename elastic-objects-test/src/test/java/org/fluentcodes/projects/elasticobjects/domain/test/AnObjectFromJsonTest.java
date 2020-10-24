package org.fluentcodes.projects.elasticobjects.domain.test;


import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_DOUBLE;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.SAMPLE_FLOAT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

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
    public void scopeTest__small__xpected()  {
        EO eo = TestProviderAnObjectJson.SMALL.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo((S_INTEGER));
    }

    @Test
    public void scopeTest__all__expected()  {
        EO eo = TestProviderAnObjectJson.ALL.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(AnObject.MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(AnObject.MY_INT)).isEqualTo((S_INTEGER));
    }

    @Test
    public void scopeDev__SubObjectJson__thenNoLog()  {
        EO eo = TestProviderAnObjectJson.SUB_TEST.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(AnObject.MY_ASUB_OBJECT, AnObject.MY_STRING)).isEqualTo((S_STRING));
    }
}
