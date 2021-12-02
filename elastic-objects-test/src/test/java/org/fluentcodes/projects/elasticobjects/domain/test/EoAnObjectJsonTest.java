package org.fluentcodes.projects.elasticobjects.domain.test;


import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_DOUBLE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_FLOAT;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_INTEGER;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.S_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_DOUBLE;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_FLOAT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_INT;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_STRING;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class EoAnObjectJsonTest {

    @Test
    public void testDouble()  {
        EO eo = TestProviderAnObjectJson.DOUBLE_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(MY_DOUBLE)).isEqualTo((SAMPLE_DOUBLE));
    }
    @Test
    public void testDoubleObject()  {
        AnObject bt = TestProviderAnObjectJson.DOUBLE_TYPED.createBt();
        Assertions.assertThat(bt.getMyDouble()).isEqualTo((SAMPLE_DOUBLE));
    }

    @Test
    public void testFloat()  {
        EO eo = TestProviderAnObjectJson.FLOAT_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get(MY_FLOAT)).isEqualTo((SAMPLE_FLOAT));
    }

    @Test
    public void givenJsonSmallType_thenSetValues()  {
        EO eo = TestProviderAnObjectJson.SMALL_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(MY_INT)).isEqualTo((S_INTEGER));
    }

    @Test
    public void testAll()  {
        EO eo = TestProviderAnObjectJson.ALL_TYPED.createBtEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(AnObject.class);
        Assertions.assertThat(eo.get(MY_STRING)).isEqualTo((S_STRING));
        Assertions.assertThat(eo.get(MY_INT)).isEqualTo((S_INTEGER));
    }
}
