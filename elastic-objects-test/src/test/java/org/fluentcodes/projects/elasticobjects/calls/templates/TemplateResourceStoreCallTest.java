package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.CallKeep.KEEP_CALL;

/**
 * Created 21.9.2020
 */
public class TemplateResourceStoreCallTest {

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(TemplateResourceStoreCall.class);
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(TemplateResourceStoreCall.class);
    }

    @Test
    public void __setTargetFile__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceStoreCall.class.getSimpleName());
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();

        modelConfig.set(TemplateResourceStoreCall.TARGET_FILE, call, "test");
        Assertions.assertThat(call.getTargetFile()).isEqualTo("test");
        Assertions.assertThat(modelConfig.get(TemplateResourceStoreCall.TARGET_FILE, call)).isEqualTo("test");
    }

    @Test
    public void __setKeepCall__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceStoreCall.class.getSimpleName());
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();

        modelConfig.set(KEEP_CALL, call, "JAVA");
        Assertions.assertThat(call.getKeepCall()).isEqualTo(KeepCalls.JAVA);
        Assertions.assertThat(modelConfig.get(KEEP_CALL, call)).isEqualTo(KeepCalls.JAVA);
    }
}
