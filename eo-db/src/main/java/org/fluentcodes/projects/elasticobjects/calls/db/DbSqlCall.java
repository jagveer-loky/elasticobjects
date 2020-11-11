package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.sql.Connection;

/*==>{ALLHeader.tpl, ., , JAVA|>}|*/
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
/**
 * Abstract class providing an sql config before executing.
 *
 * @author Werner Diwischek
 * @creationDate 
 * @modificationDate Wed Nov 11 06:55:21 CET 2020
 */
public abstract class DbSqlCall extends HostCall  {
/*=>{}.*/

/*==>{ALLStaticNames.tpl, fieldMap/*, override eq false, JAVA|>}|*/
   public static final String SQL_KEY = "sqlKey";
/*=>{}.*/

/*==>{ALLInstanceVars.tpl, fieldMap/*, , JAVA|>}|*/
   private  String sqlKey;
/*=>{}.*/
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

    public DbSqlConfig getSqlConfig() {
        return sqlConfig;
    }

/*==>{ALLSetter.tpl, fieldMap/*, , JAVA|>}|*/
    /**
    The key to select a configuration from DbSqlConfig within DbSqlCall.
    */

    public DbSqlCall setSqlKey(String sqlKey) {
        this.sqlKey = sqlKey;
        return this;
    }
    
    public String getSqlKey () {
       return this.sqlKey;
    }
    
    public boolean hasSqlKey () {
        return sqlKey!= null && !sqlKey.isEmpty();
    }
/*=>{}.*/

}
