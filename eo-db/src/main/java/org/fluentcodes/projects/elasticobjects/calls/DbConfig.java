package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Werner on 09.10.2016.
 */
public class DbConfig extends HostConfig implements DbInterface {
    public static final String H2_BASIC = "h2:mem:basic";
    private Connection connection;
    public DbConfig(final ConfigBean configBean, final ConfigMaps configMaps) {
        this((HostBean)configBean, configMaps);
    }
    public DbConfig(final HostBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
    }

    public static final void closeStatement(Statement statement) {
        if (statement == null) {
            return;
        }
        try {
            statement.close();
        } catch (Exception e) {
            new EoInternalException("Exception closing statement : " + e.getMessage());
        }
    }

    public static final void closeResultSet(ResultSet resultSet) {
        if (resultSet == null) {
            return;
        }
        try {
            resultSet.close();
        } catch (Exception e) {
            throw new EoInternalException("Exception closing resultSet: " + e.getMessage());
        }
    }

    public static final void closeAll(Statement statement, ResultSet resultSet) {
        closeResultSet(resultSet);
        closeStatement(statement);
    }

    public Connection getConnection() {
        if (this.connection != null) {
            try {
                if (!connection.isClosed()) {
                    return this.connection;
                }
            } catch (SQLException e) {
                throw new EoInternalException(e);
            }
        }
        if (!hasDbType()) {
            throw new EoException("No dbType defined, so no driver could be set '" + getNaturalId() + "'.");
        }
        if (getDbType().getDriver() == null) {
            throw new EoException("No driver is addList for '" + getNaturalId() + "'.");
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
            throw new EoException("No connection initialized for '" + getNaturalId() + "'.");
        }
        try {
            connection.close();
        } catch (SQLException e) {
            throw new EoException(e);
        }
        connection = null;
    }

    @Override
    public String getUrlPath()  {
        if (hasUrlCache()) {
            return getUrlCache();
        }
        if (hasUrlCache()) {
            super.getUrl();
        }
        StringBuilder urlPath = new StringBuilder();
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
        setUrlCache(urlPath.toString());
        return getUrlCache();
    }
}
