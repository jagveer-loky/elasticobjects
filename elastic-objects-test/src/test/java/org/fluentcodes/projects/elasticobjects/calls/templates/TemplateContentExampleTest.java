package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEo;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 22.03.2017.
 */
public class TemplateContentExampleTest {
    public static final String CONTENT_EXAMPLE_DATA = "ContentExampleData";
    public static final String STATIC_KEEP_TPL = "ContentExampleStaticKeep";
    public static final String DYNAMIC_TPL = "ContentExampleDynamic";
    private static final String DATA = "[\n" +
            "    {\n" +
            "        \"header\": \"header1\",\n" +
            "            \"summary\": \"summary1\",\n" +
            "            \"content\": \"content1\",\n" +
            "            \"template\": \"ContentExampleElement1\"\n" +
            "    },\n" +
            "    {\n" +
            "        \"header\": \"header2\",\n" +
            "            \"summary\": \"summary2\",\n" +
            "            \"content\": \"content2\",\n" +
            "            \"template\": \"ContentExampleElement2\"\n" +
            "    }\n" +
            "]";

    @Test
    public void DATA__() {
        EO eo = ProviderConfigMaps.createEo(DATA);
        Assert.assertEquals("header1", eo.get("0/header"));
        TemplateResourceCall call = new TemplateResourceCall("ContentExampleElement1");
        call.setTargetPath(Call.TARGET_AS_STRING);
        String value = call.execute(eo.getEo("/0"));
        Assert.assertEquals("\n" +
                "<h1>header1</h1>\n" +
                "<strong>summary1</strong>\n" +
                "<p>content1</p>", value);
    }

    @Test
    public void eoList_FileReadCall_ContentExampleData__execute__xpected() {
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
    public void __eo_ContentExampleDataJson__xpected() {
        EO eo = ProviderConfigMaps.createEo(DATA);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        eo.setSerializationType(JSONSerializationType.STANDARD);
        XpectEo.assertJunit(eo);
    }

    @Test
    public void eo_DataJson__parse_Template__replaced() {
        EO eo = ProviderConfigMaps.createEo(DATA);
        String value = new Parser("-.{0/header}.-").parse(eo);
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void eo_DataJson__parse_Template_WrongPathRelativePath__notReplaced() {
        EO eo = ProviderConfigMaps.createEo(DATA);
        String value = new Parser("-.{0/header}.-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-!!No value add for fieldName=0/header!!-");
    }

    @Test
    public void eo_DataJson__parse_template_absolutePath__replaced() {
        EO eo = ProviderConfigMaps.createEo(DATA);
        String value = new Parser("-.{/0/header}.-").parse(eo.getEo("1"));
        Assertions.assertThat(value).isEqualTo("-header1-");
    }

    @Test
    public void eo_StaticJson__execute_xpected() {
        EO eo = ProviderConfigMaps.createEo("{\n" +
                "  \"(FileReadCall)data\": {\n" +
                "    \"fileConfigKey\":\"ContentExampleData\"\n" +
                "  },\n" +
                "  \"(TemplateCall)_asString\": {\n" +
                "    \"content\":\"Start of content with a fileConfigKey: \"\n" +
                "  },\n" +
                "  \"(TemplateResourceCall)_asString\": {\n" +
                "    \"sourcePath\": \"data/*\",\n" +
                "    \"fileConfigKey\":\"ContentExampleElement1\"\n" +
                "  }\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get("_template")).isNotEmpty();
        XpectString.assertJunit((String) eo.get("_template"));
    }

    @Ignore
    @Test
    public void call_StaticTpl__execute__xpected() {
        final EO eo = ProviderConfigMaps.createEo();

        final TemplateResourceCall call = new TemplateResourceCall("ContentExampleStatic");
        final String result = call.execute(eo);
        XpectString.assertJunit(result);
    }

    @Ignore("problem with maven")
    @Test
    public void call_StaticKeepTpl__execute__xpected() {
        final EO eo = ProviderConfigMaps.createEo();

        final TemplateResourceCall call = new TemplateResourceCall(STATIC_KEEP_TPL);
        final String result = call.execute(eo);
        XpectString.assertJunit(result);
    }

    @Test
    public void call_StaticConditionTpl__execute__xpected() {
        final EO eo = ProviderConfigMaps.createEo();

        final TemplateResourceCall call = new TemplateResourceCall("ContentExampleStaticCondition");
        final String result = call.execute(eo);
        XpectString.assertJunit(result);
    }

    @Test
    public void eo_DynamicJson__execute__xpected() {
        EO eo = ProviderConfigMaps.createEo("{\n" +
                "  \"(FileReadCall)data\": {\n" +
                "    \"fileConfigKey\":\"ContentExampleData\"\n" +
                "  },\n" +
                "  \"(TemplateCall)_asString\": {\n" +
                "    \"content\":\"Start of content with a fileConfigKey as variable: \"\n" +
                "  },\n" +
                "  \"(TemplateResourceCall)_asString\": {\n" +
                "    \"sourcePath\": \"data/*\",\n" +
                "    \"fileConfigKey\":\".[template].\"\n" +
                "  }\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((String) eo.get("_template")).isNotEmpty();
        XpectString.assertJunit((String) eo.get("_template"));
    }

    @Test
    public void call_DynamicTpl__execute__xpected() {
        final EO eo = ProviderConfigMaps.createEo();
        final TemplateResourceCall call = new TemplateResourceCall(DYNAMIC_TPL);
        final String result = call.execute(eo);
        XpectString.assertJunit(result);
    }
}
