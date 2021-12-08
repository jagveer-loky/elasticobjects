package org.fluentcodes.projects.elasticobjects;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import static org.fluentcodes.projects.elasticobjects.PathElement.LOG_LEVEL;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface IEOObject extends IEORole, IEOSerialize, IEOLog {
    EO createChild(final PathElement fieldKey);

    EO createChild(final PathElement pathElement, final Object value);

    Object get(String... paths);

    default boolean isEmpty() {
        return getModels().isEmpty(get());
    }

    EO set(Object value, String... paths);

    EO setEmpty(String... paths);

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

    EO getEo(String... path);

    EO getEo(PathElement path);

    EO remove(String... path);

    String compare(final EO other);

    default String getLog() {
        if (!hasEo(PathElement.LOGS)) return "";
        return getLogList().stream().collect(Collectors.joining("\n"));
    }

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
        createChild(PathElement.OF_LOG_LEVEL, logLevel);
    }


}
