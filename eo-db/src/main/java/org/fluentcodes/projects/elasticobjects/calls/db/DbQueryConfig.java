package org.fluentcodes.projects.elasticobjects.calls.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.calls.condition.And;
import org.fluentcodes.projects.elasticobjects.calls.condition.Condition;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.DB_EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbQueryConfig extends ListConfig {
    private static final Logger LOG = LogManager.getLogger(DbQueryConfig.class);
    private final String modelKey;
    private final String dbKey;
    private final PathPattern pathPattern;
    private final And and;
    private final String sql;
    private ModelInterface modelCache;
    private DbConfig dbCache;


    public DbQueryConfig(final EOConfigsCache configsCache, final Builder builder)  {
        super(configsCache, builder);
        if (builder.modelKey == null) {
            throw new EoException("Problem with no modelKey.");
        }
        this.modelKey = builder.modelKey;
        this.dbKey = builder.dbKey;
        this.pathPattern = builder.pathPattern;
        this.and = builder.and;
        this.sql = builder.sql;
    }

    protected String getSql()  {
        if (sql != null && !sql.isEmpty()) {
            return sql;
        }
        if (getModelCache().getDbParams() == null) {
            throw new EoException("Db params are null for " + getModelCache().getModelKey());
        }
        if (getModelCache().getDbParams() == null) {
            throw new EoException("No Db table defined for " + getModelCache().getModelKey());
        }
        String tableName = getModelCache().getDbParams().getTable();
        return "select * from " + tableName;
    }

    /**
     * The model name for the bean/actions object {@link ModelInterface}.
     */
    public String getModelKey() {
        return this.modelKey;
    }

    /**
     * A modelCache value
     */
    public ModelInterface getModelCache()  {
        if (this.modelCache == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'modelCache' - 'modelKey''!");
            }
            this.modelCache = getConfigsCache().findModel(modelKey);
        }
        return this.modelCache;
    }

    /**
     * Hibernate object with a certain model
     */
    public String getdbKey() {
        return this.dbKey;
    }

    /**
     * The config embeded int {@link DbQueryConfig}.
     */
    public DbConfig getDbConfig()  {
        if (this.dbCache == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'dbCache' - 'dbKey''!");
            }
            this.dbCache = (DbConfig) getConfigsCache().find(DbConfig.class, dbKey);
        }
        return this.dbCache;
    }

    public PathPattern getPathPattern() {
        return this.pathPattern;
    }

    /**
     * A list of {@link Condition} conditions
     */
    public And getAnd() {
        return this.and;
    }
//</call>

    public static class Builder extends ListConfig.Builder {
        private String sql;
        private String modelKey;
        private String dbKey;
        private PathPattern pathPattern;
        private And and;

        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {
            dbKey = ScalarConverter.toString(values.get(F_DB_KEY));
            sql = (String) configsCache.transform(F_SQL, values);
            pathPattern = new PathPattern(ScalarConverter.toString(values.get(F_PATH_PATTERN)));
            modelKey = ScalarConverter.toString(values.get(ModelConfig.MODEL_KEY));
            and = new And(ScalarConverter.toString(values.get(F_AND)));
            super.prepare(configsCache, values);
            if (dbKey == null || dbKey.isEmpty()) {
                dbKey = getNaturalId().replaceAll(":[^:]+$", "");
            }
            if (modelKey == null || modelKey.isEmpty()) {
                modelKey = getNaturalId().replaceAll(".*:", "");
            }

        }

        public Config build(EOConfigsCache configsCache, Map<String, Object> values)  {
            prepare(configsCache, values);
            return new DbQueryConfig(configsCache, this);
        }
    }
}
