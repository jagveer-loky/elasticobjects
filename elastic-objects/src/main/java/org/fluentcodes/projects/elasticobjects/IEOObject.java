package org.fluentcodes.projects.elasticobjects;

import java.util.List;
import java.util.Map;
import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.PathElement.LOG_LEVEL;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface IEOObject extends IEOScalar {
    Object get(String... paths);

    default boolean isEmpty() {
        return getModels().isEmpty(get());
    }

    IEOScalar set(Object value, String... paths);

    IEOScalar createChild(String... paths);

    EO mapObject(Object source);

    boolean hasEo(String path);

    default int sizeEo() {
        return keysEo().size();
    }

    default int size() {
        return keys().size();
    }

    Set<String> keys();

    Set<String> keysEo();

    List<String> keys(String path);

    List<String> keys(PathPattern pathPattern);

    List<String> filterPaths(String filter);

    Map<String, Object> getKeyValues();

    IEOScalar getEo(String... path);

    EO remove(String... path);

    String compare(final EO other);

    @Override
    default boolean hasLogLevel() {
        return hasEo(LOG_LEVEL);
    }

    @Override
    default LogLevel getLogLevel() {
        if (hasLogLevel()) {
            return (LogLevel) get(LOG_LEVEL);
        }
        return getParent().getLogLevel();
    }

    @Override
    default void setLogLevel(LogLevel logLevel) {
        ((EoChild)this).createChild(PathElement.OF_LOG_LEVEL, logLevel);
    }


}
