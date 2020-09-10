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
    private final String hostKey;
    private final String extension;
    private Connection connection;

    public DbConfig(final EOConfigsCache provider, final Map map)  {
        super(provider, map);
        hostKey = (String) map.get(HOST_KEY);
        schema = (String) map.get(SCHEMA);
        driver = (String) map.get(DRIVER);
        jndi = (String) map.get(JNDI);
        extension = (String) map.get(EXTENSION);
        dbType = (DbTypes) map.get(DB_TYPE);
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

    /**
     * A key for host objects.
     */
    public String getHostKey() {
        return this.hostKey;
    }

    /**
     * The field for hostConfig e.g. defined in {@link HostConfig}
     */
    public HostConfig getHostConfig()  {
        if (this.hostConfig == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'hostConfig' - 'hostKey''!");
            }
            this.hostConfig = (HostConfig) getConfigsCache().find(HostConfig.class, hostKey);
        }
        return this.hostConfig;
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
            throw new EoException(e);
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
        connection = DriverManager.getConnection(getUrlPath(), getHostConfig().getUser(), getHostConfig().getPassword());
        initDb();
        return connection;
    }

    private void initDb() throws IOException, SQLException {
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources("data/" + getKey() + ".sql");
        List<URL> urlList = Collections.list(urls);
        for (URL url : urlList) {
            LOG.info("Add  from " + url.getFile());
            String sql = new IOString().setFileName(url.getFile()).read();
            DbIO dbIO = new DbIO(this);
            dbIO.execute(Arrays.asList(sql.split(";\n")));
        }
        LOG.info("Initialized db");
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
        if (getHostConfig() == null) {
            throw new EoException("Incomplete directory exception: hostConfig not addList!");
        }
        String urlPath = hostConfig.getUrlPath();

        if (schema != null && !schema.isEmpty()) {
            urlPath += ":" + schema;
        }
        if (extension != null && !extension.isEmpty()) {
            urlPath += ";" + extension;
        }
        return urlPath;
        //TODO
        //return ReplaceUtil.replace(urlPath);
    }
}
