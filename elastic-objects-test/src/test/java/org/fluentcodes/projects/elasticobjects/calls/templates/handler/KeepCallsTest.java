package org.fluentcodes.projects.elasticobjects.calls.templates.handler;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.CallImpl;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldFactory;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigNoCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

/**
 * Created 6.8.2020
 */
public class KeepCallsTest implements IModelConfigNoCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return KeepCalls.class;
    }

    @Override
    public void create_throwsEoException() {
        assertCreateThrowingException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void createByModelConfig() {
        ModelConfig modelConfig = ProviderConfigMaps.CONFIG_MAPS
                .findModel(KeepCalls.class);
        Assertions.assertThat(modelConfig.isCreate()).isFalse();
        Assertions.assertThat(modelConfig.isScalar()).isTrue();
        Assertions.assertThat(modelConfig.isEnum()).isTrue();
        Assertions.assertThat(modelConfig.create()).isNull();
    }

    @Test
    public void DEV_fieldBeanMap__find_keepCall__notNull() {
        FieldBean bean = new FieldFactory(ProviderConfigMaps.CONFIG_MAPS).createBeanMap()
                .get("keepCall");
        Assertions.assertThat(bean).isNotNull();
        Assertions.assertThat(bean.getModelKeys()).isEqualTo(KeepCalls.class.getSimpleName());
    }

    @Test
    public void ModelConfig__find_keepCall__notNull() {
        ModelConfig modelConfig = ProviderConfigMaps.CONFIG_MAPS
                .findModel(CallImpl.class);
        Assertions.assertThat(modelConfig).isNotNull();
        FieldConfig fieldConfig = (FieldConfig) modelConfig.getField("keepCall");
        Assertions.assertThat(fieldConfig.isNotNull());
    }
}
