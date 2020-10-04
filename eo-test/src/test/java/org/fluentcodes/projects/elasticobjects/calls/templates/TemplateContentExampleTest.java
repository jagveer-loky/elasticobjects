package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.files.JsonReadCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJsonCalls;
import org.fluentcodes.tools.xpect.XpectEo;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateContentExampleTest {
    public static final String CONFIG_KEY_JSON = "ContentExampleData";
    public static final String STATIC_TPL = "ContentExampleStatic";
    public static final String STATIC_KEEP_TPL = "ContentExampleStaticKeep";
    public static final String STATIC_CONDITION_TPL = "ContentExampleStaticCondition";
    public static final String DYNAMIC_TPL = "ContentExampleDynamic";
    private static final Logger LOG = LogManager.getLogger(TemplateContentExampleTest.class);
    private static final String H1 = "<h1>";
    private static final String H2 = "<h2>";



    @Test
    public void call_ConfigKeyJson__execute__xpected()  {
        EO eo = ProviderRootTestScope.createEo();
        final JsonReadCall call = new JsonReadCall(CONFIG_KEY_JSON);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        new XpectEo().compareAsString(eo);
    }

    @Test
    public void eo_DataJson__execute__xpected()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEoTest();
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        Assertions.assertThat(eo.getLog().isEmpty());
        new XpectEo().compareAsString(eo);
    }

    @Test
    public void eo_DataJson__parse_Template__replaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEoTest();
        String value = new ParserTemplate("-$[0/header/]-").parse(eo);
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void eo_DataJson__parse_Template_WrongPathRelativePath__notReplaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEoTest();
        String value = new ParserTemplate("-$[0/header/]-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-!!Could not move to path '0/header' because key '0' does not exist on '/1'.!!-");
    }

    @Test
    public void eo_DataJson__parse_template_absolutePath__replaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEoTest();
        String value = new ParserTemplate("-$[/0/header/]-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void eo_StaticJson__execute_xpected()  {
        EO eo = TestProviderJsonCalls.CONTENT_EXAMPLE_STATIC_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isNotEmpty();
        new XpectString().compareAsString((String) eo.get("_template"));
    }

    @Ignore
    @Test
    public void call_StaticTpl__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();

        final TemplateResourceCall call = new TemplateResourceCall();
        call.setTemplateKey(STATIC_TPL);
        final String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void call_StaticKeepTpl__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();

        final TemplateResourceCall call = new TemplateResourceCall();
        call.setConfigKey(STATIC_KEEP_TPL);
        final String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void call_StaticConditionTpl__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();

        final TemplateResourceCall call = new TemplateResourceCall();
        call.setTemplateKey(STATIC_CONDITION_TPL);
        final String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void eo_DynamicJson__execute__xpected()  {
        EO eo = TestProviderJsonCalls.CONTENT_EXAMPLE_DYNAMIC_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isNotEmpty();
        new XpectString().compareAsString((String) eo.get("_template"));
    }

    @Test
    public void call_DynamicTpl__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final TemplateResourceCall call = new TemplateResourceCall();
        call.setTemplateKey(DYNAMIC_TPL);
        final String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }
}
