package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class AndTest {
    static final Pattern ifPattern = Pattern.compile("[\\s]*([^\\s]*?)[\\s]+(eq|ne|equals|le|ge|notEquals|like|notLike|in)[\\s]+(.*)");

    //static final Pattern ifPattern = Pattern.compile("(key).*");
    @Test
    public void and() {
        And and = new And(toEq(S_TEST_STRING, S_STRING));
        Assert.assertEquals(S_TEST_STRING, and.getCondition(0).getKey());
        Assert.assertEquals(S_STRING, and.getCondition(0).getValue());
        Assert.assertEquals(S_TEST_STRING + "=:" + S_TEST_STRING + "_0 ", and.getCondition(0).createQuery(new HashMap<>()));
    }

    @Test
    public void and2() {
        And and = new And(toAnd(toEq(S_KEY0, S_STRING), toEq(S_KEY1, S_STRING_OTHER)));
        Assert.assertEquals(S_KEY1, and.getCondition(1).getKey());
        Assert.assertEquals(S_STRING_OTHER, and.getCondition(1).getValue());
        Assert.assertEquals(S_KEY1 + "=:" + S_KEY1 + "_0 ", and.getCondition(1).createQuery(new HashMap<>()));
    }

    @Test
    public void simple() {
        pattern(S_TEST_STRING + " eq " + S_STRING);
    }

    @Test
    public void simpleLeadingSpace() {
        pattern(" " + S_TEST_STRING + " eq " + S_STRING);
    }

    @Test
    public void simpleMultipleSpacesBeforeOperator() {
        pattern(S_TEST_STRING + "   eq " + S_STRING);
    }

    @Test
    public void simpleMultipleSpacesAfterOperator() {
        pattern(S_TEST_STRING + " eq     " + S_STRING);
    }


    public void simpleMultipleSpacesAfterValue() {
        pattern("key eq value   ");
    }

    private void pattern(String condition) {

        Matcher matcher = ifPattern.matcher(condition);
        if (matcher.find()) {
            int i = matcher.groupCount();
            Assert.assertEquals(3, i);
            Assert.assertEquals(S_TEST_STRING, matcher.group(1));
            Assert.assertEquals(Condition.EQ, matcher.group(2));
            Assert.assertEquals(S_STRING, matcher.group(3));
        }
    }

    @Test
    public void filterAdapter()  {
        EO adapter = TestProviderRootTest.createEo();
        adapter.set(S_STRING, S_TEST_STRING);
        And condition = new And(toLike(S_TEST_STRING, S_STRING));
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString(),
                condition.filter(adapter));
        condition = new And(toLike(S_KEY, S_STRING_OTHER));
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + condition.toString(),
                condition.filter(adapter));
    }

    @Test
    public void filterRow()  {
        List list = List.of(S_STRING, S_STRING_OTHER, null, S_KEY0, S_INTEGER);
        And condition = new And(toLike(S0, S_STRING));
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + list.get(0),
                condition.filter(list));
        condition = new And(toLike(S3, S_STRING));
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + condition.toString() + list.get(3),
                condition.filter(list));
        condition = new And(toLike(S4, S_INTEGER.toString()));
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + list.get(4),
                condition.filter(list));
    }
}
