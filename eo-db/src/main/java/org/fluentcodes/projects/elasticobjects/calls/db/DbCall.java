package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.ResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.ResourceReadCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListReadCall;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.security.Permission;

import static org.fluentcodes.projects.elasticobjects.calls.db.DbConfig.H2_BASIC;

/**
 * Super class setting the dbConfig as
 * Created by werner.diwischek on 29.10.20.
 */
public abstract class DbCall extends ResourceCall {
    public DbCall(PermissionType permission)  {
        super(permission);
    }

    public DbCall(final PermissionType permission, final String configKey)  {
        super(permission, configKey );
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return HostConfig.class;
    }

    public DbConfig getDbConfig() {
        return ((DbConfig) getConfig());
    }


    @Override
    public boolean init (EO eo) {
        if (!hasConfigKey()) {
            setConfigKey(H2_BASIC);
        }
        super.init(eo);
        return true;
    }
}
