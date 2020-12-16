package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ConfigProperties;

import java.net.URL;

/**
 * Created by Werner on 09.12.2020.
 */
public interface FileConfigInterfaceMethods extends FileConfigInterface {
    String getCachedContent();
    void setCachedContent(String cachedContent);

    String getUrlPath(HostConfig hostConfig);
    URL findUrl(HostConfig hostConfig) ;
    URL getUrl(HostConfig hostConfig);
    URL createUrl(HostConfig hostConfig);

}
