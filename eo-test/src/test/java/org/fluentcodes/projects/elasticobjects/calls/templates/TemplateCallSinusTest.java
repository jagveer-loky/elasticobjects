package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderCallTemplate;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootDev;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootTest;
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
        EO eo = TestProviderRootTest.createEo();
        eo.addCall(call);
        eo.set(2, "testKey");
        eo.execute();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isEqualTo("sin(2) = 0.9092974268256817");
    }


    @Test
    public void givenEoWithContentWithSinusCallPlaceholderJson_whenExecuteEo_thenPlaceHolderIsReplaced()  {
        EO eo = TestProviderCallTemplate.CALL_SINUS_ARRAY_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isEqualTo(
                "sin(1.0) = 0.8414709848078965\n" +
                        "sin(2.0) = 0.9092974268256817\n" +
                        "sin(3.0) = 0.1411200080598672\n");
    }
/*
    @Test
    public void executeWithPath()  {
        final TemplateCall action = new TemplateCall(T_SIMPLE_INSERT_WITH_PATH);
        final String result = action.execute(MapProviderEO.createSimpleInsertWithPath());
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(S_STRING));
        //AssertEO.compare(result);
    }

    @Test
    public void executeWithPathAndEmbeddedJson()  {
        final String value = TestTemplateProvider.executeTemplateCall(T_SIMPLE_INSERT_WITH_PATH_AND_EMBEDDED_JSON);
        Assert.assertEquals("\nTest testValue Insert: testValue2 testValue2", value);
    }

    @Test
    public void executeWithPathAndJson()  {
        final String result = TestTemplateProvider.executeTemplateCall(T_SIMPLE_INSERT_WITH_PATH_AND_JSON);
        //AssertEO.compare(result);
    }

    @Test
    public void executeWithPathAndJsonAndStore()  {
        final String result = TestTemplateProvider.executeTemplateCall(T_SIMPLE_INSERT_WITH_PATH_AND_JSON_STORE);
        Assert.assertTrue(result.contains(S_STRING_OTHER));
        //AssertEO.compare(result);
    }

 */

}
