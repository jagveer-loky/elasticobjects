package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class DbIO {
    private DbConfig dbConfig;

    public DbIO(DbConfig config)  {
        this.dbConfig = config;
    }

    protected static List createRow(final ResultSet resultSet) throws SQLException {
        List row = new ArrayList();
        ResultSetMetaData metaData = resultSet.getMetaData();
        if (resultSet.isAfterLast()) {
            return null;
        }
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

    protected static Map<String, Object> createRowAsMap(ResultSet resultSet) throws SQLException {
        Map<String, Object> row = new LinkedHashMap<>();
        ResultSetMetaData metaData = resultSet.getMetaData();
        if (resultSet.isAfterLast()) {
            return null;
        }
        for (int i = 1; i < metaData.getColumnCount(); i++) {
            switch (metaData.getColumnTypeName(i)) {
                case "VARCHAR":
                    row.put(metaData.getColumnName(i), resultSet.getString(i));
                    break;
                case "INTEGER":
                    row.put(metaData.getColumnName(i), resultSet.getInt(i));
                    break;
                case "BOOLEAN":
                    row.put(metaData.getColumnName(i), resultSet.getBoolean(i));
                    break;
                case "DOUBLE":
                    row.put(metaData.getColumnName(i), resultSet.getDouble(i));
                    break;
                case "DATE":
                    row.put(metaData.getColumnName(i), resultSet.getDate(i));
                    break;
                case "BIGINT":
                    row.put(metaData.getColumnName(i), resultSet.getLong(i));
                    break;
            }
        }
        return row;
    }

    public boolean execute(List<String> sqlList) throws SQLException {
        boolean execution = false;
        for (String sql : sqlList) {
            execution = execute(sql) || execution;
        }
        return execution;
    }

    public boolean execute(String sql) throws SQLException {
        Statement stmt = null;
        try {
            stmt = dbConfig.getConnection().createStatement();
            boolean execution = stmt.execute(sql);
            return execution;
        } catch (Exception e) {
            throw new EoException(e);
        } finally {
            if (stmt != null) {
                stmt.close();
            }
        }
    }

    public List readAsList(String sql) throws SQLException, IOException, ClassNotFoundException {
        Statement stmt = null;
        ResultSet resultSet = null;
        List result = new ArrayList<>();
        try {
            stmt = dbConfig.getConnection().createStatement();
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                result.add(createRow(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            resultSet.close();
            stmt.close();
        }
        return result;
    }

    public List readListMap(String sql) throws SQLException {
        Statement stmt = null;
        ResultSet resultSet = null;
        List<Map<String, Object>> result = new ArrayList<>();
        try {
            stmt = dbConfig.getConnection().createStatement();
            resultSet = stmt.executeQuery(sql);

            while (resultSet.next()) {
                result.add(createRowAsMap(resultSet));
            }
        } catch (Exception e) {
            e.printStackTrace();
            throw new EoException(e);
        } finally {
            resultSet.close();
            stmt.close();
        }
        return result;
    }
}
