package org.fluentcodes.projects.elasticobjects.calls.like;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.TestProviderAnObjectJson;
import org.fluentcodes.projects.elasticobjects.calls.condition.Like;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.junit.Test;

import java.util.List;


public class LikeTest {

    @Test
    public void testString_string__filter_eoString__true()  {
        Like like = new Like(AnObject.MY_STRING, "test");
        EO eo = TestProviderAnObjectJson.STRING.createEoDev();

        Assertions.assertThat( like.filter(eo)).isTrue();
    }

    @Test
    public void testString_stringOther__filter_eoString__false()  {
        Like like = new Like(AnObject.MY_STRING, "stringOther");
        EO eo = TestProviderAnObjectJson.STRING.createEoDev();

        Assertions.assertThat( like.filter(eo)).isFalse();
     }

    @Test
    public void _0_string__filter_rowList__true()  {
        Like like = new Like("0", "test");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( like.filter(row)).isTrue();
    }

    @Test
    public void _2_stringOther__filter_rowList__false()  {
        Like like = new Like("2", "stringOther");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( like.filter(row)).isFalse();
    }

    @Test
    public void _3_s1__filter_rowList__true()  {
        Like like = new Like("3", "1");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( like.filter(row)).isTrue();
    }
}
