package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.List;
import java.util.Map;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbSqlConfig extends ConfigResourcesImpl implements PropertiesDbSqlAccessor {
    public static final String DB_SQL_LIST = "sqlList";
    private final List<String> sqlList;
    private DbConfig dbConfig;

    public DbSqlConfig(final EOConfigsCache cache, final Map map)  {
        super(cache, map);
        sqlList = (List) map.get(DB_SQL_LIST);
    }

    public void resolve()  {
        if (isResolved()) {
            return;
        }
        dbConfig = (DbConfig) getConfigsCache().find(HostConfig.class, getDbKey());
    }

    public boolean execute() {
        resolve();
        boolean execution = false;
        for (String sql : sqlList) {
            execution = dbConfig.execute(sql) || execution;
        }
        return execution;
    }

    public List<String> getSqlList() {
        return sqlList;
    }
}
