package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDev;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 22.03.2017.
 */
public class TemplateCallSimpleTest {
    private static final Logger LOG = LogManager.getLogger(TemplateCallSimpleTest.class);

    @Test
    public void givenCallWithContentWithStringPlaceholder_whenExecuteCall_thenPlaceHolderIsReplaced()  {
        TemplateCall call = new TemplateCall();
        call.setContent("Just a content with placeHolder testKey=$[testKey]");
        EO eo = ProviderRootDev.createEo();
        eo.set("testValue", "testKey");
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("testValue");
    }

    @Test
    public void givenEoWithContentWithStringPlaceholder_whenExecuteEo_thenPlaceHolderIsReplaced()  {
        TemplateCall call = new TemplateCall();
        call.setContent("Just a content with placeHolder testKey=$[testKey]");
        EO eo = ProviderRootTest.createEo();
        eo.addCall(call);
        eo.set("testValue", "testKey");
        eo.execute();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("testValue");
        Assertions.assertThat((String)eo.get("_template")).contains("testValue");
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
