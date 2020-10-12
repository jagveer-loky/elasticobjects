package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.condition.And;
import org.fluentcodes.projects.elasticobjects.calls.condition.Condition;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.calls.lists.CsvSimpleReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_AND;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_PATH_PATTERN;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbQueryConfig extends ConfigResourcesImpl implements PropertiesDbSqlAccessor {

    public static final String SQL = "sql";
    private final String modelKey;
    private final PathPattern pathPattern;
    private final And and;
    private final String sql;

    private ModelConfigInterface modelCache;
    private DbConfig dbConfig;
    private List<String> metaDataNames;
    private List<String> metaDataTypes;

    public DbQueryConfig(final EOConfigsCache configsCache, final Map map)  {
        super(configsCache, map);
        sql = (String)map.get(SQL);
        pathPattern = new PathPattern((String) map.get(F_PATH_PATTERN));
        modelKey = (String)map.get(ModelConfig.MODEL_KEY);
        and = new And((String)map.get(F_AND));
    }

    public void resolve()  {
        if (isResolved()) {
            return;
        }
        super.resolve();
        this.dbConfig = (DbConfig) getConfigsCache().find(HostConfig.class, getDbKey());
    }

    public List<String> getMetaDataNames() {
        return metaDataNames;
    }

    public Statement getStatement() {
        resolve();
        try {
            return getDbConfig().getConnection().createStatement();
        }
        catch (Exception e) {
            throw new EoException("Problem creating statement for " + getConfigModelKey() + ": " + e.getMessage());
        }
    }



    public List readRaw(ListParams params) {
        throw new EoException("Deprecated");
    }

    public Object read(CsvSimpleReadCall readCall) {
        throw new EoException("Deprecated");
    }

    protected List<String> initMetaData(ResultSetMetaData metaData) throws SQLException {
        if (metaDataNames!=null) {
            return metaDataNames;
        }
        this.metaDataNames = new ArrayList<>();
        this.metaDataTypes = new ArrayList<>();
        for (int i = 1; i < metaData.getColumnCount(); i++) {
            metaDataTypes.add(metaData.getColumnTypeName(i));
            metaDataNames.add(metaData.getColumnName(i));
        }
        return metaDataNames;
    }

    protected List createRow(final ResultSet resultSet) throws SQLException {
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
     * The model name for the bean/actions object {@link ModelConfigInterface}.
     */
    public String getModelKey() {
        return this.modelKey;
    }

    /**
     * A modelCache value
     */
    public ModelConfigInterface getModelCache()  {
        if (this.modelCache == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'modelCache' - 'modelKey''!");
            }
            this.modelCache = getConfigsCache().findModel(modelKey);
        }
        return this.modelCache;
    }

    /**
     * The config embeded int {@link DbQueryConfig}.
     */
    public DbConfig getDbConfig()  {
        if (this.dbConfig == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'dbCache' - 'dbKey''!");
            }
            this.dbConfig = (DbConfig) getConfigsCache().find(DbConfig.class, getDbKey());
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

}
