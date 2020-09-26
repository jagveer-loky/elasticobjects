package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

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
    public void __setDirective__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceCall.class.getSimpleName());
        Assertions.assertThat(modelConfig).isNotNull();
        TemplateResourceCall templateResourceCall = new TemplateResourceCall();

        modelConfig.set(PropertiesTemplateResourceAccessor.DIRECTIVE, templateResourceCall, "test");
        Assertions.assertThat(templateResourceCall.getDirective()).isEqualTo("test");
        Assertions.assertThat(modelConfig.get(PropertiesTemplateResourceAccessor.DIRECTIVE, templateResourceCall)).isEqualTo("test");
    }

    @Test
    public void __setEndDirective__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceCall.class.getSimpleName());
        TemplateResourceCall templateResourceCall = new TemplateResourceCall();

        modelConfig.set(PropertiesTemplateResourceAccessor.END_DIRECTIVE, templateResourceCall, "test");
        Assertions.assertThat(templateResourceCall.getEndDirective()).isEqualTo("test");
        Assertions.assertThat(modelConfig.get(PropertiesTemplateResourceAccessor.END_DIRECTIVE, templateResourceCall)).isEqualTo("test");
    }

    @Test
    public void __setTemplateKey__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceCall.class.getSimpleName());
        TemplateResourceCall templateResourceCall = new TemplateResourceCall();

        modelConfig.set(PropertiesTemplateResourceAccessor.TEMPLATE_KEY, templateResourceCall, "test");
        Assertions.assertThat(templateResourceCall.getTemplateKey()).isEqualTo("test");
        Assertions.assertThat(modelConfig.get(PropertiesTemplateResourceAccessor.TEMPLATE_KEY, templateResourceCall)).isEqualTo("test");
    }

    @Test
    public void __setKeepCall__accessOk() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(TemplateResourceCall.class.getSimpleName());
        TemplateResourceCall templateResourceCall = new TemplateResourceCall();

        modelConfig.set(PropertiesTemplateResourceAccessor.KEEP_CALL, templateResourceCall, "JAVA");
        Assertions.assertThat(templateResourceCall.getKeepCall()).isEqualTo(KeepCalls.JAVA);
        Assertions.assertThat(modelConfig.get(PropertiesTemplateResourceAccessor.KEEP_CALL, templateResourceCall)).isEqualTo(KeepCalls.JAVA);
    }
}
