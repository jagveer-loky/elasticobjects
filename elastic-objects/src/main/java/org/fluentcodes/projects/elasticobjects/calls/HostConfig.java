package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class HostConfig extends ConfigResourcesImpl implements PropertiesHostAccessor {
    public static final String LOCALHOST = "localhost";
    public static final String HOST_KEY = "hostKey";

    public HostConfig(final EOConfigsCache provider, final Map map) {
        super(provider, map);
    }

      public String getUrlPath()  {
        if (getHostName() == null) {
            throw new EoException("Incomplete host exception: host name not add!");
        }
        if ("".equals(getHostName())) {
            return getProtocol();
        } else if (!LOCALHOST.equals(getHostName())) {
            if (hasPort()) {
                return getProtocol() + "://" + getHostName() + ":" + getPort();
            }
            return getProtocol() + "://" + getHostName();
        } else {
            return "file:";
        }
    }

}
