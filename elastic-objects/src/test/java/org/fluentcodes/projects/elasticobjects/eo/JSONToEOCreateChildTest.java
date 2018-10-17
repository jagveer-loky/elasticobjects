package org.fluentcodes.projects.elasticobjects.eo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.MapProviderJSON;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

/**
 * Created by werner on 30.05.16.
 */

public class JSONToEOCreateChildTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(JSONToEOCreateChildTest.class);

    private static EO createEO(final String json) throws Exception {
        JSONToEO tokener = new JSONToEO(json, TestObjectProvider.EO_CONFIGS_CACHE);
        return tokener.createChild(TestObjectProvider.createEO(), null);
    }

    @Test
    public void test() throws Exception {
        TestHelper.printStartMethod();
        EO eoStringMap = createEO(MapProviderJSON.toJSONMap(S1,S_STRING,S2,S_STRING_OTHER));
        Assert.assertEquals(S_STRING, eoStringMap.get(S1));
    }

    @Test
    public void testNewLineAsPartOfAStringValue() throws Exception {
        TestHelper.printStartMethod();
        EO eoWithNewLine = createEO( "{\"1\":\"a\\n\"}");
        Assert.assertEquals("a\n", eoWithNewLine.get(S1));
    }

    @Test
    public void testArray() throws Exception {
        TestHelper.printStartMethod();
        String test = "[\"a\"]";
        JSONToEO tokener = new JSONToEO(test, TestObjectProvider.EO_CONFIGS_CACHE);
        EO adapter = tokener.createChild(TestObjectProvider.createEO(), null);
        Assert.assertEquals("a", adapter.get(S0));
    }


    @Test
    public void testNewLineEscapedArray() throws Exception {
        TestHelper.printStartMethod();
        String test = "[\"\\n\"]";
        JSONToEO tokener = new JSONToEO(test, TestObjectProvider.EO_CONFIGS_CACHE);
        EO adapter = tokener.createChild(TestObjectProvider.createEO(), null);
        Assert.assertEquals("\n", adapter.get(S0));
    }

    @Test
    public void testNewLine2EscapedArray() {
        try {
            createEO( "[\"\\\n\"]");
            Assert.fail("Illegal escape");
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Illegal escape"));
        }
    }

    @Test
    public void testCombinationsOfEscapes() throws Exception {
        EO adapter = createEO("[\"\\t\\r\"]");
        Assert.assertEquals("\t\r", adapter.get(S0));
    }

    @Test
    public void MapWithValueAndNoEndQuote_fails() {
        try {
            createEO("{\"k\":\"v}");
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
            createEO("{\"k\":v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with bad json v missing start quote!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains(" in the map. Expected"));
        }
    }

    @Test
    public void exceptionMap_NoColon() {
        try {
            createEO("{\"k:\"v\"}");
            TestObjectProvider.createEOBuilder().map("{\"k\",\"v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon after map k!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected ':' in the map after the k"));
        }
    }

    @Test
    public void exceptionMap_Key_NoEndQuote() {
        try {
            createEO("{\"k:\"v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon after map k!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected ':' in the map after the k"));
        }
    }

    @Test
    public void exceptionMap_Key_NoStartQuote() {
        try {
            createEO("{k\":\"v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon after map k!");
        } catch (Exception e) {
            LOG.info(e.getMessage());
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected ':' in the map after the k"));
        }
    }

    @Test
    public void stringNotQuoted_setValue() throws Exception {
            EO adapter = createEO("{\"string\":test}");
            Assert.assertEquals(S_STRING, adapter.get(S_TEST_STRING));
            Assert.assertEquals(S_STRING, ((Map) adapter.get()).get(S_TEST_STRING));
    }

    @Test
    public void exceptionList_NoEndColon() {
        try {
            createEO("[\"v]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Unterminated string cause of an escaped carriage return in a character"));
        }
    }

    @Test
    public void exceptionList_NoStartColon() {
        try {
            createEO("[v\"]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected  ',' or ']' within list value:"));
        }
    }

    @Test
    public void exceptionList_NoColon() {
        try {
            EO adapter = createEO("[test]");
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
            createEO("[\"v\"");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected  ',' or ']' within list value:"));
        }
    }

    @Test
    public void exceptionList_NoSeparatedValues() {
        try {
            createEO("[\"v\":2]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected  ',' or ']' within list value:"));
        }
    }

    @Test
    public void ListWithFurtherValue_fails() {
        try {
            createEO("[\"v\":2],\"k\"");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Expected  ',' or ']' within list value:"));
        }
    }

    @Test
    public void ListWrongStartValues_fails() {
        try {
            createEO("\"k\",[\"v\":2]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Scalar value with no name"));
        }
    }

}
