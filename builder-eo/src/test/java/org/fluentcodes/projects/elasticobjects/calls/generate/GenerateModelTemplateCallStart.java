package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.LogLevel;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigAsFlatListCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigKeysCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigOpenApiCall;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigTypesCall;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryListReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryMapReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.DirectoryWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileReadWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.files.JsonWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateDirResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreCall;
import org.fluentcodes.projects.elasticobjects.calls.values.SinusValueCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringLowerCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringLowerFirstCharCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringPluralCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringReplaceCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringReplaceWhiteSpaceCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringReplaceWithHtmlCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringUpperCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringUpperFirstCharCall;
import org.fluentcodes.projects.elasticobjects.calls.values.ValueCall;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateModelTemplateCallStart {

    public static void createAll(final String naturalId) {
        GenerateModelTemplateMapCall call = new GenerateModelTemplateMapCall();
        call.setTargetFileConfigKey("classes.java");
        call.setProjectDirectory("..");
        call.setPackagePath("org.fluentcodes.projects.elasticobjects.");
        call.setFileEnding("");
        call.setNaturalId(naturalId);
        EO eo = ProviderRootTestScope.createEo();
        eo.setLogLevel(LogLevel.INFO);
        String result = call.execute(eo);
        Assertions.assertThat(eo.hasErrors()).isFalse();
        Assertions.assertThat(result).contains("src/main");
        System.out.println("Executed within " + call.getDuration() + " ms: " + result);
        System.out.println(eo.getLog());

    }

    @Test
    public void call_AnObject__execute__logEmpty() {
        createAll(AnObject.class.getSimpleName());
    }

    @Test
    public void call_ASubObject__execute__logEmpty() {
        createAll(ASubObject.class.getSimpleName());
    }

    @Test
    public void call_DirectoryMapReadCall__execute__logEmpty() {
        createAll(DirectoryMapReadCall.class.getSimpleName());
    }
    @Test
    public void call__execute__logEmpty() {
        createAll(Config.class.getSimpleName());
    }
}
