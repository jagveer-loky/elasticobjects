package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.domain.test.TestProviderAnObjectJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

public class EqTest {
    private static final String DATA_LIST_STRING = "[\"test\",\n\"testOther\",\n" + null + ",\n\"key0\",\n1]";
    static final EO DATA_LIST_DEV = ProviderConfigMapsDev.createEo(DATA_LIST_STRING);
    static final List DATA_LIST = (List) DATA_LIST_DEV.get();

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
        Assertions.assertThat(eq.filter(DATA_LIST)).isFalse();
    }

    @Test
    public void _3_string__filterList__false() {
        Eq eq = new Eq("3", "string");
        Assertions.assertThat(eq.filter(DATA_LIST)).isFalse();
    }

    @Test
    public void _4_1__filterList__true() {
        Eq eq = new Eq("4", 1);
        Assertions.assertThat(eq.filter(AndTest.EXAMPLE_LIST)).isTrue();
    }

    @Test
    public void testString_test__createQuery__expected() {
        Eq eq = new Eq(AnObject.MY_STRING, "test");
        Assert.assertEquals(AnObject.MY_STRING, eq.getKey());
        Assert.assertEquals("test", eq.getValue());
        Assert.assertEquals(AnObject.MY_STRING + "=:" + AnObject.MY_STRING + "_0 ", eq.createQuery(new HashMap<>()));
    }

}
