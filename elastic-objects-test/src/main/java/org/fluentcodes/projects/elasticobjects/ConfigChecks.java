package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigCall;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;

import java.util.List;
import java.util.Set;

public class ConfigChecks {

    public static void resolveConfigs(final Class<? extends ConfigConfigInterface> configClass)  {
        Set<String> keys = ProviderRootTestScope.EO_CONFIGS.getConfigKeys(configClass);
        Assertions.assertThat(keys).isNotEmpty();
        for (String key: keys) {
            Assertions.assertThat(key).isNotEmpty();
            ConfigConfigInterface config = (ConfigConfigInterface) ProviderRootTestScope.EO_CONFIGS.find(configClass, key);
            Assertions.assertThat(config).isNotNull();
        }
    }

    public static void compareConfigurations(final Class<? extends ConfigConfigInterface> configClass) {
        compareConfiguration(configClass, ".*");
    }

    public static void compareConfiguration(final Class<? extends ConfigConfigInterface> configClass, final String configKey) {
        ConfigCall call = new ConfigCall(configClass, configKey);
        EO eo = ProviderRootTestScope.createEo();
        List result = (List)call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.EO)
                .build()
                .compareAsString(result);
    }

    public static void resolveConfigurations(Class<? extends ConfigConfigInterface> configClass)  {
        Set<String> keys = ProviderRootTestScope.EO_CONFIGS.getConfigKeys(configClass);
        Assertions.assertThat(keys).isNotEmpty();
        for (String key: keys) {
            Assertions.assertThat(key).isNotEmpty();
            ConfigConfigInterface config = (ConfigConfigInterface) ProviderRootTestScope.EO_CONFIGS.find(configClass, key);
            Assertions.assertThat(config).isNotNull();
        }
    }
}
