package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.models.ConfigFactory;
import org.fluentcodes.projects.elasticobjects.models.Scope;

/**
 * Created by Werner on 21.1.2021.
 */

public class DbSqlFactory extends ConfigFactory< DbSqlBean, DbSqlConfig> {
    public DbSqlFactory() {
        super(Scope.DEV, DbSqlBean.class, DbSqlConfig.class);
    }
    public DbSqlFactory(final Scope scope) {
        super(scope, DbSqlBean.class, DbSqlConfig.class);
    }
}
