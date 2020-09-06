package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.models.Expose;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderJsonCalls;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderTemplateContent;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class ConfigKeysCallTest {

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(ConfigKeysCall.class);
    }

    @Test
    public void givenTestProvider_whenSetConfigType_thenIsSet()  {
        ConfigModelChecks.checkSetGet(ConfigKeysCall.class,"configType", ModelConfig.class.getSimpleName());
    }

    @Test
    public void givenTestProvider_whenSetConfigFilter_thenIsSet()  {
        ConfigModelChecks.checkSetGet(ConfigKeysCall.class,"configFilter", ".+");
    }

    @Test
    public void givenCallWithModelConfigAndExposeInfo_whenExecute_thenXpected() {
        ConfigKeysCall call = new ConfigKeysCall(ModelConfig.class)
                .setExpose(Expose.INFO);
        EO eo = ProviderRootTestScope.createEo();
        List<String> result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo<>().compareAsString(result);
    }

    @Test
    public void givenCallWithModelConfigAndExposeWeb_whenExecute_thenXpected() {
        ConfigKeysCall call = new ConfigKeysCall(ModelConfig.class)
                .setExpose(Expose.WEB);
        EO eo = ProviderRootTestScope.createEo();
        List<String> result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo<>().compareAsString(result);
    }

    @Test
    public void givenCallWithFieldConfigAndExposeNone_whenExecute_thenXpected() {
        ConfigKeysCall call = new ConfigKeysCall(FieldConfig.class)
                .setExpose(Expose.NONE);
        EO eo = ProviderRootTestScope.createEo();
        List<String> result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo<>().compareAsString(result);
    }

    @Test
    public void givenCallWithFieldConfigAndExposeInfo_whenExecute_thenXpected() {
        ConfigKeysCall call = new ConfigKeysCall(FieldConfig.class)
                .setExpose(Expose.INFO);
        EO eo = ProviderRootTestScope.createEo();
        List<String> result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo<>().compareAsString(result);
    }

    @Test
    public void givenCallWithFieldConfigAndExposeWeb_whenExecute_thenXpected() {
        ConfigKeysCall call = new ConfigKeysCall(FieldConfig.class)
                .setExpose(Expose.WEB);
        EO eo = ProviderRootTestScope.createEo();
        List<String> result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo<>().compareAsString(result);
    }

    @Test
    public void givenEoWithModelConfig_whenExecute_thenResultIsOrderedList() {
        EO eo = ProviderJsonCalls.CONFIG_KEYS_CALL_MODEL_CONFIG.createMapEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((List) eo.get("keys")).isNotEmpty();
    }


    @Ignore
    @Test
    public void givenEoWithTemplate_whenExecuteEo_thenTemplateResultContainsConfigKeyListCall()  {
        final TemplateCall call = new TemplateCall();
        final String template = "Load model configuration key list: " +
                "$[(ConfigKeysCall)fieldKey\" " +
                "configType=\"ModelConfig\" " +
                "configFilter=\"ConfigKeysCall\" " +
                "inTemplate=\"true\"/]";
        call.setContent(template);

        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();

        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get("_template")).isEqualTo("Load model configuration key list: [ConfigKeysCall]");
    }
}
