package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.condition.And;
import org.fluentcodes.projects.elasticobjects.calls.condition.Condition;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListConfigInterface;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_AND;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_PATH_PATTERN;
import static org.fluentcodes.projects.elasticobjects.calls.lists.ListInterface.LIST_PARAMS;


/**
 * Created by Werner on 09.10.2016.
 */
public class DbQueryConfig extends ConfigResourcesImpl implements ListConfigInterface {

    public static final String SQL = "sql";
    private final String modelKey;
    private final String dbKey;
    private final PathPattern pathPattern;
    private final And and;
    private final String sql;
    private ModelInterface modelCache;
    private DbConfig dbConfig;
    private List<String> metaDataNames;
    private List<String> metaDataTypes;
    private final ListParams listParams;

    public DbQueryConfig(final EOConfigsCache configsCache, final Map map)  {
        super(configsCache, map);
        dbKey = (String)map.get(DbConfig.DB_KEY);
        sql = (String)map.get(SQL);
        pathPattern = new PathPattern((String) map.get(F_PATH_PATTERN));
        modelKey = (String)map.get(ModelConfig.MODEL_KEY);
        and = new And((String)map.get(F_AND));
        this.listParams = map.containsKey(LIST_PARAMS) ? new ListParams((Map)map.get(LIST_PARAMS)) : new ListParams();
        listParams.setRowHead(0);
    }

    public void resolve()  {
        if (isResolved()) {
            return;
        }
        super.resolve();
        this.dbConfig = (DbConfig) getConfigsCache().find(HostConfig.class, dbKey);
    }

    @Override
    public ListParams getListParams() {
        return listParams;
    }

    @Override
    public List readRaw(ListParams params) {
        resolve();
        List result = new ArrayList<>();
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = getDbConfig().getConnection().createStatement();
            resultSet = statement.executeQuery(getSql());
            initMetaData(resultSet.getMetaData());
            if (!params.hasColKeys()) {
                params.setColKeys(metaDataNames);
            }

            List rowEntry = null;
            int i = -1;
            while ((rowEntry = createRow(resultSet)) !=null) {
                i++;
                if (!params.isRowStart(i)) {
                    continue;
                }
                if (!params.isRowEnd(i)) {
                    return result;
                }
                addRowEntry(result, rowEntry, params);
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

}
