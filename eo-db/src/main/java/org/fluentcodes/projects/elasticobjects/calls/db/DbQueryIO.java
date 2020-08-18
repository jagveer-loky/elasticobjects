package org.fluentcodes.projects.elasticobjects.calls.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.calls.lists.ListParams;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Werner on 09.10.2016.
 */
public class DbQueryIO {
    private static transient final Logger LOG = LogManager.getLogger(DbQueryIO.class);
    private DbQueryConfig dbConfig;
    private Statement statement;
    private String sql;
    private ResultSet resultSet;
    private ResultSetMetaData metaData;
    private int colCount = -1;

    protected DbQueryIO(DbQueryConfig config)  {
        this.dbConfig = config;
        this.sql = config.getSql();
        try {
            this.statement = config.getDbConfig().getConnection().createStatement();
            this.resultSet = statement.executeQuery(this.sql);
            this.metaData = resultSet.getMetaData();
            this.colCount = metaData.getColumnCount();
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    public Object read() {
        return null;
    }


    public List read(ListParams listParams) {
        return null;
    }

    public void write(Object write) {
    }

    public List readRow() throws SQLException {
        if (resultSet.next()) {
            return DbIO.createRow(resultSet);
        } else {
            return null;
        }
    }

    protected int getColCount() {
        return colCount;
    }

    public List readHead(int rownum) throws SQLException {
        List<String> result = new ArrayList<>();
        for (int i = 1; i < colCount + 1; i++) {
            result.add(metaData.getColumnName(i));
        }
        return result;
    }

    public List readRow(int i) throws SQLException {
        for (int j = 0; j < i; i++) {
            resultSet.next();
        }
        resultSet.next();
        return readRow();
    }

    public void write(List rows)  {

    }

    public void reset() throws SQLException {
        resultSet.first();
    }

    public void close() throws SQLException {
        resultSet.close();
        statement.close();
        statement = null;
    }
}
