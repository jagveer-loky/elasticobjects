package org.fluentcodes.projects.elasticobjects.calls.db;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.tools.xpect.IOString;

import java.io.IOException;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.*;

import static org.fluentcodes.projects.elasticobjects.DB_EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_HOST_KEY;

/**
 * Created by Werner on 09.10.2016.
 */
public class DbConfig extends ConfigResourcesImpl {
    private static transient final Logger LOG = LogManager.getLogger(DbConfig.class);

    private final String schema;
    private final String driver;
    private final String jndi;
    private final DbTypes dbType;
    private final String hostKey;
    private final String extension;
    private Connection connection;
    private HostConfig hostConfig;


    public DbConfig(final EOConfigsCache provider, final Builder builder)  {
        super(provider, builder);
        this.schema = builder.schema;
        this.driver = builder.driver;
        this.jndi = builder.jndi;
        this.dbType = builder.dbType;
        this.hostKey = builder.hostKey;
        this.extension = builder.extension;
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


    public static class Builder extends ConfigResourcesImpl.Builder {
        private String schema;
        private String driver;
        private String jndi;
        private DbTypes dbType;
        private String hostKey;
        private String extension;


        protected void prepare(EOConfigsCache configsCache, Map<String, Object> values)  {
            hostKey = (String) configsCache.transform(F_HOST_KEY, values);
            schema = (String) configsCache.transform(F_SCHEMA, values);
            driver = (String) configsCache.transform(F_DRIVER, values);
            jndi = (String) configsCache.transform(F_JNDI, values);
            extension = (String) configsCache.transform(F_EXTENSION, values);
            dbType = (DbTypes) configsCache.transform(F_DB_TYPE, values);
            super.prepare(configsCache, values);
            if (hostKey == null || hostKey.isEmpty()) {
                hostKey = getNaturalId().replaceAll(":[^:]+$", "");
            }

        }

        public Config build(EOConfigsCache configsCache, Map<String, Object> values)  {
            prepare(configsCache, values);
            return new DbConfig(configsCache, this);
        }


    }

}
