package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.sql.Connection;

/**
 * Abstract class providing an sql config before executing.
 * Created by werner.diwischek on 29.10.20.
 */
public abstract class DbSqlCall extends HostCall {
    private String sqlKey;
    private DbSqlConfig sqlConfig;

    public DbSqlCall()  {
        super();
    }

    public DbSqlCall(final String hostConfigKey)  {
        super(hostConfigKey);
    }

    public DbSqlCall(final String hostConfigKey, final String sqlKey)  {
        super(hostConfigKey);
        this.sqlKey = sqlKey;
    }

    public void setConfigKey(final String configKey) {
        this.sqlKey = configKey;
    }

    protected DbSqlConfig init(final PermissionType permissionType, final EO eo) {
        if (!hasSqlKey()) {
            throw new EoException("Empty key");
        }
        sqlConfig = (DbSqlConfig)eo.getConfigsCache().find(DbSqlConfig.class, sqlKey);
        sqlConfig.hasPermissions(permissionType, eo.getRoles());
        if (!hasHostConfigKey()) {
            if (sqlConfig.hasDbKey()) {
                setHostConfigKey(sqlConfig.getDbKey());
            }
            else {
                setHostConfigKey(DbConfig.H2_BASIC);
            }
        }
        super.initHostConfig(permissionType, eo);
        return sqlConfig;
    }

    public DbConfig getDbConfig () {
        return (DbConfig) getHostConfig();
    }

    public Connection getConnection () {
        return getDbConfig().getConnection();
    }

    public String getSqlKey() {
        return sqlKey;
    }

    public void setSqlKey(String sqlKey) {
        this.sqlKey = sqlKey;
    }

    public boolean hasSqlKey() {
        return sqlKey!=null && !sqlKey.isEmpty();
    }

    public DbSqlConfig getSqlConfig() {
        return sqlConfig;
    }

}
