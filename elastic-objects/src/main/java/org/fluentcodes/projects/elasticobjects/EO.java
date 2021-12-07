package org.fluentcodes.projects.elasticobjects;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface EO extends IEOModel, IEOLog, IEOScalar, IEOCall, IEORole {
    EO getParent();

    default boolean hasParent() {
        return getParent() != null;
    }

    default boolean isRoot() {
        return !hasParent();
    }

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

    int sizeEo();

    int size();

    Set<String> keys();

    Set<String> keysEo();

    List<String> keys(String path);

    List<String> keys(PathPattern pathPattern);

    List<String> filterPaths(String filter);

    Map<String, Object> getKeyValues();

    EO getEo(String... path);

    EO getEo(PathElement path);

    EO remove(String... path);

    EO getRoot();

    boolean isCheckObjectReplication();

    void setCheckObjectReplication(boolean checkObjectReplication);

    boolean isEoEmpty();

    String compare(final EO other);

    String toString(JSONSerializationType serializationType);

    default boolean hasRoles() {
        return getRoles() != null && !getRoles().isEmpty();
    }

    default String getLog() {
        if (!hasEo(PathElement.LOGS)) return "";
        return getLogList().stream().collect(Collectors.joining("\n"));
    }

    default boolean hasLogLevel() {
        return hasEo(PathElement.LOG_LEVEL);
    }

    default boolean hasSerializationType() {
        return getRoot().hasEo(PathElement.SERIALIZATION_TYPE);
    }

    JSONSerializationType getSerializationType();

    EO setSerializationType(JSONSerializationType serializationType);

}
