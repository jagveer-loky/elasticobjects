package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEo;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Werner on 17.11.2021.
 */
public interface IConfigurationTests extends IModelConfigNoCreateTests {

    void compareConfigurations();

    default void assertConfigBeanEqualsPersisted(final String configKey)  {
        ConfigConfig selectedConfig = (ConfigConfig) ProviderConfigMaps.
                CONFIG_MAPS.
                find((Class<? extends ConfigInterface>)getModelConfigClass(), configKey);

        assertThat(selectedConfig.toString())
                .isEqualTo(XpectEo.load(selectedConfig));
    }

    default void assertLoadedConfigurationsEqualsPersisted()  {
        String configurationsAsString = ProviderConfigMaps.
                CONFIG_MAPS.
                toString((Class<? extends ConfigInterface>)getModelConfigClass());

        assertThat(configurationsAsString)
                .isEqualTo(configurationsAsString);
    }
}
