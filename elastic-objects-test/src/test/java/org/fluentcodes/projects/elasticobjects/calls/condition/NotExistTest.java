package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.TestProviderAnObjectJson;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.condition.EqTest.DATA_LIST;


public class NotExistTest {
    @Test
    public void testString__filter_eoString__false()  {
        NotExists notExists = new NotExists(AnObject.MY_STRING);
        EO eo = TestProviderAnObjectJson.STRING.createEoDev();

        Assertions.assertThat( notExists.filter(eo)).isFalse();
    }

    @Test
    public void key__filter_eoString__true()  {
        NotExists notExists = new NotExists("key");
        EO eo = TestProviderAnObjectJson.STRING.createEoDev();

        Assertions.assertThat( notExists.filter(eo)).isTrue();
    }

    @Test
    public void _0__filterList__false()  {
        NotExists notExists = new NotExists("0");
        Assertions.assertThat( notExists.filter(DATA_LIST)).isFalse();
    }

    @Test
    public void _6__filterList__true()  {
        NotExists notExists = new NotExists("6");
        Assertions.assertThat( notExists.filter(DATA_LIST)).isTrue();
    }
}
