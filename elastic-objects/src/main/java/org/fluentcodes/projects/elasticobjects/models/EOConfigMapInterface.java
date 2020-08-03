package org.fluentcodes.projects.elasticobjects.config;

import java.util.Map;
import java.util.Set;

/**
 * @since Created by Werner on 12.10.2018.
 */
public interface EOConfigMapInterface<T extends Config> {
    T find(final String key) ;
    Set<String> getKeys();
}
