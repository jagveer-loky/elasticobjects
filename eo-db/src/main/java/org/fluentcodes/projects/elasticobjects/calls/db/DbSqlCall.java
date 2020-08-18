package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.sql.SQLException;
import java.util.List;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbSqlCall extends  CallResource {
    public DbSqlCall()  {
        super(PermissionType.EXECUTE);
    }
    public DbSqlCall(final String configKey)  {
        super(PermissionType.EXECUTE, configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return DbSqlConfig.class;
    }

    public DbSqlConfig getDbSqlCache() {
        return (DbSqlConfig) getConfig();
    }

    public DbConfig getDbConfig()  {
        return getDbSqlCache().getDbCache();
    }

    public List<String> getSqlList() {
        return getDbSqlCache().getSqlList();
    }

    public EO execute(EO adapter)  {
        resolve(adapter.getConfigsCache());
        if (adapter == null) {
            throw new EoException("Null or empty adapter. But checkConfig needs values to be readed from the db!");
        }
        DbIO dbIO = new DbIO(getDbConfig());
        try {
            dbIO.execute(getSqlList());
        } catch (SQLException e) {
            throw new EoException(e);
        }
        return adapter;
    }
}
