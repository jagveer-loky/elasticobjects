package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;
import org.fluentcodes.projects.elasticobjects.xpect.XpectStringJunit4;

/**
 * Created by Werner on 17.11.2021.
 */
public interface IConfigurationTests extends IModelConfigNoCreateTests {
    default void assertConfigBeanEqualsPersisted(final String configKey)  {
        ConfigConfig selectedConfig = (ConfigConfig) ProviderConfigMaps.
                CONFIG_MAPS.
                find((Class<? extends ConfigInterface>)getModelConfigClass(), configKey);

        XpectStringJunit4.assertStatic(selectedConfig.toString());
    }

    default void assertLoadedConfigurationsEqualsPersisted()  {
        String configurationsAsString = ProviderConfigMaps.
                CONFIG_MAPS.
                toString((Class<? extends ConfigInterface>)getModelConfigClass());

        XpectStringJunit4.assertStatic(configurationsAsString);
    }
}
