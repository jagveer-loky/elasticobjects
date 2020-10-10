package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 30.05.16.
 */

public class JSONToEOTest {
    private static final Logger LOG = LogManager.getLogger(JSONToEOTest.class);

    @Test
    public void dot__patternMatcher() {
        Assertions.assertThat(JSONToEO.jsonPattern.matcher(".").find()).isFalse();
    }


    @Test
    public void testNewLineAsPartOfAStringValue()  {
        EO eoWithNewLine = ProviderRootTestScope.createEo("{\"1\":\"a\\n\"}");
        Assert.assertEquals("a\n", eoWithNewLine.get(S1));
    }

    @Test
    public void testArray()  {
        String test = "[\"a\"]";
        JSONToEO tokener = new JSONToEO(test, ProviderRootTestScope.EO_CONFIGS);
        EO adapter = tokener.createChild(ProviderRootTestScope.createEo());
        Assert.assertEquals("a", adapter.get(S0));
    }


    @Test
    public void testNewLineEscapedArray()  {
        String test = "[\"\\n\"]";
        JSONToEO tokener = new JSONToEO(test, ProviderRootTestScope.EO_CONFIGS);
        EO adapter = tokener.createChild(ProviderRootTestScope.createEo());
        Assert.assertEquals("\n", adapter.get(S0));
    }

    @Test
    public void testNewLine2EscapedArray() {
        try {
            ProviderRootTestScope.createEo("[\"\\\n\"]");
            Assert.fail("Illegal escape");
        } catch (Exception e) {
            Assert.assertTrue(e.getMessage().contains("Illegal escape"));
        }
    }

    @Test
    public void testCombinationsOfEscapes()  {
        EO adapter = ProviderRootTestScope.createEo("[\"\\t\\r\"]");
        Assert.assertEquals("\t\r", adapter.get(S0));
    }

    @Test
    public void MapWithValueAndNoEndQuote_fails() {
        try {
            ProviderRootTestScope.createEo("{\"k\":\"v}");
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
            ProviderRootTestScope.createEo("{\"k\":v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with bad json v missing start quote!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void exceptionMap_NoColon() {
        try {
            ProviderRootTestScope.createEo("{\"k:\"v\"}");
            ProviderRootTestScope.createEo("{\"k\",\"v\"}");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon after map k!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void givenKeyNoEndQuote_thenExceptionThrown() {
        Assertions
                .assertThatThrownBy(()->{
                    ProviderRootTestScope.createEo("{\"k:\"v\"}");})
                .isInstanceOf(EoException.class)
                .hasMessage("Unterminated string cause of an escaped carriage return in a character: 'k:'.");
    }

    @Test
    public void givenNoStartQuote_thenExceptionThrown() {
        Assertions
                .assertThatThrownBy(()->{
                    ProviderRootTestScope.createEo("{k\":\"v\"}");})
                .isInstanceOf(EoException.class)
                .hasMessage("Expected colon not found but ':': mapObject: 4: {k\":====v\"}");
    }

    @Test
    public void givenStringNotQuoted_throwException()  {
        Assertions
                .assertThatThrownBy(()->{
                    ProviderRootTestScope.createEo("{\"string\":test}");})
                .isInstanceOf(EoException.class)
                .hasMessage("Could not transform non quoted value 'test'.");
    }

    @Test
    public void exceptionList_NoEndColon() {
        try {
            ProviderRootTestScope.createEo("[\"v]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            Assert.assertTrue("Unexpected Exception message: " + e.getMessage(),
                    e.getMessage().contains("Unterminated string cause of an escaped carriage return in a character"));
        }
    }

    @Test
    public void exceptionList_NoStartColon() {
        try {
            ProviderRootTestScope.createEo("[v\"]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void givenListWithStringWithNoColon_ThenExceptionThrown() {
        Assertions
                .assertThatThrownBy(()->{
                    ProviderRootDevScope.createEo("[test]");})
                .isInstanceOf(EoException.class)
                .hasMessage("Could not transform non quoted value 'test'.");
    }

    @Test
    public void exceptionList_NoClosingBracket() {
        try {
            ProviderRootDevScope.createEo("[\"v\"");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void exceptionList_NoSeparatedValues() {
        try {
            ProviderRootDevScope.createEo("[\"v\":2]");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with missing colon within list!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void ListWithFurtherValue_fails() {
        try {
            ProviderRootDevScope
                    .createEo("[\"v\"],\"k\"");
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION + "with further values after closing list!");
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void givenNoObjectCharacter_thenMappedAsString() {
        EO eo = ProviderRootDevScope.createEo("\"k\",[\"v\":2]");
        Assertions
                .assertThat(eo.get())
                .isEqualTo("\"k\",[\"v\":2]");
    }

}
