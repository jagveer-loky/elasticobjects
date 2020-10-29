package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.domain.Base.ID;
import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;

public class PreparedStatementValues {
    private SqlType sqlType = SqlType.NONE;
    private final List values;
    private final StringBuilder statement = new StringBuilder();
    public PreparedStatementValues() {
        values = new ArrayList();
    }

    public PreparedStatementValues(final String preparedStatement, EO eo) {
        this();
        statement.append(preparedStatement);
        if (countParams()==0) {
            return;
        }
        if (eo.isScalar()) {
            values.add(eo.get());
        }
        else {
            for (Object value: eo.getKeyValues().values()) {
                values.add(value);
            }
        }
        checkHealth();
    }

    private void resolve(final PreparedStatement preparedStatement) {
        int counter = 1;
        try {
            for (Object value : values) {
                if (value instanceof String) {
                    preparedStatement.setString(counter, (String) value);
                } else if (value instanceof Integer) {
                    preparedStatement.setInt(counter, (Integer) value);
                } else if (value instanceof Long) {
                    preparedStatement.setLong(counter, (Long) value);
                } else {
                    throw new EoInternalException("No validated " + counter + " - " + value);
                }
                counter++;
            }
        }
        catch (SQLException e) {
            throw new EoException(counter + ": " + e.getMessage());
        }
    }

    public List read(Connection connection, EOConfigsCache configsCache, ListParams listParams) {
        if (connection == null) {
            throw new EoInternalException("Null connection");
        }
        checkHealth();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement.toString());
            resolve(preparedStatement);
            ResultSet resultSet = null;
            try {
                resultSet = preparedStatement.executeQuery();
                return readRaw(resultSet, configsCache, listParams);
            }
            catch (Exception e) {
                closeAll(preparedStatement, resultSet);
                throw new EoException("Exception get resultSet for sql "  + statement.toString() + ": " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new EoException(e);
        }
    }

    private List readRaw(final ResultSet resultSet, EOConfigsCache configsCache, ListParams listParams) {
        List result = new ArrayList<>();
        if (listParams.hasRowHead()) {
            try {
                listParams.setColKeys(initMetaData(resultSet));
            } catch (SQLException e) {
                throw new EoException(e);
            }
        }
        List rowEntry = null;
        int i = 1;
        try {
            while ((rowEntry = createRow(resultSet)) != null) {
                if (i>=listParams.getRowStart()) {
                    listParams.addRowEntry(configsCache, result, rowEntry);
                }
                if (i>listParams.getRowEnd()) {
                    return result;
                }
                i++;
            }
        }
        catch (Exception e) {
            throw new EoException("Exception create a list from line counter " + i + ": " + e.getMessage());
        }
        return result;
    }

    public String getStatement() {
        return statement.toString();
    }

    public void append(String add) {
        statement.append(add);
    }

    private int countParams() {
        return statement.toString().replaceAll("[^\\?]","").length();
    }

    private void checkHealth() {
        if (countParams() != values.size()) {
            throw new EoException("Problem that prepared statement has " + countParams() + " variables but only " + values.size() + " values are provided!");
        }
    }

    public boolean hasValues() {
        return values!=null && !values.isEmpty();
    }

    public void addValue(final Object value) {
        values.add(value);
    }
    public List getValues() {
        return new ArrayList(values);
    }

    public int find(Connection connection, String table, Map<String,Object> values) {
        sqlType = SqlType.FIND;
        if (connection == null) {
            throw new EoInternalException("Null connection");
        }

        StringBuilder builder = new StringBuilder();
        if (values.get(ID)!=null) {
            this.values.add(values.get(ID));
            builder.append(" id = ? ");
        }
        else if (values.get(NATURAL_ID)!=null) {
            this.values.add(values.get(NATURAL_ID));
            builder.append(" naturalId = ? ");
        }
        else {
            throw new EoInternalException("no id nor naturalid provided.");
        }
        statement.append("SELECT * FROM ");
        statement.append(table);
        statement.append(" WHERE ");
        statement.append(builder.toString().replaceAll(", ", " "));
        return execute(connection);
    }

    private int execute (Connection connection) {
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(statement.toString());
            resolve(preparedStatement);
            ResultSet resultSet = null;
            try {
                if (sqlType.equals(SqlType.FIND)) {
                    resultSet = preparedStatement.executeQuery();
                    return resultSet.next()?1:0;
                }
                else if (sqlType.equals(SqlType.UPDATE)) {
                    return preparedStatement.executeUpdate();
                }
                else if (sqlType.equals(SqlType.INSERT)) {
                    return preparedStatement.executeUpdate();
                }
                else {
                    throw new EoException("Nothing to do for  " + sqlType.name());
                }
            }
            catch (Exception e) {
                closeAll(preparedStatement, resultSet);
                throw new EoException("Exception get resultSet for sql "  + statement.toString() + ": " + e.getMessage());
            }
        } catch (SQLException e) {
            throw new EoException(e);
        }

    }

    public PreparedStatementValues createSelect(String table, Map<String,Object> values) {
        StringBuilder builderValues = new StringBuilder("");
        for (String key: values.keySet()) {
            this.values.add(values.get(key));
            builderValues.append(key + " = ? AND ");
        }
        statement.append("SELECT * FROM ");
        statement.append(table);
        statement.append(" WHERE ");
        statement.append(builderValues.toString().replaceAll(" AND $", " "));
        return this;
    }


    public int insert(Connection connection, String table, Map<String,Object> values) {
        sqlType = SqlType.INSERT;
        StringBuilder builderFields = new StringBuilder("VALUES (");
        StringBuilder builderValues = new StringBuilder("(");
        StringBuilder builder = new StringBuilder();
        for (String key: values.keySet()) {
            this.values.add(values.get(key));
            builderFields.append(key);
            builderValues.append("?, ");
        }
        statement.append("INSERT INTO ");
        statement.append(table);
        statement.append(" ");
        statement.append(builderFields.toString().replaceAll(", ", ") "));
        statement.append(" VALUES ");
        statement.append(builderValues.toString().replaceAll(", ", ") "));
        return execute(connection);
    }

    public int update(Connection connection, String table, Map<String,Object> values) {
        sqlType = SqlType.UPDATE;
        StringBuilder builderValues = new StringBuilder("");
        for (String key: values.keySet()) {
            this.values.add(values.get(key));
            builderValues.append(key + " = ?, ");
        }
        statement.append("UPDATE ");
        statement.append(table);
        statement.append(" SET ");
        statement.append(builderValues.toString().replaceAll(", $", " "));
        if (values.get(ID)!=null) {
            this.values.add(values.get(ID));
            statement.append(" WHERE id = ? ");
        }
        else if (values.get(NATURAL_ID)!=null) {
            this.values.add(values.get(NATURAL_ID));
            statement.append(" WHERE naturalId = ? ");
        }
        else {
            throw new EoInternalException("no id nor naturalid provided.");
        }
        return execute(connection);
    }

    protected List<String> initMetaData(ResultSet resultSet) throws SQLException {
        List<String> metaDataNames = new ArrayList<>();
        for (int i = 1; i < resultSet.getMetaData().getColumnCount(); i++) {
            metaDataNames.add(resultSet.getMetaData().getColumnName(i));
        }
        return metaDataNames;
    }

    protected static List createRow(final ResultSet resultSet) throws SQLException {
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


    protected static final void closeStatement(Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            statement.close();
        } catch (Exception e) {
            statement = null;
            new EoInternalException("Exception closing statement : " + e.getMessage());
        }
    }

    protected static final void closeResultSet(ResultSet resultSet) {
        if (resultSet == null) {
            return;
        }
        try {
            resultSet.close();
        } catch (Exception e) {
            resultSet = null;
            throw new EoInternalException("Exception closing resultSet: " + e.getMessage());
        }
    }

    protected static final void closeAll(Statement statement, ResultSet resultSet) {
        closeResultSet(resultSet);
        closeStatement(statement);
    }
    private enum SqlType {
        UPDATE, INSERT, FIND, NONE;
    }

}
