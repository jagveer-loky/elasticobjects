package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.PermissionProperties;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;

import java.net.URL;

/**
 * Created by Werner on 09.10.2016.
 */
public interface FileConfigInterface extends PermissionProperties {
    Boolean isCached();
    String getFileName();
    String getFilePath();
    String getCachedContent();
    void setCachedContent(String cachedContent);

    String getUrlPath();
    URL findUrl() ;
    URL getUrl();
    URL createUrl();

    boolean hasFileName();
    String getHostConfigKey();
    HostConfig getHostConfig();

}
