package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 09.10.2016.
 */
public class ConfigResourcesHost extends ConfigResourcesImpl {
    private final String hostKey;
    private final Integer port;
    private final String protocol;
    private final String hostName;
    private final String user;
    private final String password;

    protected ConfigResourcesHost(final EOConfigsCache provider, final Builder builder) {
        super(provider, builder);
        this.hostKey = builder.hostKey;
        this.port = builder.port;
        this.protocol = builder.protocol;
        this.hostName = builder.hostName;
        this.user = builder.user;
        this.password = builder.password;
    }

    @Override
    public String getKey() {
        return hostKey;
    }

    /**
     * A key for host objects.
     */
    public String getHostKey() {
        return this.hostKey;
    }

    /**
     * Basic host definition for file or db config.
     */
    public Integer getPort() {
        return this.port;
    }

    /**
     * Basic host definition for file or db config.
     */
    public String getProtocol() {
        return this.protocol;
    }

    /**
     * A name for host objects.
     */
    public String getHostName() {
        return this.hostName;
    }

    /**
     * Basic host definition for file or db config.
     */
    public String getUser() {
        return this.user;
    }

    /**
     * Basic host definition for file or db config.
     */
    public String getPassword() {
        return this.password;
    }

    public String getUrlPath()  {
        if (hostName == null) {
            throw new EoException("Incomplete host exception: host name not add!");
        }
        if ("".equals(hostName)) {
            return protocol;
        } else if (!H_LOCALHOST.equals(hostName)) {
            return protocol + "://" + hostName + ":" + port;
        } else {
            return "file:";
        }
    }

    public static class Builder extends ConfigResourcesImpl.Builder {
        //<call keep="JAVA" templateKey="BeanInstanceVars.tpl">
        private String hostKey;
        private Integer port;
        private String protocol;
        private String hostName;
        private String user;
        private String password;

        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            super.prepare(configsCache, values);
            hostKey = (String) configsCache.transform(F_HOST_KEY, values);
            protocol = (String) configsCache.transform(F_PROTOCOL, values);
            hostName = (String) configsCache.transform(F_HOST_NAME, values);
            user = (String) configsCache.transform(F_USER, values);
            password = (String) configsCache.transform(F_PASSWORD, values);
            port = (Integer) configsCache.transform(F_PORT, values);
            String fileKey = (String) configsCache.transform(F_FILE_KEY, values);
            if (hostKey == null) {
                if (fileKey != null && fileKey.contains(":")) {
                    hostKey = fileKey.replaceAll(":[^:]+$", "");
                } else {
                    hostKey = H_LOCALHOST;
                }
            }
            if (hostName == null) {
                hostName = "";
            }
        }

        protected String getHostKey() {
            return this.hostKey;
        }

        public Config build(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            prepare(configsCache, values);
            return new ConfigResourcesHost(configsCache, this);
        }
    }
}
