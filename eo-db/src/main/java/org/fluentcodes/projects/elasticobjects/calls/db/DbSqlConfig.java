package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.DB_EO_STATIC.F_DB_KEY;
import static org.fluentcodes.projects.elasticobjects.DB_EO_STATIC.F_DB_SQL_LIST;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbSqlConfig extends ConfigResourcesImpl {
    private final String dbKey;
    private final List<String> sqlList;
    private DbConfig dbCache;

    public DbSqlConfig(final EOConfigsCache provider, final Builder builder)  {
        super(provider, builder);
        this.dbKey = builder.dbKey;
        this.sqlList = builder.sqlList;
        if (builder.isExpanded()) {
            this.dbCache = new DbConfig(provider, builder);
        }
    }

    public DbConfig getDbCache()  {
        if (this.dbCache == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'hibernateCache' - 'hibernateKey''!");
            }
            this.dbCache = (DbConfig) getConfigsCache().find(DbConfig.class, dbKey);
        }
        return this.dbCache;
    }

    public String getDbKey() {
        return dbKey;
    }

    public List<String> getSqlList() {
        return sqlList;
    }

    public static class Builder extends DbConfig.Builder {
        private String dbKey;
        private List<String> sqlList;

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {

            sqlList = (List) configsCache.transform(F_DB_SQL_LIST, values);
            super.prepare(configsCache, values);
            dbKey = (String) configsCache.transform(F_DB_KEY, values);
            if (dbKey == null || dbKey.isEmpty()) {
                dbKey = getNaturalId().replaceAll(":[^:]+$", "");
            }
        }

        @Override
        public Config build(EOConfigsCache configsCache, Map<String, Object> values)  {
            prepare(configsCache, values);
            return new DbSqlConfig(configsCache, this);
        }
    }
}
