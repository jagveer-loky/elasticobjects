package org.fluentcodes.projects.elasticobjects.calls.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.tools.xpect.IOString;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.calls.HostConfig.HOST_KEY;

/**
 * Created by Werner on 09.10.2016.
 */
public class DbConfig extends HostConfig {
    public static final String DB_KEY = "dbKey";
    public static final String SCHEMA = "schema";
    public static final String DRIVER = "driver";
    public static final String JNDI = "jndi";
    public static final String DB_TYPE = "dbType";
    public static final String EXTENSION = "extension";
    private static transient final Logger LOG = LogManager.getLogger(DbConfig.class);

    private final String schema;
    private final String driver;
    private final String jndi;
    private final DbTypes dbType;
    private final String extension;
    private Connection connection;

    public DbConfig(final EOConfigsCache provider, final Map map)  {
        super(provider, map);
        schema = (String) map.get(SCHEMA);
        driver = (String) map.get(DRIVER);
        jndi = (String) map.get(JNDI);
        extension = (String) map.get(EXTENSION);
        dbType = map.containsKey(DB_TYPE) ? DbTypes.valueOf((String) map.get(DB_TYPE)): DbTypes.UNDEFINED;
    }

    /**
     * The database schema used for persisting. {@link HostConfig}
     */
    public String getSchema() {
        return this.schema;
    }

    public String getExtension() {
        return extension;
    }

    /**
     * Mainly the schema
     */
    public String getDriver() {
        return this.driver;
    }

    /**
     * Mainly the schema
     */
    public String getJndi() {
        return this.jndi;
    }

    /**
     * Mainly the schema
     */
    public DbTypes getDbType() {
        return this.dbType;
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
            throw new EoException("Problem in " + getUrlPath() + " to execute " + sql ,e);
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

    public Connection getConnection() throws ClassNotFoundException, SQLException, IOException {
        if (this.connection != null) {
            return this.connection;
        }
        if (dbType == null) {
            throw new EoException("No driver is addList for '" + getKey() + "'.");
        }

        if (dbType.getDriver() == null) {
            throw new EoException("No driver is addList for '" + getKey() + "'.");
        }
        Class.forName(dbType.getDriver());
        System.out.println(getUrlPath());
        connection = DriverManager.getConnection(getUrlPath(), getUser(), getPassword());
        return connection;
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

    public boolean hasExtension() {
        return extension != null && !extension.isEmpty();
    }

    public boolean hasSchema() {
        return schema != null && !schema.isEmpty();
    }

    public String getUrlPath()  {
        StringBuffer urlPath = new StringBuffer();
        if (hasProtocol()) {
            urlPath.append(getProtocol());
        }
        if (hasSchema()) {
            urlPath.append(":");
            urlPath.append(schema);
        }
        if (hasExtension()) {
            urlPath.append( ";");
            urlPath.append(extension);
        }
        return urlPath.toString();
    }
}
