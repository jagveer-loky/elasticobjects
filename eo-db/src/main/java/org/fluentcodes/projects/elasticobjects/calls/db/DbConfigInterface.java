package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigProperties;

public interface DbConfigInterface extends ConfigConfigInterface, ConfigProperties {
    String SCHEMA = "schema";
    String DRIVER = "driver";
    String JNDI = "jndi";
    String DB_TYPE = "dbType";
    String EXTENSION = "extension";

    default boolean hasSchema() {
        return getSchema()!=null && !getSchema().isEmpty();
    }
    default String getSchema() {
        return (String) getProperties().get(SCHEMA);
    }

    default boolean hasDriver() {
        return getDriver()!=null && !getDriver().isEmpty();
    }
    default String getDriver() {
        return (String) getProperties().get(DRIVER);
    }

    default boolean hasJndi() {
        return getJndi()!=null && !getJndi().isEmpty();
    }
    default String getJndi() {
        return (String) getProperties().get(JNDI);
    }

    default boolean hasDbType() {
        return getDbType()!=null;
    }

    default DbTypes getDbType() {
        return (DbTypes)getProperties().get(DB_TYPE);
    }

    default boolean hasExtension() {
        return getExtension()!=null && !getExtension().isEmpty();
    }

    default String getExtension() {
        return (String) getProperties().get(EXTENSION);
    }
}
