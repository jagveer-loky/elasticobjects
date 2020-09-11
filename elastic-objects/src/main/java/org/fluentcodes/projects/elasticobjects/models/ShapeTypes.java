package org.fluentcodes.projects.elasticobjects.models;


/**
 * Created by Werner on 18.11.2014.
 */
public enum ShapeTypes {
    MAP(),
    LIST(),
    SET(),
    INTERFACE(),
    OBJECT(),
    SCALAR_SERIALIZED(),
    SCALAR(),
    NUMBER(),
    ENUM(),
    NULL(),
    NONE(),
    BEAN(),
    CALL_BEAN(),
    MODEL(),
    INSTANCE(),
    CONFIG();
    ShapeTypes() {
    }
}
