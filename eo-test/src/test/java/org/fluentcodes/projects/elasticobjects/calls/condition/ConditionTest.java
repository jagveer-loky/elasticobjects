package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.junit.Assert;
import org.junit.Test;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_TEST_STRING;

/**
 * Created by werner.diwischek on 08.01.18.
 */
public class ConditionTest {

    public static final Pattern ifPattern = Pattern.compile("[\\s]*([^\\s]*?)[\\s]+(eq|ne|equals|le|ge|notEquals|like|notLike|in)[\\s]+(.*)");

    static void patternTest(String condition) {

        Matcher matcher = ifPattern.matcher(condition);
        if (matcher.find()) {
            int i = matcher.groupCount();
            Assert.assertEquals(3, i);
            Assert.assertEquals("testString", matcher.group(1));
            Assert.assertEquals(Condition.EQ, matcher.group(2));
            Assert.assertEquals("test", matcher.group(3));
        }
    }

    @Test
    public void simple() {
        ConditionTest.patternTest("testString eq test");
    }

    @Test
    public void simpleLeadingSpace() {
        ConditionTest.patternTest(" testString eq test");
    }

    @Test
    public void simpleMultipleSpacesBeforeOperator() {
        ConditionTest.patternTest("testString   eq test");
    }

    @Test
    public void simpleMultipleSpacesAfterOperator() {
        ConditionTest.patternTest("testString eq     test");
    }


    public void simpleMultipleSpacesAfterValue() {
        ConditionTest.patternTest("key eq value   ");
    }


}
