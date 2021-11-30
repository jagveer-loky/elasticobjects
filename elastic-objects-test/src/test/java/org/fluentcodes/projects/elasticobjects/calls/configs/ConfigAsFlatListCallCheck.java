package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;

import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Ignore;
import org.junit.Test;

/**
 * Created 8.9.2020
 */
public class ConfigAsFlatListCallCheck implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ConfigAsFlatListCall.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
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

    @Ignore
    @Test
    public void call_configType_ModelConfig__execute_xpected() {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall().setConfigType(ModelConfig.class.getSimpleName());
        String result = call.execute(eo);
        XpectString.assertJunit(result);
    }

    @Test
    public void call_configType_FieldConfig__execute_xpected() {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigAsFlatListCall call = new ConfigAsFlatListCall()
                .setConfigType(FieldConfig.class.getSimpleName())
                .setFieldKeys("naturalId", "modelKeys", "description", "module", "subModule", "fieldKey", "scope",
                        "dbFieldParams", "eoFieldParams", "viewFieldParams");
        String result = call.execute(eo);
        XpectString.assertJunit(result);
    }
}
