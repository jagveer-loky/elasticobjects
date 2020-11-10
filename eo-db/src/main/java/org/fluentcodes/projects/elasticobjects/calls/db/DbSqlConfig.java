package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.ResourceConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.List;
import java.util.Map;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbSqlConfig extends ResourceConfig implements DbSqlProperties {
    public static final String SQL_LIST = "sqlList";
    public static final String DEFAULT_HOST_CONFIG_KEY = "defaultHostConfigKey";
    private final List<String> sqlList;
    private final String defaultHostConfigKey;

    public DbSqlConfig(final EOConfigsCache cache, final Map map)  {
        super(cache, map);
        sqlList = (List) map.get(SQL_LIST);
        defaultHostConfigKey = (String) map.get(DEFAULT_HOST_CONFIG_KEY);
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public boolean hasSqlList() {
        return sqlList!=null && !sqlList.isEmpty();
    }

    public String getSql() {
        if (!hasSqlList()) {
            throw new EoInternalException("Problem with empty sql list");
        }
        if (sqlList.size()>1) {
            throw new EoInternalException("Problem with " + sqlList.size() + " entriies in sql list");
        }
        return sqlList.get(0);
    }
}
