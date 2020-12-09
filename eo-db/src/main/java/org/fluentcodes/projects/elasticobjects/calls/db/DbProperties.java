package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.ConfigProperties;

public interface DbProperties extends Config, ConfigProperties {
    String SCHEMA = "schema";
    String DRIVER = "driver";
    String JNDI = "jndi";
    String DB_TYPE = "dbType";
    String EXTENSION = "extension";

    default boolean hasSchema() {
        return getSchema()!=null && !getSchema().isEmpty();
    }

    default String getSchema() {
        return hasProperties() ? (String) getProperties().get(SCHEMA) : null;
    }

    default void setSchema(final String module) {
        if (hasProperties()) {
            getProperties().put(SCHEMA, module);
        }
    }

    default boolean hasDriver() {
        return getDriver()!=null && !getDriver().isEmpty();
    }

    default String getDriver() {
        return hasProperties() ? (String) getProperties().get(DRIVER) : null;
    }

    default void setDriver(final String module) {
        if (hasProperties()) {
            getProperties().put(DRIVER, module);
        }
    }

    default boolean hasJndi() {
        return getJndi()!=null && !getJndi().isEmpty();
    }

    default String getJndi() {
        return hasProperties() ? (String) getProperties().get(JNDI) : null;
    }

    default void setJndi(final String module) {
        if (hasProperties()) {
            getProperties().put(JNDI, module);
        }
    }


    default boolean hasDbType() {
        return getDbType()!=null;
    }

    default DbTypes getDbType() {
        Object dbType = getProperties().get(DB_TYPE);
        if (!hasProperties() || dbType == null) {
            return null;
        }
        if (dbType instanceof String) {
            return DbTypes.valueOf((String)dbType);
        }
        if (dbType instanceof DbTypes) {
            return (DbTypes)getProperties().get(DB_TYPE);
        }
        throw new EoInternalException("Could not map '" + dbType + "' " + getProperties().get(DB_TYPE).getClass());
    }

    default void setDbType(final String module) {
        if (hasProperties() && hasDbType()) {
            getProperties().put(DB_TYPE, DbTypes.valueOf(module));
        }
    }

    default boolean hasExtension() {
        return getExtension()!=null && !getExtension().isEmpty();
    }

    default String getExtension() {
        return hasProperties() ? (String) getProperties().get(EXTENSION) : null;
    }

    default void setExtension(final String module) {
        if (hasProperties()) {
            getProperties().put(EXTENSION, module);
        }
    }
}
