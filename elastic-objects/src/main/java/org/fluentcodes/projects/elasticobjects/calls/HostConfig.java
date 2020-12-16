package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.calls.files.FileBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class HostConfig extends PermissionConfig implements HostConfigInterface, HostConfigInterfaceMethods {
    public static final String LOCALHOST = "localhost";
    public static final String HOST_KEY = "hostKey";
    private String urlCache;

    public HostConfig(final Map map) {
        this(new HostBean(map));
    }
    public HostConfig(ConfigBean bean) {
        this((HostBean) bean);
    }
    public HostConfig(final HostBean host) {
        super(host);
    }

    protected boolean hasUrlCache() {
        return urlCache!=null && !urlCache.isEmpty();
    }
    public String getUrlCache() {
        return urlCache;
    }
    protected void setUrlCache(final String urlCache) {
        if (hasUrlCache()) {
            return;
        }
        this.urlCache = urlCache;
    }

    protected String createUrl() {
        if (!hasHostName()) {
            return getProtocol();
        }
        else if (LOCALHOST.equals(getHostName())) {
            urlCache = "file:";
        }
        else  {
            if (hasPort()) {
                urlCache = getProtocol() + "://" + getHostName() + ":" + getPort();
            }
            else {
                urlCache = getProtocol() + "://" + getHostName();
            }
        }
        return urlCache;
    }

    public String getUrlPath()  {
        if (hasUrlCache()) {
            return urlCache;
        }
        if (hasUrl()) {
            this.urlCache = getUrl();
            return urlCache;
        }
        if (getHostName() == null) {
            throw new EoException("Incomplete host exception: host name not add!");
        }
        return createUrl();
    }

    @Override
    public String toString() {
        return getNaturalId() + " -> " + getUrl();
    }

}
