package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.domain.Base;

public interface DbBeanInterface extends DbConfigInterface, Base {
    default void setSchema(final String value) {
        getProperties().put(SCHEMA, value);
    }
    default void setDriver(final String value) {
        getProperties().put(DRIVER, value);
    }
    default void setJndi(final String value) {
        getProperties().put(JNDI, value);
    }
    default void setDbType(final String value) {
        getProperties().put(DB_TYPE, DbTypes.valueOf(value));
    }
    default void setExtension(final String value) {
        getProperties().put(EXTENSION, value);
    }
}
