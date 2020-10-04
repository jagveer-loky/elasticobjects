package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.LinkedHashMap;

import static org.fluentcodes.projects.elasticobjects.models.ConfigImpl.PROPERTIES;

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

        modelConfig.set(PropertiesTemplateResourceStoreAccessor.TARGET_FILE, call, "test");
        Assertions.assertThat(call.getTargetFile()).isEqualTo("test");
        Assertions.assertThat(modelConfig.get(PropertiesTemplateResourceStoreAccessor.TARGET_FILE, call)).isEqualTo("test");
    }

    @Test
    public void __setStartDirective__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceStoreCall.class.getSimpleName());
        
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();

        modelConfig.set(PropertiesTemplateResourceAccessor.START_DIRECTIVE, call, "test");
        Assertions.assertThat(call.getStartDirective()).isEqualTo("test");
        Assertions.assertThat(modelConfig.get(PropertiesTemplateResourceAccessor.START_DIRECTIVE, call)).isEqualTo("test");
    }

    @Test
    public void __setEndDirective__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceStoreCall.class.getSimpleName());
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();

        modelConfig.set(PropertiesTemplateResourceAccessor.END_DIRECTIVE, call, "test");
        Assertions.assertThat(call.getEndDirective()).isEqualTo("test");
        Assertions.assertThat(modelConfig.get(PropertiesTemplateResourceAccessor.END_DIRECTIVE, call)).isEqualTo("test");
    }

    @Test
    public void __setTemplateKey__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceStoreCall.class.getSimpleName());
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();

        modelConfig.set(PropertiesTemplateResourceAccessor.TEMPLATE_KEY, call, "test");
        Assertions.assertThat(call.getTemplateKey()).isEqualTo("test");
        Assertions.assertThat(modelConfig.get(PropertiesTemplateResourceAccessor.TEMPLATE_KEY, call)).isEqualTo("test");
    }

    @Test
    public void __setKeepCall__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceStoreCall.class.getSimpleName());
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();

        modelConfig.set(PropertiesTemplateResourceAccessor.KEEP_CALL, call, "JAVA");
        Assertions.assertThat(call.getKeepCall()).isEqualTo(KeepCalls.JAVA);
        Assertions.assertThat(modelConfig.get(PropertiesTemplateResourceAccessor.KEEP_CALL, call)).isEqualTo(KeepCalls.JAVA);
    }

    @Test
    public void __setProperties__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceStoreCall.class.getSimpleName());
        TemplateResourceStoreCall call = new TemplateResourceStoreCall();

        modelConfig.set(PROPERTIES, call, new LinkedHashMap<>());
        Assertions.assertThat(call.getProperties()).isNotNull();
        Assertions.assertThat(modelConfig.get(PROPERTIES,call)).isNotNull();
    }
}
