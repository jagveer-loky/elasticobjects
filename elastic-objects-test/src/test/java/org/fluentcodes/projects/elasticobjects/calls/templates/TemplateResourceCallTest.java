package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.Call.KEEP_CALL;

/**
 * Created 6.8.2020
 */
public class TemplateResourceCallTest {

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(TemplateResourceCall.class);
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(TemplateResourceCall.class);
    }

    @Test
    public void __setKeepCall__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceCall.class.getSimpleName());
        TemplateResourceCall templateResourceCall = new TemplateResourceCall();

        modelConfig.set(KEEP_CALL, templateResourceCall, KeepCalls.JAVA);
        Assertions.assertThat(templateResourceCall.getKeepCall()).isEqualTo(KeepCalls.JAVA);
        Assertions.assertThat(modelConfig.get(KEEP_CALL, templateResourceCall)).isEqualTo(KeepCalls.JAVA);
    }
}
