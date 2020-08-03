package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderJson;


import org.junit.Test;

/**
 * Created by Werner on 22.03.2017.
 */
public class EmbeddedTest {
    private static final Logger LOG = LogManager.getLogger(EmbeddedTest.class);

    @Test
    public void givenLevel0_ok()  {
        final TemplateCall call = new TemplateCall(
                "path='$[path]' " +
                        "--> $[&level0]..path='$[../path]', path='$[path]'" +
                        "$[/]");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEo());
        Assertions.assertThat(result).isEqualTo("path='value0' --> ..path='value0', path='value1'");
    }

    @Test
    public void givenLStar_ok()  {
        final TemplateCall call = new TemplateCall(
                        "--> $[&l*]..path='$[../path]', path='$[path]'" +
                        "$[/]");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEo());
        Assertions.assertThat(result).isEqualTo("--> ..path='value0', path='value1'");
    }
    @Test
    public void givenStar_thenProblemGettingPathValueFromStringEo()  {
        final TemplateCall call = new TemplateCall(
                "--> $[&*]..path='$[../path]', path='$[path]'" +
                        "$[/]");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEo());
        Assertions.assertThat(result).isEqualTo("--> ..path='value0', path='!! not found 'path' in '/path'!!'..path='value0', path='value1'");
    }

    @Test
    public void givenLevel0Level1_ok()  {
        final TemplateCall call = new TemplateCall(
                "path='$[path]' " +
                        "--> $[&level0/level1]../path='$[../path]', path='$[path]'" +
                        "$[/]");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEo());
        Assertions.assertThat(result).isEqualTo("path='value0' --> ../path='value1', path='value2'");
    }

    @Test
    public void givenLevel1InLevel0_ok()  {
        final TemplateCall call = new TemplateCall(
                "path='$[path]' " +
                        "--> $[&level0] " +
                        "--> $[&level1]../path='$[../path]', path='$[path]'" +
                        "$[/]" +
                        "$[/]");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEo());
        Assertions.assertThat(result).isEqualTo("path='value0' -->  --> ../path='value1', path='value2'");
    }

    @Test
    public void givenLevel0Level1Level2_ok()  {
        final TemplateCall call = new TemplateCall(
                "path='$[path]' " +
                        "--> $[&level0/level1/level2]../path='$[../path]', path='$[path]'" +
                        "$[/]");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEo());
        Assertions.assertThat(result).isEqualTo("path='value0' --> ../path='value2', path='value3'");
    }

    @Test
    public void givenLevel2InLevel1InLevel0_ok()  {
        final TemplateCall call = new TemplateCall(
                "path='$[path]' " +
                        "--> $[&level0] " +
                        "--> $[&level1] " +
                        "--> $[&level2]../path='$[../path]', path='$[path]'" +
                        "$[/]" +
                        "$[/]" +
                        "$[/]");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEo());
        Assertions.assertThat(result).isEqualTo("path='value0' -->  -->  --> ../path='value2', path='value3'");
    }
}
