package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 09.10.2016.
 */
public class HostConfig extends ConfigResourcesImpl {
    public static final String LOCALHOST = "localhost";
    public static final String PORT = "port";
    public static final String PROTOCOL = "protocol";
    public static final String PASSWORD = "password";
    public static final String HOST_NAME = "hostName";
    public static final String USER = "user";
    public static final String HOST_KEY = "hostKey";
    private final Integer port;
    private final String protocol;
    private final String hostName;
    private final String user;
    private final String password;

    public HostConfig(final EOConfigsCache provider, final Map map) {
        super(provider, map);
        this.port = (Integer) map.get(PORT);
        this.protocol = (String) map.get(PROTOCOL);
        this.hostName = (String) map.get(HOST_NAME);
        this.user = (String) map.get(USER);
        this.password = (String) map.get(PASSWORD);
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

}
