package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 30.05.16.
 */

public class JsonToEoTest {
    private static final Logger LOG = LogManager.getLogger(JsonToEoTest.class);
    @Test
    public void dot__patternMatcher() {
        Assertions.assertThat(JSONToEO.jsonPattern.matcher(".").find()).isFalse();
    }


    @Test
    public void key_valuenewLine____get_valueNewLine()  {
        EO eoWithNewLine = ProviderConfigMaps.createEoDev("{\"key\":\"value\\n\"}");
        Assert.assertEquals("value\n", eoWithNewLine.get("key"));
    }

    @Test
    public void testArray()  {
        String test = "[\"a\"]";
        JSONToEO tokener = new JSONToEO(test, ProviderConfigMaps.CONFIG_MAPS);
        EO adapter = tokener.createChild(ProviderConfigMaps.createEo());
        Assert.assertEquals("a", adapter.get(S0));
    }


    @Test
    public void testNewLineEscapedArray()  {
        String test = "[\"\\n\"]";
        JSONToEO tokener = new JSONToEO(test, ProviderConfigMaps.CONFIG_MAPS);
        EO adapter = tokener.createChild(ProviderConfigMaps.createEo());
        Assert.assertEquals("\n", adapter.get(S0));
    }

    @Test
    public void list2EscapedArray____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("[\"\\\n\"]");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void testCombinationsOfEscapes()  {
        EO adapter = ProviderConfigMaps.createEo("[\"\\t\\r\"]");
        Assert.assertEquals("\t\r", adapter.get(S0));
    }

    @Test
    public void value_NoEndQuote____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("{\"k\":\"v}");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void value_NoStartQuote____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("{\"k\":v\"}");})
        .isInstanceOf(EoException.class);
    }

    @Test
    public void value_NoColon____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("{\"k:\"v\"}");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void key_NoEndQuote____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("{\"k:\"v\"}");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void key_NoStartQuote____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("{k\":\"v\"}");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void givenStringNotQuoted_exception()  {
        Assertions
                .assertThatThrownBy(()->{
                    ProviderConfigMaps.createEo("{\"string\":test}");})
                .isInstanceOf(EoException.class)
                .hasMessage("Could not transform non quoted value 'test'.");
    }

    @Test
    public void list_NoEndQuote____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("[\"v]");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void list_NoStartQuote____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("[v\"]");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void list_NoQuote____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("[test]");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void list_NoClosingBracket____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("[\"v\"");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void list_colon____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("[\"v\":2]");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void list_furtherValues____exception() {
        Assertions.assertThatThrownBy(()->{
            ProviderConfigMaps.createEo("[\"v\"],\"k\"");})
                .isInstanceOf(EoException.class);
    }

    @Test
    public void DEV__NoObjectCharacter__exception() {
        Assertions
                .assertThatThrownBy(()->{
                    ProviderConfigMaps.createEoDev("\"k\",[\"v\":2]");})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Null scalar value not supported for root");
    }

}
