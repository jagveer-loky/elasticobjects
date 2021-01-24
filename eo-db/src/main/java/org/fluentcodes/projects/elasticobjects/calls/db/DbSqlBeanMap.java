package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.models.ConfigBeanMap;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.Map;

/**
 * Created by Werner on 21.1.2021.
 */

public class DbSqlBeanMap extends ConfigBeanMap<DbSqlBean> {
    public DbSqlBeanMap(final Scope scope)  {
        super(scope, DbSqlConfig.class);
    }

    @Override
    protected DbSqlBean createBean(final String naturalId, final Map valueMap) {
        return new DbSqlBean(naturalId, valueMap);
    }

}
