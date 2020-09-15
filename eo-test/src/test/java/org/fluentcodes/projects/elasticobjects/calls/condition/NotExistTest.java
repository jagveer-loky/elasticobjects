package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.junit.Test;

import java.util.List;


public class NotExistTest {
    @Test
    public void testString__filter_eoString__false()  {
        NotExists notExists = new NotExists("testString");
        EO eo = TestProviderBtJson.STRING.createEoDev();

        Assertions.assertThat( notExists.filter(eo)).isFalse();
    }

    @Test
    public void key__filter_eoString__true()  {
        NotExists notExists = new NotExists("key");
        EO eo = TestProviderBtJson.STRING.createEoDev();

        Assertions.assertThat( notExists.filter(eo)).isTrue();
    }

    @Test
    public void _0__filterList__false()  {
        NotExists notExists = new NotExists("0");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( notExists.filter(row)).isFalse();
    }

    @Test
    public void _6__filterList__true()  {
        NotExists notExists = new NotExists("6");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( notExists.filter(row)).isTrue();
    }
}
