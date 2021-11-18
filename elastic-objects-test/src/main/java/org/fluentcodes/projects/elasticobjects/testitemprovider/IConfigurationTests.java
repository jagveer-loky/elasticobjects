package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;
import org.fluentcodes.tools.xpect.XpectEo;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Werner on 17.11.2021.
 */
public interface IConfigurationTests extends IModelConfigNoCreateTests {

    void compareConfigurations();

    default void assertConfigBeanEqualsPersisted(final String configKey)  {
        ConfigConfig selectedConfig = (ConfigConfig) ProviderRootTestScope.
                EO_CONFIGS.
                find((Class<? extends ConfigInterface>)getModelConfigClass(), configKey);

        assertThat(selectedConfig.toString())
                .isEqualTo(XpectEo.load(selectedConfig));
    }

    default void assertLoadedConfigurationsEqualsPersisted()  {
        String configurationsAsString = ProviderRootTestScope.
                EO_CONFIGS.
                toString((Class<? extends ConfigInterface>)getModelConfigClass());

        assertThat(configurationsAsString)
                .isEqualTo(configurationsAsString);
    }
}
