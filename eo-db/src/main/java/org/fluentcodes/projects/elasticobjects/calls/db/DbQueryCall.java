package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;
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
        getListParams().merge(getConfig().getProperties());
        return read(eo, readRaw(eo));
    }
    
    public List readRaw(final EO eo) {
        List result = new ArrayList<>();

        ResultSet resultSet = null;
        Statement statement = getDbQueryConfig().getStatement();
        try {
            resultSet = statement.executeQuery(getDbQueryConfig().getSql());
            getDbQueryConfig().initMetaData(resultSet.getMetaData());
        }
        catch (Exception e) {
            closeAll(statement, resultSet);
            throw new EoException("Exception get resultSet for sql "  + getDbQueryConfig().getSql() + " and key " + getConfigKey() + ": " + e.getMessage());
        }
            if (!getListParams().hasColKeys()) {
                getListParams().setColKeys(getDbQueryConfig().getMetaDataNames());
            }

        List rowEntry = null;
        int i = -1;
        try {
            while ((rowEntry = getDbQueryConfig().createRow(resultSet)) != null) {
                i++;
                if (!getListParams().isRowStart(i)) {
                    continue;
                }
                if (!getListParams().isRowEnd(i)) {
                    return result;
                }
                addRowEntry(eo.getConfigsCache(), result, rowEntry, getListParams());
            }
        }
        catch (Exception e) {
            closeAll(statement, resultSet);
            throw new EoException("Exception create a list from line counter " + i + " and " + getConfigKey() + ": " + e.getMessage());
        }
        closeAll(statement, resultSet);
        return result;
    }

    private final void closeAll(Statement statement, ResultSet resultSet) {
            if (resultSet != null) {
                try {
                    resultSet.close();
                } catch (Exception e) {
                    throw new EoException("Exception closing resultSet " +  getConfigKey() + ": " + e.getMessage());
                }
            }
            if (statement != null) {
                try {
                    statement.close();
                } catch (Exception e) {
                    new EoException("Exception closing statement " +  getConfigKey() + ": " + e.getMessage());
                }
            }
        }



}
