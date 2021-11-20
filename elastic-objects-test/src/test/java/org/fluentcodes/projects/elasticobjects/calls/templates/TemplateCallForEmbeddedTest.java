package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJson;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateCallForEmbeddedTest {
    private static EO initEo() {
        return TestProviderJson.FOR_EMBEDDED_TEST.getEoTest();
    }

    @Test
    public void call_sourcePath_level0__execute__replaced()  {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "-->  @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0\"}" +
                        "}|" +
                        " ..path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(initEo());
        Assertions.assertThat(result).isEqualTo("path='value0' --> ..path='value0', path='value1'");
    }

    @Test
    public void call_sourcePath_lstar__execute__replaced()  {
        final TemplateCall call = new TemplateCall(
                        "-->  @{\"(TemplateCall).\":{" +
                                "\"sourcePath\":\"l*\"}" +
                                "}|" +
                                " ..path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEoTest());
        Assertions.assertThat(result).isEqualTo("--> ..path='value0', path='value1'");
    }

    // TODO
    @Ignore
    @Test
    public void call_sourcePath_star__execute__replaced()  {
        final TemplateCall call = new TemplateCall(
                "-->  @{(TemplateCall).\":{" +
                        "\"sourcePath\":\"*\"}" +
                        "}|" +
                        " ..path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEoTest());
        Assertions
                .assertThat(result)
                .isEqualTo("--> ..path='value0', path='!!Could not move to path 'path' because key 'path' does not exist on '/path'.!!'..path='value0', path='value1'");
    }

    @Test
    public void call_sourcePath_level0level1_placeholders__execute__replaced()  {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "-->  @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0/level1\"}" +
                        "}| ../path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEoTest());
        Assertions.assertThat(result).isEqualTo("path='value0' --> ../path='value1', path='value2'");
    }

    @Test
    public void call_sourcePath_level0_level1_placeholders__execute__replaced()  {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "-->  @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0\"}" +
                        "}| " +
                        "-->  @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level1\"}" +
                        "}|" +
                        " ../path='.{../path}.', path='.{path}.'" +
                        ".{}." +
                        ".{}.");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEoTest());
        Assertions.assertThat(result).isEqualTo("path='value0' --> --> ../path='value1', path='value2'");
    }

    @Test
    public void call_sourcePath_level0level1level3_placeholders__execute__replaced()  {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "--> @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0/level1/level2\"}" +
                        "}|" +
                        " ../path='.{../path}.', path='.{path}.'" +
                        ".{}.");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEoTest());
        Assertions.assertThat(result).isEqualTo("path='value0' --> ../path='value2', path='value3'");
    }

    @Test
    public void call_sourcePath_level0_level1_level3_placeholders__execute__replaced()  {
        final TemplateCall call = new TemplateCall(
                "path='.{path}.' " +
                        "--> @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level0\"}" +
                        "}|" +
                        " --> @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level1\"}" +
                        "}|" +
                        " --> @{\"(TemplateCall).\":{" +
                        "\"sourcePath\":\"level2\"}" +
                        "}|" +
                        " ../path='.{../path}.', path='.{path}.'" +
                        ".{}." +
                        ".{}." +
                        ".{}.");
        final String result = call.execute(TestProviderJson.FOR_EMBEDDED_TEST.getEoTest());
        Assertions.assertThat(result).isEqualTo("path='value0' --> --> --> ../path='value2', path='value3'");
    }
}
