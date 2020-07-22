package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootDev;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 30.05.16.
 */

public class JSONToEOElementaryTest {
    private static final Logger LOG = LogManager.getLogger(JSONToEOElementaryTest.class);


    @Test
    public void testNewLineAsPartOfAStringValue()  {
        EO eoWithNewLine = TestProviderRootTest.createEo("{\"1\":\"a\\n\"}");
        Assert.assertEquals("a\n", eoWithNewLine.get(S1));
    }

    @Test
    public void testArray()  {
        String test = "[\"a\"]";
        JSONToEO tokener = new JSONToEO(test, TestProviderRootTest.EO_CONFIGS);
        EO adapter = tokener.createChild(TestProviderRootTest.createEo());
        Assert.assertEquals("a", adapter.get(S0));
    }


    @Test
    public void testNewLineEscapedArray()  {
        String test = "[\"\\n\"]";
        JSONToEO tokener = new JSONToEO(test, TestProviderRootTest.EO_CONFIGS);
        EO adapter = tokener.createChild(TestProviderRootTest.createEo());
        Assert.assertEquals("\n", adapter.get(S0));
    }

    @Test
    public void testNewLine2EscapedArray() {
        try {
            TestProviderRootTest.createEo("[\"\\\n\"]");
            Assert.fail("Illegal escape");
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Illegal escape"));
        }
    }

    @Test
    public void testCombinationsOfEscapes()  {
        EO adapter = TestProviderRootTest.createEo("[\"\\t\\r\"]");
        Assert.assertEquals("\t\r", adapter.get(S0));
    }

    @Test
    public void MapWithValueAndNoEndQuote_fails() {
        try {
            TestProviderRootTest.createEo("{\"k\":\"v}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with bad json v!");
        } catch (Exception e) {
            LOG.info(e.getMessage());
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Unterminated string cause of an escaped carriage return"));
        }
    }

    @Test
    public void exceptionMap_Value_NoStartQuote() {
        try {
            TestProviderRootTest.createEo("{\"k\":v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with bad json v missing start quote!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void exceptionMap_NoColon() {
        try {
            TestProviderRootTest.createEo("{\"k:\"v\"}");
            TestProviderRootTest.createEo("{\"k\",\"v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon after map k!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void exceptionMap_Key_NoEndQuote() {
        try {
            TestProviderRootTest.createEo("{\"k:\"v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon after map k!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected ':' in the map after the k"));
        }
    }

    @Test
    public void exceptionMap_Key_NoStartQuote() {
        try {
            TestProviderRootTest.createEo("{k\":\"v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon after map k!");
        } catch (Exception e) {
            LOG.info(e.getMessage());
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected ':' in the map after the k"));
        }
    }

    @Test
    public void stringNotQuoted_setValue()  {
        EO adapter = TestProviderRootTest.createEo("{\"string\":test}");
        Assert.assertEquals(S_STRING, adapter.get(S_TEST_STRING));
        Assert.assertEquals(S_STRING, ((Map) adapter.get()).get(S_TEST_STRING));
    }

    @Test
    public void exceptionList_NoEndColon() {
        try {
            TestProviderRootTest.createEo("[\"v]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Unterminated string cause of an escaped carriage return in a character"));
        }
    }

    @Test
    public void exceptionList_NoStartColon() {
        try {
            TestProviderRootTest.createEo("[v\"]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void exceptionList_NoColon() {
        try {
            EO adapter = TestProviderRootDev.createEo("[test]");
            Assert.assertEquals(S_STRING, adapter.get(S0));
            Assert.assertEquals(S_STRING, ((List) adapter.get()).get(0));
            //Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected  ',' or ']' within list v:"));
        }
    }

    @Test
    public void exceptionList_NoClosingBracket() {
        try {
            TestProviderRootDev.createEo("[\"v\"");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void exceptionList_NoSeparatedValues() {
        try {
            TestProviderRootDev.createEo("[\"v\":2]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void ListWithFurtherValue_fails() {
        try {
            TestProviderRootDev
                    .createEo("[\"v\"],\"k\"");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with further values after closing list!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void ListWrongStartValues_fails() {
        try {
            TestProviderRootDev.createEo("\"k\",[\"v\":2]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Scalar value with no name"));
        }
    }

}
