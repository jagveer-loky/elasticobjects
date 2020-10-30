package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

/**
 * Abstract class providing an sql config before executing.
 * Created by werner.diwischek on 29.10.20.
 */
public abstract class DbSqlCall extends DbCall  {
    private String sqlKey;
    private DbSqlConfig sqlConfig;

    public DbSqlCall(PermissionType permission)  {
        super(permission);
    }

    public DbSqlCall(PermissionType permission, final String hostConfigKey)  {
        super(permission, hostConfigKey);
    }

    public DbSqlCall(PermissionType permission, final String hostConfigKey, final String sqlKey)  {
        super(permission, hostConfigKey);
        this.sqlKey = sqlKey;
    }

    @Override
    public boolean init (EO eo) {
        if (!hasSqlKey()) {
            throw new EoException("No sqlKey is set!");
        }
        super.init(eo);
        this.sqlConfig = (DbSqlConfig) eo.getConfigsCache().find(DbSqlConfig.class, sqlKey);
        sqlConfig.getRolePermissions().hasPermissions(getPermissions(), eo.getRoles());
        return true;
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
