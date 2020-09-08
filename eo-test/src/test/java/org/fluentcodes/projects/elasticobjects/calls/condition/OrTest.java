package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.assets.TestProviderBtJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class OrTest {
    static final Pattern ifPattern = Pattern.compile("[\\s]*([^\\s]*?)[\\s]+(eq|ne|equals|le|ge|notEquals|like|notLike|in)[\\s]+(.*)");

    //static final Pattern ifPattern = Pattern.compile("(key).*");
    @Test
    public void eq_testString_test__filter_eoString__true() {
        Or or = new Or("testString eq test");
        Assert.assertEquals("testString", or.getAnd(0).getCondition(0).getKey());
        Assert.assertEquals("test", or.getAnd(0).getCondition(0).getValue());
        Assert.assertEquals( "testString=:testString_0 ", or.getAnd(0).getCondition(0).createQuery(new HashMap<>()));
        Assert.assertEquals("(testString=:testString_0 )", or.createQuery());
        EO eo = TestProviderBtJson.STRING.createEoDev();

        Assertions.assertThat(or.filter(eo)).isTrue();
    }

    @Test
    public void eq_testString_test2__filter_eoString__false() {
        Or or = new Or("testString eq test2");
        EO eo = TestProviderBtJson.STRING.createEoDev();

        Assertions.assertThat(or.filter(eo)).isFalse();
    }

    @Test
    public void eq_testString2_test2__filter_eoString__false() {
        Or or = new Or("testString2 eq test2");
        EO eo = TestProviderBtJson.STRING.createEoDev();

        Assertions.assertThat(or.filter(eo)).isFalse();
    }

    @Test
    public void eq_key0_test0_eq_key1_test1__filter_eoKey0Test__true() {
        Or or = new Or("key0 eq test0 || key1 eq test1");
        EO eo = ProviderRootDevScope.createEo("{\"key0\":\"test0\"}");

        Assertions.assertThat(or.filter(eo)).isTrue();
    }

    @Test
    public void eq_tey0_test0_eq_key1_test1__filter_eoKey1Test1__true() {
        Or or = new Or("key0 eq test || key1 eq test1");
        EO eo = ProviderRootDevScope.createEo("{\"key1\":\"test1\"}");

        Assertions.assertThat(or.filter(eo)).isTrue();
    }

    @Test
    public void eq_key0_test0_eq_key1_test1__filter_eoKey0Test1__false() {
        Or or = new Or("key0 eq test0 || key1 eq test1");
        EO eo = ProviderRootDevScope.createEo("{\"key0\":\"test1\"}");

        Assertions.assertThat(or.filter(eo)).isFalse();
    }

    @Test
    public void eq_key0_test0_eq_key1_test1_eq_key2_test2__filter_eoKey1Test1__true() {
        Or or = new Or("key0 eq test0 || key1 eq test1  || key2 eq test2");
        EO eo = ProviderRootDevScope.createEo("{\"key1\":\"test1\"}");
        Assertions.assertThat(or.filter(eo)).isTrue();
    }

    @Test
    public void eq_key0_test0_eq_key1_test1_eq_key2_test2__filter_eoKey2Test2__true() {
        Or or = new Or("key0 eq test0 || key1 eq test1  || key2 eq test2");
        EO eo = ProviderRootDevScope.createEo("{\"key2\":\"test2\"}");

        Assertions.assertThat(or.filter(eo)).isTrue();
    }

    @Test
    public void eq_key0_test0_eq_key1_test1_eq_key2_test2__filter_eoKey0Test0__true() {
        Or or = new Or("key0 eq test0 || key1 eq test1  || key2 eq test2");
        EO eo = ProviderRootDevScope.createEo("{\"key0\":\"test0\"}");

        Assertions.assertThat(or.filter(eo)).isTrue();
    }

    @Test
    public void eq_key0_test0_eq_key1_test1_eq_key2_test2__filterEoKey0Test1__false() {
        Or or = new Or("key0 eq test0 || key1 eq test1  || key2 eq test2");
        EO eo = ProviderRootDevScope.createEo("{\"key0\":\"test1\"}");

        Assertions.assertThat(or.filter(eo)).isFalse();
    }

    @Test
    public void like_0_string__filter_rowList__true()  {
        Or or = new Or(new Like("0", "test"));
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( or.filter(row)).isTrue();
    }

    @Test
    public void like_2_stringOther__filter_rowList__false()  {
        Or or = new Or(new Like("2", "stringOther"));
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( or.filter(row)).isFalse();
    }

    @Test
    public void like_3_s1__filter_rowList__true()  {
        Or or = new Or(new Like("3", "1"));
        List row = ProviderListJson.LIST.createListDev();

        Assertions.assertThat( or.filter(row)).isTrue();
    }

}
