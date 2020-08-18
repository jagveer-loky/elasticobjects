package org.fluentcodes.projects.elasticobjects.calls.db;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListCall;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListReadCall;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Reads and writes database tables.
 * Created by werner.diwischek on 18.12.17.
 */
public class DbQueryCall extends ListReadCall {

    public DbQueryCall()  {
        super();
    }
    public DbQueryCall(final String configKey)  {
        super(configKey);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return DbQueryConfig.class;
    }

    public DbQueryConfig getDbQueryConfig() {
        return ((DbQueryConfig) getConfig());
    }

    public Object execute(EO eo) {
        return null;
    }

}
