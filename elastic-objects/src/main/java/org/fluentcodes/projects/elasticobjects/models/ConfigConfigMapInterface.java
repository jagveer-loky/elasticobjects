package org.fluentcodes.projects.elasticobjects.models;

import java.util.Map;

/**
 * @since Created by Werner on 12.10.2018.
 */
public interface ConfigConfigMapInterface<T extends ConfigBeanInterface, U extends ConfigConfigInterface> {
    Map<String, T> createBeanMap(ConfigMaps configMaps);

    Map<String, U> createConfigMap(ConfigMaps configMaps);
}
