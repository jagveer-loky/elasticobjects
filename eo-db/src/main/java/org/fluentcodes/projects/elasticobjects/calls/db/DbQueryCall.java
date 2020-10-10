package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListReadCall;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.util.List;

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
        return DbSqlConfig.class;
    }

    public DbQueryConfig getDbQueryConfig() {
        return ((DbQueryConfig) getConfig());
    }

    @Override
    public Object execute(EO eo) {
        init(eo);
        return getDbQueryConfig().read(eo, this);
    }

}
