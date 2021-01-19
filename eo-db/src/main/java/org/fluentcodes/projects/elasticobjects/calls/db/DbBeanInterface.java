package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.domain.BaseBeanInterface;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

public interface DbBeanInterface extends DbConfigInterface, BaseBeanInterface {
    default void setSchema(final String value) {
        getProperties().put(SCHEMA, value);
    }
    default void setDriver(final String value) {
        getProperties().put(DRIVER, value);
    }
    default void setJndi(final String value) {
        getProperties().put(JNDI, value);
    }

    default void mergeDbType(final Object value) {
        if (hasDbType()) {
            return;
        }
        if (value == null) {
            return;
        }
        if (value instanceof DbTypes) {
            setDbType((DbTypes) value);
        }
        if (value instanceof String) {
            setDbType(DbTypes.valueOf((String) value));
        }
        throw new EoException("Instance of dbType is '" + value.getClass() + "'");
    }

    default void setDbType(final DbTypes value) {
        getProperties().put(DB_TYPE, value);
    }
    default void setExtension(final String value) {
        getProperties().put(EXTENSION, value);
    }
}
