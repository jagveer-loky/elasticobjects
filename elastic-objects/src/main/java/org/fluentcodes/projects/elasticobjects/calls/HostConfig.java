package org.fluentcodes.projects.elasticobjects.calls;

import com.sun.corba.se.spi.ior.IdentifiableFactory;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

import java.util.Map;
/*=>{javaHeader}|*/
/**
 * 
 * Basic host definition for file or db cache.  
 * @author null
 * @creationDate Wed Oct 17 00:00:00 CEST 2018
 * @modificationDate Thu Jan 14 12:17:41 CET 2021
 */
public class HostConfig extends PermissionConfig implements HostInterface  {
/*=>{}.*/
    public static final String LOCALHOST = "localhost";

/*=>{javaAccessors}|*/
/*=>{}.*/
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
