package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.fluentcodes.tools.xpect.XpectEo;

import java.util.List;

public class ConfigModelChecks {

    public static ModelConfig createThrowException(final Class modelClass) {
        final ModelConfig model = ProviderRootTest
                .EO_CONFIGS
                .findModel(modelClass);
        Assertions.assertThat(model.getModelClass()).isEqualTo(modelClass);
        Assertions
                .assertThatThrownBy(
                        () -> {
                            model.create();
                        })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not create empty instance from model for '" + modelClass.getSimpleName() + "'");
        return model;
    }

    public static Object create(final Class modelClass)  {
        ModelConfig config = ProviderRootTest.EO_CONFIGS.findModel(modelClass);
        Assertions.assertThat(config).isNotNull();
        if (config.isScalar()) {
            return config.create();
        }
        else if (config.isCreate()) {
            return config.create();
        }

        throw new EoException("Method expects a create model type " + modelClass.getSimpleName());
    }

    public static void compare(final Class modelClass) {
        ConfigCall call = new ConfigCall(ModelConfig.class, modelClass.getSimpleName());
        EO eo = ProviderRootTest.createEo();
        List result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo().compareAsString(result);
    }

    public static final Object checkSetGet(final Class modelClass, final String fieldKey, final Object value) {
        Object object = create(modelClass);
        ModelConfig model = ProviderRootTest.EO_CONFIGS.findModel(modelClass);
        model.set(fieldKey, object, value);
        Assertions.assertThat(model.get(fieldKey, object)).isEqualTo(value);
        return object;
    }
}
