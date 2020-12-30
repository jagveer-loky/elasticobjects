package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigCall;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;

import java.util.List;
import java.util.Map;
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
        EO cloneMapEo = EoRoot.ofClass(ProviderRootTestScope.EO_CONFIGS, Map.class);
        cloneMapEo.setSerializationType(JSONSerializationType.STANDARD);
        Set<String> configKeys = ProviderRootTestScope.EO_CONFIGS.getConfigKeys(configClass);
        for (String key: configKeys) {
            if (!key.matches(configKey)) continue;
            cloneMapEo.set(ProviderRootTestScope.EO_CONFIGS.find(configClass, key), key);
        }
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.STANDARD)
                .build()
                .compareAsString(cloneMapEo);
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
