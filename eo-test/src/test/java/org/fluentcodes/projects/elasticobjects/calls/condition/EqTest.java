package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.AnObject;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderAnObjectJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class EqTest {
    private static final Logger LOG = LogManager.getLogger(EqTest.class);

    @Test
    public void testString_test__filter_eoString__true() {
        Eq eq = new Eq(AnObject.MY_STRING, "test");
        EO eo = TestProviderAnObjectJson.STRING.createEoDev();

        Assertions.assertThat(eq.filter(eo)).isTrue();
    }

    @Test
    public void testString_test__filter_eoString__false() {
        Eq eq = new Eq(AnObject.MY_STRING, "testOther");
        EO eo = TestProviderAnObjectJson.STRING.createEoDev();

        Assertions.assertThat(eq.filter(eo)).isFalse();
    }

    @Test
    public void _0_string__filterList__false() {
        Eq eq = new Eq("0", "string");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(eq.filter(row)).isFalse();
    }

    @Test
    public void _3_string__filterList__false() {
        Eq eq = new Eq("3", "string");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(eq.filter(row)).isFalse();
    }

    @Test
    public void _3_1__filterList__true() {
        Eq eq = new Eq("3", 1);
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(eq.filter(row)).isTrue();
    }

    @Test
    public void testString_test__createQuery__expected() {
        Eq eq = new Eq(AnObject.MY_STRING, "test");
        Assert.assertEquals(AnObject.MY_STRING, eq.getKey());
        Assert.assertEquals("test", eq.getValue());
        Assert.assertEquals(AnObject.MY_STRING + "=:" + AnObject.MY_STRING + "_0 ", eq.createQuery(new HashMap<>()));
    }

}
