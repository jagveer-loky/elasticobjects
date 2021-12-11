package org.fluentcodes.projects.elasticobjects;

import java.io.StringWriter;

import static org.fluentcodes.projects.elasticobjects.EOToJSON.stringify;

/**
 * Offers an adapter for scalar wrapper to access elements via path.
 */

public interface IEOBase {
    EO getParent();

    default boolean hasParent() {
        return getParent() != null;
    }

    default boolean isRoot() {
        return !hasParent();
    }

    String getFieldKey();

    Path getPath();

    String getPathAsString();

    Object get();
    IEOScalar getEo(String... path);
    Object get(final String... pathStrings);

    void set(final Object value);
    IEOScalar set(Object value, String... paths);

    EO getRoot();

    default void setCheckObjectReplication(boolean checkObjectReplication) {
        getRoot().setCheckObjectReplication(checkObjectReplication);
    }

    boolean isChanged();

    void setChanged();

    default boolean isEmpty() {
        return get() == null;
    }

    default boolean isEoEmpty() {
        return true;
    }
    default boolean hasEo(String path) {
        return false;
    }
}
