package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class ConfigKeysCallTest {

    @Test
    public void givenTestProvider_whenFindModel_thenNotNull()  {
        ConfigChecks.findModelAndCreateInstance(ConfigKeysCall.class);
    }

    @Test
    public void givenTestProvider_whenSetConfigType_thenIsSet()  {
        ConfigChecks.findModelAndCheck(ConfigKeysCall.class,"configType", ModelConfig.class.getSimpleName());
    }

    @Test
    public void givenTestProvider_whenSetConfigFilter_thenIsSet()  {
        ConfigChecks.findModelAndCheck(ConfigKeysCall.class,"configFilter", ".+");
    }

    @Test
    public void givenCallWithModelConfig_whenExecute_thenResultIsOrderedList() {
        ConfigKeysCall call = new ConfigKeysCall(ModelConfig.class);
        EO eo = ProviderRootTest.createEo();
        List<String> result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
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

        EO eo = ProviderRootTest.createEo();
        eo.addCall(call);
        eo.execute();

        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.get("_template")).isEqualTo("Load model configuration key list: [ConfigKeysCall]");
    }
}
