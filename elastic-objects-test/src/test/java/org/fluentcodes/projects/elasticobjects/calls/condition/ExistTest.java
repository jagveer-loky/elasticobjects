package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.TestProviderAnObjectJson;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.condition.EqTest.DATA_LIST;

public class ExistTest {
    @Test
    public void testString__filter_eoString__true()  {
        Exists exists = new Exists(AnObject.MY_STRING);
        EO eo = TestProviderAnObjectJson.STRING.createEoDev();

        Assertions.assertThat( exists.filter(eo)).isTrue();
    }

    @Test
    public void key__filter_eoString__false()  {
        Exists exists = new Exists("key");
        EO eo = TestProviderAnObjectJson.STRING.createEoDev();

        Assertions.assertThat( exists.filter(eo)).isFalse();
    }

    @Test
    public void _0__filterList__true()  {
        Exists exists = new Exists("0");
        Assertions.assertThat( exists.filter(DATA_LIST)).isTrue();
    }

    @Test
    public void _6__filterList__false()  {
        Exists exists = new Exists("6");
        Assertions.assertThat( exists.filter(DATA_LIST)).isFalse();
    }
}
