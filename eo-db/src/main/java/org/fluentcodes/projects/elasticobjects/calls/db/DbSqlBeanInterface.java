package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.domain.BaseBeanInterface;

import java.util.List;

public interface DbSqlBeanInterface extends DbSqlConfigInterface, BaseBeanInterface {
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
