package org.fluentcodes.projects.elasticobjects;

/**
 * Offers an adapter for scalar wrapper to access elements via path.
 */

public interface IEOScalar extends IEOModel {
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

    EO getRoot() ;

    void setCheckObjectReplication(boolean checkObjectReplication);

    boolean isChanged();

    boolean isEoEmpty();

    String toString(JSONSerializationType serializationType);
}
