package org.fluentcodes.projects.elasticobjects.calls.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

/**
 * Created by Werner on 09.10.2016.
 */
public class DbConfig extends HostConfig implements PropertiesDbAccessor{
    public static final String DB_KEY = "dbKey";
    public static final String H2_BASIC = "h2:mem:basic";
    private Connection connection;

    public DbConfig(final EOConfigsCache provider, final Map map)  {
        super(provider, map);
    }

    public boolean read(EO eo) {
        return false;
    }

    public boolean write(EO eo) {
        return false;
    }


    public boolean execute(String sql) {
        Statement stmt = null;
        try {
            stmt = this.getConnection().createStatement();
            return stmt.execute(sql);
        } catch (Exception e) {
            throw new EoInternalException("Problem in " + getUrlPath() + " to execute " + sql ,e);
        } finally {
            if (stmt != null) {
                try {
                    stmt.close();
                } catch (SQLException throwables) {
                    throw new EoException(throwables);
                }
            }
        }
    }

    public Connection getConnection() {
        if (this.connection != null) {
            return this.connection;
        }
        if (!hasDbType()) {
            throw new EoException("No dbType defined, so no driver could be set '" + getKey() + "'.");
        }
        if (getDbType().getDriver() == null) {
            throw new EoException("No driver is addList for '" + getKey() + "'.");
        }
        try {
            Class.forName(getDbType().getDriver());
            String url = getUrlPath();
            connection = DriverManager.getConnection(url, getUser(), getPassword());
            return connection;
        }
        catch (ClassNotFoundException|SQLException e) {
            throw new EoException(e);
        }
    }

    public void closeConnection()  {
        if (connection == null) {
            throw new EoException("No connection initialized for '" + getKey() + "'.");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new EoException(e);
        }
        connection = null;
    }

    public String getUrlPath()  {
        StringBuffer urlPath = new StringBuffer();
        if (hasProtocol()) {
            urlPath.append(getProtocol());
        }
        if (hasHostName()) {
            urlPath.append(":");
            urlPath.append(getHostName());
        }
        if (hasSchema()) {
            urlPath.append(":");
            urlPath.append(getSchema());
        }
        if (hasExtension()) {
            urlPath.append( ";");
            urlPath.append(getExtension());
        }
        return urlPath.toString();
    }
}
