package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.PermissionBean;

import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 11.12.2020.
 */
public class DbSqlBean extends PermissionBean implements DbSqlBeanInterface {
    public static final String SQL_LIST = "sqlList";
    public static final String DEFAULT_HOST_CONFIG_KEY = "defaultHostConfigKey";
    private List<String> sqlList;

    public DbSqlBean() {
        super();
        defaultConfigModelKey();
    }

    public DbSqlBean(final Map<String, Object> map) {
        super();

        defaultConfigModelKey();
    }

    protected void merge(final Map values) {
        super.merge(values);
    }

    @Override
    public void defaultConfigModelKey() {
        if (hasConfigModelKey()) {
            return;
        }
        setConfigModelKey(DbSqlConfig.class.getSimpleName());
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public void setSqlList(List<String> sqlList) {
        this.sqlList = sqlList;
    }

    @Override
    public String toString() {
        return getNaturalId() + " -> ";
    }
}
