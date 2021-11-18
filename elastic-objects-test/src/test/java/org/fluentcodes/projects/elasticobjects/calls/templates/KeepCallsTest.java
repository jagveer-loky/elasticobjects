package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldFactory;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * Created 6.8.2020
 */
public class KeepCallsTest {

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(KeepCalls.class);
    }

    @Test
    public void createByModelConfig()  {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS
                .findModel(KeepCalls.class);
        Assertions.assertThat(modelConfig.isCreate()).isFalse();
        Assertions.assertThat(modelConfig.isScalar()).isTrue();
        Assertions.assertThat(modelConfig.isEnum()).isTrue();
        Assertions.assertThat(modelConfig.create()).isNull();
    }


    @Test
    public void DEV_fieldBeanMap__find_keepCall__notNull() {
        FieldBean bean = new FieldFactory(ProviderRootTestScope.EO_CONFIGS).createBeanMap()
                .get("keepCall");
        Assertions.assertThat(bean).isNotNull();
        Assertions.assertThat(bean.getModelKeys()).isEqualTo(KeepCalls.class.getSimpleName());
    }

    @Test
    public void TEST_ModelConfig__find_keepCall__notNull() {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS
                .findModel(CallImpl.class);
        Assertions.assertThat(modelConfig).isNotNull();
        FieldConfig fieldConfig = (FieldConfig) modelConfig.getField("keepCall");
        Assertions.assertThat(fieldConfig.isNotNull());
    }
}
