package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigCall;
import org.fluentcodes.projects.elasticobjects.calls.json.JsonConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Test;

import java.util.List;
import java.util.Set;

public class ConfigChecks {

    public static ModelConfig findModelAndCreateInstanceExceptionThrown(final Class<? extends Config> configClass) {
        final ModelConfig model = ProviderRootTest
                .EO_CONFIGS
                .findModel(configClass);
        Assertions.assertThat(model.getModelClass()).isEqualTo(configClass);
        Assertions
                .assertThatThrownBy(
                        () -> {
                            model.create();
                        })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not create empty instance from model for '" + configClass.getSimpleName() + "'");
        return model;
    }

    public static void resolveConfigs(final Class<? extends Config> configClass)  {
        Set<String> keys = ProviderRootTest.EO_CONFIGS.getConfigKeys(configClass);
        Assertions.assertThat(keys).isNotEmpty();
        for (String key: keys) {
            Assertions.assertThat(key).isNotEmpty();
            Config config = (Config) ProviderRootTest.EO_CONFIGS.find(configClass, key);
            Assertions.assertThat(config).isNotNull();
            config.resolve();
        }
    }

    public static Object findModelAndCreateInstance(final Class configClass)  {
        ModelConfig config = ProviderRootTest.EO_CONFIGS.findModel(configClass);
        Assertions.assertThat(config).isNotNull();
        if (config.isCreate()) {
            Object instance = config.create();
            return instance;
        }
        throw new EoException("Method expects a create model type");
    }

    public static void compareConfigModel(final Class configClass) {
        ConfigCall call = new ConfigCall(ModelConfig.class, configClass.getSimpleName());
        EO eo = ProviderRootTest.createEo();
        List result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo().compareAsString(result);
    }

    public static void compareConfigurations(final Class configClass) {
        ConfigCall call = new ConfigCall(configClass,".*");
        EO eo = ProviderRootTest.createEo();
        List result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo().compareAsString(result);
    }

    public static final Object checkField(final Class modelClass, final String fieldKey, final Object value) {
        Object object = ConfigChecks.findModelAndCreateInstance(modelClass);
        ModelConfig model = ProviderRootTest.EO_CONFIGS.findModel(modelClass);
        model.set(fieldKey, object, value);
        Assertions.assertThat(model.get(fieldKey, object)).isEqualTo(value);
        return object;
    }

    public static void resolveConfigEntries(Class<? extends Config> configClass)  {
        Set<String> keys = ProviderRootTest.EO_CONFIGS.getConfigKeys(configClass);
        Assertions.assertThat(keys).isNotEmpty();
        for (String key: keys) {
            Assertions.assertThat(key).isNotEmpty();
            Config config = (Config) ProviderRootTest.EO_CONFIGS.find(configClass, key);
            Assertions.assertThat(config).isNotNull();
            config.resolve();
        }
    }
}
