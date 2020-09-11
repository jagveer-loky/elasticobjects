package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbSqlCall extends  CallResource<Boolean> {
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

    public DbSqlConfig getDbSqlConfig() {
        return (DbSqlConfig) getConfig();
    }

    public Boolean execute(EO eo)  {
        resolve(eo.getConfigsCache());
        if (eo == null) {
            throw new EoException("Null or empty EO. But checkConfig needs values to be readed from the db!");
        }
        return getDbSqlConfig().execute();
    }
}
