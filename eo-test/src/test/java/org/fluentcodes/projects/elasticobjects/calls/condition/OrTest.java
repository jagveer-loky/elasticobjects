package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
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
    public void oneCondition() {
        Or or = new Or(toEq(S_TEST_STRING, S_STRING));
        Assert.assertEquals(S_TEST_STRING, or.getAnd(0).getCondition(0).getKey());
        Assert.assertEquals(S_STRING, or.getAnd(0).getCondition(0).getValue());
        Assert.assertEquals(S_TEST_STRING + "=:" + S_TEST_STRING + "_0 ", or.getAnd(0).getCondition(0).createQuery(new HashMap<>()));
        Assert.assertEquals("(" + S_TEST_STRING + "=:" + S_TEST_STRING + "_0 )", or.createQuery());
    }

    @Test
    public void twoConditions() {
        final String orAsString = toOr(
                toEq(S_KEY0, S_STRING),
                toEq(S_KEY1, S_STRING_OTHER));
        Or or = new Or(orAsString);
        Assert.assertEquals(S_KEY1, or.getAnd(1).getCondition(0).getKey());
        Assert.assertEquals(S_STRING_OTHER, or.getAnd(1).getCondition(0).getValue());
        Assert.assertEquals("key1=:key1_0 ", or.getAnd(1).getCondition(0).createQuery(new HashMap<>()));
        Assert.assertEquals("(key0=:key0_0 ) or (key1=:key1_1 )", or.createQuery());
    }

    @Test
    public void filterAdapter()  {
        EO adapter = ProviderRootTestScope.createEo();
        adapter.set(S_STRING, S_TEST_STRING);
        Or condition = new Or(toLike(S_TEST_STRING, S_STRING));
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + adapter.get(S_TEST_STRING),
                condition.filter(adapter));
        condition = new Or(toLike(S_TEST_STRING, S_INTEGER.toString()));
        Assert.assertFalse(INFO_CONDITION_TRUE_FAILS + condition.toString() + adapter.get(S_TEST_STRING),
                condition.filter(adapter));
    }

    @Test
    public void filterRow() {
        List row = ProviderListJson.JSON_FILTER.createListDev();
        Or condition = new Or(toLike(S0, S_STRING));
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + row.get(0),
                condition.filter(row));
        condition = new Or(toLike(S2, S_KEY2));
        Assert.assertFalse(INFO_CONDITION_TRUE_FAILS + condition.toString() + row.get(2),
                condition.filter(row));
        condition = new Or(toLike(S3, S1));
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + row.get(3),
                condition.filter(row));
    }

}
