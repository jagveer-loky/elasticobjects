package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigInterface;

import java.util.List;

public interface DbSqlConfigInterface extends ConfigInterface {
    default String getSql() {
        if (!hasSqlList()) {
            throw new EoInternalException("Problem with empty sql list");
        }
        if (getSqlList().size()>1) {
            throw new EoInternalException("Problem with " + getSqlList().size() + " entriies in sql list");
        }
        return getSqlList().get(0);
    }

    List<String> getSqlList();
    default boolean hasSqlList() {
        return getSqlList()!=null && !getSqlList().isEmpty();
    }
}
