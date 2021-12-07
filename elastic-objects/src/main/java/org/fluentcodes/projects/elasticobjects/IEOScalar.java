package org.fluentcodes.projects.elasticobjects;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface IEOScalar {
    /**
     * Creates a new object an map it to the object
     *
     * @
     */
    String getFieldKey();

    Path getPath();
    String getPathAsString();

    Object get();

    IEOScalar getRoot() ;

    void setCheckObjectReplication(boolean checkObjectReplication);

    boolean isChanged();

    boolean isEoEmpty();

    String toString(JSONSerializationType serializationType);


}
