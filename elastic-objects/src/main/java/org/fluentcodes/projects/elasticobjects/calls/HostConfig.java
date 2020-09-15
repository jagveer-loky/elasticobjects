package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

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
        this.port = map.containsKey(PORT) ? ScalarConverter.toInt(map.get(PORT)) : null;
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
    public boolean hasPort() {
        return port!=null && port>-1;
    }

    /**
     * Basic host definition for file or db config.
     */
    public String getProtocol() {
        return this.protocol;
    }
    public boolean hasProtocol() {
        return protocol!=null && !protocol.isEmpty();
    }
    /**
     * A name for host objects.
     */
    public String getHostName() {
        return this.hostName;
    }
    public boolean hasHostName() {
        return hostName!=null && !hostName.isEmpty();
    }
    /**
     * Basic host definition for file or db config.
     */
    public String getUser() {
        return this.user;
    }
    public boolean hasUser() {
        return user!=null && !user.isEmpty();
    }
    /**
     * Basic host definition for file or db config.
     */
    public boolean hasPassword() {
        return password!=null && !password.isEmpty();
    }
    public String getPassword() {
        return this.password;
    }

    public String getUrlPath()  {
        if (hostName == null) {
            throw new EoException("Incomplete host exception: host name not add!");
        }
        if ("".equals(hostName)) {
            return protocol;
        } else if (!LOCALHOST.equals(hostName)) {
            if (hasPort()) {
                return protocol + "://" + hostName + ":" + port;
            }
            return protocol + "://" + hostName;
        } else {
            return "file:";
        }
    }

}
