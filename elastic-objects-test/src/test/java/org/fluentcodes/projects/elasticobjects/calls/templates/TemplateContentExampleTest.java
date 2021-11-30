package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestProviderJsonCalls;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEo;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateContentExampleTest {
    public static final String CONTENT_EXAMPLE_DATA = "ContentExampleData";
    public static final String STATIC_TPL = "ContentExampleStatic";
    public static final String STATIC_KEEP_TPL = "ContentExampleStaticKeep";
    public static final String STATIC_CONDITION_TPL = "ContentExampleStaticCondition";
    public static final String DYNAMIC_TPL = "ContentExampleDynamic";
    private static final String H1 = "<h1>";
    private static final String H2 = "<h2>";



    @Test
    public void eoList_FileReadCall_ContentExampleData__execute__xpected()  {
        EO eo = ProviderConfigMaps.createEoWithClasses(List.class);
        final FileReadCall call = new FileReadCall(CONTENT_EXAMPLE_DATA);
        call.setTargetPath(".");
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        eo.setSerializationType(JSONSerializationType.STANDARD);
        XpectEo.assertJunit(eo);
    }

    @Test
    public void __eo_ContentExampleDataJson__xpected()  {
        EO eo = ProviderConfigMaps.createEo(TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.content());
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        eo.setSerializationType(JSONSerializationType.STANDARD);
        XpectEo.assertJunit(eo);
    }

    @Test
    public void eo_DataJson__parse_Template__replaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEoTest();
        String value = new Parser("-.{0/header}.-").parse(eo);
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void eo_DataJson__parse_Template_WrongPathRelativePath__notReplaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEoTest();
        String value = new Parser("-.{0/header}.-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-!!No value add for fieldName=0/header!!-");
    }

    @Test
    public void eo_DataJson__parse_template_absolutePath__replaced()  {
        EO eo = TestProviderJson.CONTENT_EXAMPLE_DATA_JSON.getEoTest();
        String value = new Parser("-.{/0/header}.-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void eo_StaticJson__execute_xpected()  {
        EO eo = TestProviderJsonCalls.CONTENT_EXAMPLE_STATIC_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isNotEmpty();
        XpectString.assertJunit((String) eo.get("_template"));
    }

    @Ignore
    @Test
    public void call_StaticTpl__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();

        final TemplateResourceCall call = new TemplateResourceCall(STATIC_TPL);
        final String result = call.execute(eo);
        XpectString.assertJunit(result);
    }

    @Ignore("problem with maven")
    @Test
    public void call_StaticKeepTpl__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();

        final TemplateResourceCall call = new TemplateResourceCall(STATIC_KEEP_TPL);
        final String result = call.execute(eo);
        XpectString.assertJunit(result);
    }

    @Test
    public void call_StaticConditionTpl__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();

        final TemplateResourceCall call = new TemplateResourceCall(STATIC_CONDITION_TPL);
        final String result = call.execute(eo);
        XpectString.assertJunit(result);
    }

    @Test
    public void eo_DynamicJson__execute__xpected()  {
        EO eo = TestProviderJsonCalls.CONTENT_EXAMPLE_DYNAMIC_JSON.getEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String)eo.get("_template")).isNotEmpty();
        XpectString.assertJunit((String) eo.get("_template"));
    }

    @Test
    public void call_DynamicTpl__execute__xpected()  {
        final EO eo = ProviderConfigMaps.createEo();
        final TemplateResourceCall call = new TemplateResourceCall(DYNAMIC_TPL);
        final String result = call.execute(eo);
        XpectString.assertJunit(result);
    }
}
