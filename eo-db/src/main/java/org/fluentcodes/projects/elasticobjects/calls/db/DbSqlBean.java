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
    private Map properties;

    public DbSqlBean() {
        super();
        defaultConfigModelKey();
    }

    public DbSqlBean(final Map<String, Object> map) {
        super();

        defaultConfigModelKey();
    }

    public void merge(final Map configMap) {
        super.merge(configMap);
    }

    @Override
    public Map<String, Object> getProperties() {
        return properties;
    }

    public void setProperties(Map properties) {
        this.properties = properties;
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
