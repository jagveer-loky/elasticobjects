package org.fluentcodes.projects.elasticobjects.models;

import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * @since Created by Werner on 12.10.2018.
 */
public interface ConfigConfigMapInterface<T extends ConfigConfigInterface, U extends ConfigBeanInterface> {
    Map<String, U> createBeanMap(ConfigMaps configMaps);

    Map<String, T> createConfigMap(ConfigMaps configMaps);
}
