package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderJsonCalls;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 31.08.2020.
 */
public class TemplateCallSinusTest {
    private static final Logger LOG = LogManager.getLogger(TemplateCallSinusTest.class);

    @Test
    public void givenEoWithSimpleSinusCall_whenExecuteEo_thenPlaceHolderIsReplaced()  {
        TemplateCall call = new TemplateCall();
        call.setContent("sin($[testKey]) = $[(SinusValueCall)testKey]");
        EO eo = ProviderRootTest.createEo();
        eo.addCall(call);
        eo.set(2, "testKey");
        eo.execute();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isEqualTo("sin(2) = 0.9092974268256817");
    }


    @Test
    public void givenEoWithContentWithSinusCallPlaceholderJson_whenExecuteEo_thenPlaceHolderIsReplaced()  {
        EO eo = TestProviderJsonCalls.CALL_SINUS_ARRAY_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isEqualTo(
                "sin(1.0) = 0.8414709848078965\n" +
                        "sin(2.0) = 0.9092974268256817\n" +
                        "sin(3.0) = 0.1411200080598672\n");
    }

    @Test
    public void givenEo_whenReplaceString_thenPlaceHolderIsReplaced()  {
        EO eo = ProviderRootTest.createEo();
        eo.set(2, "value");
        String result = new TemplateParser("-$[(SinusValueCall)value/]-").parse(eo);
        Assertions.assertThat(result).isEqualTo("--");
        Assertions.assertThat(eo.get("value")).isEqualTo(0); // was integer before.
    }

    @Test
    public void givenEo_whenReplaceStringInTemplate_thenPlaceHolderIsReplaced()  {
        EO eo = ProviderRootTest.createEo();
        eo.set(2, "value");
        String result = new TemplateParser("-$[(SinusValueCall)value inTemplate=\"true\"/]-").parse(eo);
        Assertions.assertThat(result).isEqualTo("-0.9092974268256817-");
        Assertions.assertThat(eo.get("value")).isEqualTo(2); // was integer before.
    }
}
