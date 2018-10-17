package org.fluentcodes.projects.elasticobjects.config;
import java.util.Map;
import java.util.Set;

/**
 * @since Created by Werner on 12.10.2018.
 */
public interface EOConfigsInterface<T extends Config> {
    Map<String, T> getConfigMap();
    T find(final String key) throws Exception;
    Set<String> getKeys() ;
}
