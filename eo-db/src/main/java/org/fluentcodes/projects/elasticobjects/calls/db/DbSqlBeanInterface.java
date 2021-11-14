package org.fluentcodes.projects.elasticobjects.calls.db;

import java.util.List;

public interface DbSqlBeanInterface extends DbSqlConfigInterface {
    void setSqlList(final List<String> value);
    default void mergeSqlList(final Object value) {
        if (value == null) {
            return;
        }
        if (hasSqlList()) {
            return;
        }
        if (value instanceof List) {
            setSqlList((List)value);
        }
    }
}
