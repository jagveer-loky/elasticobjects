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

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
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
    private DbConfig dbConfig;
    private List<String> metaDataNames;
    private List<String> metaDataTypes;


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
        getListParams().setRowHead(0);
    }
    public void resolve()  {
        if (isResolved()) {
            return;
        }
        super.resolve();
        dbConfig = (DbConfig) getConfigsCache().find(DbConfig.class, dbKey);
    }

    public List execute() {
        resolve();
        List<List> result = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getDbConfig().getConnection().createStatement();
            resultSet = statement.executeQuery(getSql());
            initMetaData(resultSet.getMetaData());
            List row = metaDataNames;
            while (row!=null) {
                result.add(row);
                row = createRow(resultSet);
            }
        }
        catch (Exception e) {
            if (resultSet!=null) {
                try {
                    resultSet.close();
                } catch (SQLException throwables) {
                    throw new EoException(e);
                }
                resultSet = null;
            }
            if (statement!=null) {
                try {
                    statement.close();
                } catch (SQLException throwables) {
                    throw new EoException(e);
                }
                statement = null;
            }
            throw new EoException(e);
        }
        return result;
    }

    private void initMetaData(ResultSetMetaData metaData) throws SQLException {
        if (metaDataNames!=null) {
            return;
        }
        this.metaDataNames = new ArrayList<>();
        this.metaDataTypes = new ArrayList<>();
        for (int i = 1; i < metaData.getColumnCount(); i++) {
            metaDataTypes.add(metaData.getColumnTypeName(i));
            metaDataNames.add(metaData.getColumnName(i));
        }
    }

    private List createRow(final ResultSet resultSet) throws SQLException {
        resultSet.next();
        if (resultSet.isAfterLast()) {
            return null;
        }
        List row = new ArrayList();
        ResultSetMetaData metaData = resultSet.getMetaData();
        for (int i = 1; i < metaData.getColumnCount(); i++) {
            switch (metaData.getColumnTypeName(i)) {
                case "VARCHAR":
                    row.add(resultSet.getString(i));
                    break;
                case "INTEGER":
                    row.add(resultSet.getInt(i));
                    break;
                case "BOOLEAN":
                    row.add(resultSet.getBoolean(i));
                    break;
                case "DOUBLE":
                    row.add(resultSet.getDouble(i));
                    break;
                case "DATE":
                    row.add(resultSet.getDate(i));
                    break;
                case "BIGINT":
                    row.add(resultSet.getLong(i));
                    break;
            }
        }
        return row;
    }

    public String getSql()  {
        return sql;
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
        if (this.dbConfig == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'dbCache' - 'dbKey''!");
            }
            this.dbConfig = (DbConfig) getConfigsCache().find(DbConfig.class, dbKey);
        }
        return this.dbConfig;
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

    public String getDbKey() {
        return dbKey;
    }

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
