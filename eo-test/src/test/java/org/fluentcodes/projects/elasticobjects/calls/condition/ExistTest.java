package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.AnObject;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderAnObjectJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.junit.Test;

import java.util.List;

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
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( exists.filter(row)).isTrue();
    }

    @Test
    public void _6__filterList__false()  {
        Exists exists = new Exists("6");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( exists.filter(row)).isFalse();
    }
}
