package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigProperties;

import java.util.List;

public interface DbSqlConfigInterface extends ConfigConfigInterface, ConfigProperties {
    final static String DB_KEY = "dbKey";

    default String getSql() {
        if (!hasSqlList()) {
            throw new EoInternalException("Problem with empty sql list");
        }
        if (getSqlList().size()>1) {
            throw new EoInternalException("Problem with " + getSqlList().size() + " entriies in sql list");
        }
        return getSqlList().get(0);
    }

    default boolean hasDbKey() {
        return getDbKey()!=null && !getDbKey().isEmpty();
    }

    default String getDbKey() {
        return (String) getProperties().get(DB_KEY);
    }

    List<String> getSqlList();
    default boolean hasSqlList() {
        return getSqlList()!=null && !getSqlList().isEmpty();
    }
}
