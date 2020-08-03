package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.json.JsonCallRead;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderJson;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderJsonCalls;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

import org.fluentcodes.tools.xpect.XpectEo;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_CONTAINS_FAILS;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateContentExampleTest {
    private static final Logger LOG = LogManager.getLogger(TemplateContentExampleTest.class);
    private static final String H1 = "<h1>";
    private static final String H2 = "<h2>";



    @Test
    public void givenDataWithKey_whenExecuteEo_thenExpected()  {
        EO eo = ProviderRootTest.createEo();
        final JsonCallRead call = new JsonCallRead("ContentExample");
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectEo().compareAsString(eo.setSerializationType(JSONSerializationType.STANDARD));
    }

    @Test
    public void givenDataWithJson_whenExecuteEo_thenExpected()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectEo().compareAsString(eo.setSerializationType(JSONSerializationType.STANDARD));
    }

    @Test
    public void givenPlaceHolderNonAbsolutePath_whenReplaced_thenReplaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEo();
        eo.execute();
        String value = new TemplateParser("-$[0/header/]-").parse(eo);
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void givenPlaceHolderNonAbsolutePathWithEoOtherPath_whenReplaced_thenNotReplaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEo();
        eo.execute();
        String value = new TemplateParser("-$[0/header/]-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-!! not found '0/header/' in '/1'!!-");
    }

    @Test
    public void givenPlaceHolderAbsolutePathWithEoOtherPath_whenReplace_thenReplaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEo();
        eo.execute();
        String value = new TemplateParser("-$[/0/header/]-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void givenAsJson_whenExecuteEo_thenExpected()  {
        EO eo = TestProviderJsonCalls.CONTENT_EXAMPLE_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isNotEmpty();
        new XpectString().compareAsString((String) eo.get("_template"));
    }

    @Test
    public void givenDynamicTemplateAsJson_whenExecuteEo_thenExpected()  {
        EO eo = TestProviderJsonCalls.CONTENT_EXAMPLE_DATA_JSON_DYNAMIC_TEMPLATE.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isNotEmpty();
        new XpectString().compareAsString((String) eo.get("_template"));
    }

    @Test
    public void callContentExample()  {
        final TemplateResourceCall action = new TemplateResourceCall().setConfigKey(T_CONTENT_EXAMPLE);
        final String result = action.execute(ProviderRootTest.createEo());
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H2));
        //AssertEO.compare(result);
    }


    @Test
    public void callContentExampleWithStaticTemplate()  {
        final TemplateResourceCall action = new TemplateResourceCall().setConfigKey( T_CONTENT_EXAMPLE_WITH_STATIC_TEMPLATE);
        final EO adapter = ProviderRootTest.createEo();
        final String result = action.execute(adapter);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H1));
        //AssertEO.compare(result);
    }

    @Test
    public void contentExampleWithKeepTpl()  {
        final TemplateResourceCall action = new TemplateResourceCall().setConfigKey( T_CONTENT_EXAMPLE_WITH_KEEP);
        final EO adapter = ProviderRootTest.createEo();
        final String result = action.execute(adapter);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H1));
        //AssertEO.compare(result);
    }

    @Test
    public void callContentExampleWithDynamicTemplateKey()  {
        final TemplateResourceCall action = new TemplateResourceCall().setConfigKey(T_CONTENT_EXAMPLE_WITH_DYNAMIC_TEMPLATE);
        final EO adapter = ProviderRootTest.createEo();
        final String result = action.execute(adapter);
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains(H1));
        //AssertEO.compare(result);
    }

    @Test
    public void callContentOrExample()  {
        
        final TemplateResourceCall action = new TemplateResourceCall().setConfigKey( T_CONTENT_OR_EXAMPLE);
        final String result = action.execute(ProviderRootTest.createEo());
        Assert.assertTrue(INFO_CONTAINS_FAILS + result, result.contains("<h1>header2</h1>"));
        //AssertEO.compare(result);
    }
}
