package org.fluentcodes.projects.elasticobjects.config;


/**
 * Created by Werner on 18.11.2014.
 */
public enum ShapeTypes {
    MAP(),
    LIST(),
    INTERFACE(),
    SCALAR_SERIALIZED(),
    OBJECT(),
    SCALAR(),
    SET(),
    NULL(),
    NONE(),
    BEAN(),
    ACTION(),
    ACTIONDIV(),
    ACTIONMODEL(),
    MODEL(),
    ADAPTER(),
    INSTANCE(),
    ENUM(),
    CONFIG(),
    CALL();

    ShapeTypes() {
    }
}
