package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class AndTest {

    //static final Pattern ifPattern = Pattern.compile("(key).*");
    @Test
    public void eq_testString_string__createQuery__expected() {
        And and = new And(new Eq("testString", "test"));
        Assertions
                .assertThat(and.getCondition(0).getKey())
                .isEqualTo("testString");
        Assertions
                .assertThat(and.getCondition(0).getValue())
                .isEqualTo("test");
        Assertions
                .assertThat(and.getCondition(0).createQuery(new HashMap<>()))
                .isEqualTo("testString=:testString_0 ");
        Assert.assertEquals(S_STRING, and.getCondition(0).getValue());
    }

    @Test
    public void eq_key0_test_and_eq_key1_testOther__createQuery__expected() {
        And and = new And(
                new Eq("key0", "test"),
                new Eq("key1", "stringOther"));
        Assertions
                .assertThat(and.getCondition(0).getValue())
                .isEqualTo("test");
        Assertions
                .assertThat(and.getCondition(1).getKey())
                .isEqualTo("key1");
        Assertions
                .assertThat(and.getCondition(1).createQuery(new HashMap<>()))
                .isEqualTo("key1=:key1_0 ");
    }

    @Test
    public void eq_testString_test__filter_eoString__true()  {
        And and = new And(new Eq("testString", "test"));
        EO eo = TestProviderBtJson.STRING.createEoDev();

        Assertions.assertThat( and.filter(eo)).isTrue();
    }

    @Test
    public void eq_testString_test__filter_eoString__false()  {
        And and = new And(new Eq("testString", "testOther"));
        EO eo = TestProviderBtJson.STRING.createEoDev();

        Assertions.assertThat( and.filter(eo)).isFalse();
    }



    @Test
    public void like_0_test__filterList__true()  {
        And and = new And(new Like("0", "test"));
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(and.filter(row)).isTrue();
    }

    @Test
    public void like_2_test__filterList__false()  {
        And and = new And(new Like("2", "test"));
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(and.filter(row)).isFalse();
    }

    @Test
    public void like_3_1__filterList__true()  {
        And and = new And(new Like("3", 1));
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(and.filter(row)).isTrue();
    }

    @Test
    public void like_0_test_and_like_3_1__filterList__true()  {
        And and = new And(new Like("0", "test"), new Like("3", 1));
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(and.filter(row)).isTrue();
    }

    @Test
    public void like_0_testFalse_and_like_3_1__filterList__false()  {
        And and = new And(new Like("0", "testFalse"), new Like("3", 1));
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(and.filter(row)).isFalse();
    }

    @Test
    public void string_like_0_test_and_like_3_1__filterList__true()  {
        And and = new And("0 like test && 3 like 1");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(and.filter(row)).isTrue();
    }

    @Test
    public void string_like_0_testFalse_and_like_3_1__filterList__false()  {
        And and = new And("0 like testFalse && 3 like 1");
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat(and.filter(row)).isFalse();
    }
}
