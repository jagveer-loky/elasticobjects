package org.fluentcodes.projects.elasticobjects.calls.db.statements;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class PreparedStatementValues {
    private SqlType sqlType = SqlType.NONE;
    private final List values;
    private final StringBuilder statement = new StringBuilder();
    public PreparedStatementValues() {
        values = new ArrayList();

    }
    public PreparedStatementValues(final SqlType sqlType) {
        this();
        this.sqlType = sqlType;
    }

    protected void add(Object object) {
        values.add(object);
    }

    protected void resolve(final PreparedStatement preparedStatement) {
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

    public String getStatement() {
        return statement.toString();
    }

    public void append(String add) {
        statement.append(add);
    }

    protected int countParams() {
        return statement.toString().replaceAll("[^\\?]","").length();
    }

    protected void checkHealth() {
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

    public int execute (Connection connection) {
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
                else if (sqlType.equals(SqlType.DELETE)) {
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
    protected enum SqlType {
        UPDATE, INSERT, FIND, DELETE, NONE;
    }

}
