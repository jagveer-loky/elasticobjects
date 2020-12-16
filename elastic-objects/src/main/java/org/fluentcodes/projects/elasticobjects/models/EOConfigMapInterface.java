package org.fluentcodes.projects.elasticobjects.models;

import java.util.Set;

/**
 * @since Created by Werner on 12.10.2018.
 */
public interface EOConfigMapInterface<T extends ConfigConfigInterface> {
    T find(final String key) ;
    Set<String> getKeys();
}
