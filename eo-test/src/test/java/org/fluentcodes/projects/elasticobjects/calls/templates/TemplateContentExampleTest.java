package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.json.JsonCallRead;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJsonCalls;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

import org.fluentcodes.tools.xpect.XpectEo;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateContentExampleTest {
    public static final String JSON_CONFIG_KEY = "ContentExampleStatic";
    public static final String DATA_JSON_CONFIG_KEY = "ContentExampleData";
    public static final String STATIC_TEMPLATE_CONFIG_KEY = "ContentExampleStatic";
    public static final String TEMPLATE_STATIC_KEEP_CONFIG_KEY = "ContentExampleStaticKeep";
    public static final String TEMPLATE_STATIC_CONDITION_CONFIG_KEY = "ContentExampleStaticCondition";
    public static final String DYNAMIC_TEMPLATE_CONFIG_KEY = "ContentExampleDynamic";
    public static final String T_CONTENT_OR_EXAMPLE = "ContentOrExample";
    private static final Logger LOG = LogManager.getLogger(TemplateContentExampleTest.class);
    private static final String H1 = "<h1>";
    private static final String H2 = "<h2>";



    @Test
    public void givenCallJsonData_whenExecute_thenExpected()  {
        EO eo = ProviderRootTest.createEo();
        final JsonCallRead call = new JsonCallRead(DATA_JSON_CONFIG_KEY);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        new XpectEo().compareAsString(eo);
    }

    @Test
    public void givenEoData_whenExecuteEo_thenExpected()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA.getEo();
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        Assertions.assertThat(eo.getLog().isEmpty());
        new XpectEo().compareAsString(eo);
    }

    @Test
    public void givenEoData_whenParseTemplate_thenReplaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA.getEo();
        String value = new ParserTemplate("-$[0/header/]-").parse(eo);
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void givenEoData_whenParseTemplateWrongPathRelativePath_thenNotReplaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA.getEo();
        String value = new ParserTemplate("-$[0/header/]-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-!!Path 0/header undefined: Could not find entry for '0'.!!-");
    }

    @Test
    public void givenEoData_whenParseTemplateWrongPathAbsolutePath_thenReplaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA.getEo();
        String value = new ParserTemplate("-$[/0/header/]-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void givenEoJsonStatic_whenExecuteEo_thenExpected()  {
        EO eo = TestProviderJsonCalls.CONTENT_EXAMPLE_STATIC_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isNotEmpty();
        new XpectString().compareAsString((String) eo.get("_template"));
    }

    @Ignore
    @Test
    public void givenCallTemplateResourceStatic_whenExecute_thenXpected()  {
        final EO eo = ProviderRootTest.createEo();

        final TemplateResourceCall call = new TemplateResourceCall()
                .setConfigKey(STATIC_TEMPLATE_CONFIG_KEY);
        final String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void givenCallTemplateResourceStaticKeep_whenExecute_thenXpected()  {
        final EO eo = ProviderRootTest.createEo();

        final TemplateResourceCall call = new TemplateResourceCall()
                .setConfigKey(TEMPLATE_STATIC_KEEP_CONFIG_KEY);
        final String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void givenCallTemplateResourceStaticCondition_whenExecute_thenXpected()  {
        final EO eo = ProviderRootTest.createEo();

        final TemplateResourceCall call = new TemplateResourceCall()
                .setConfigKey(TEMPLATE_STATIC_CONDITION_CONFIG_KEY);
        final String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }

    @Test
    public void givenCallJsonDynamic_whenExecuteEo_thenXpected()  {
        EO eo = TestProviderJsonCalls.CONTENT_EXAMPLE_DYNAMIC_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isNotEmpty();
        new XpectString().compareAsString((String) eo.get("_template"));
    }

    @Test
    public void givenCallTemplateResourceDynamic_whenExecute_thenXpected()  {
        final EO eo = ProviderRootTest.createEo();

        final TemplateResourceCall call = new TemplateResourceCall()
                .setConfigKey(DYNAMIC_TEMPLATE_CONFIG_KEY);
        final String result = call.execute(eo);
        new XpectString().compareAsString(result);
    }
}
