package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResources;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;

import java.net.URL;

/**
 * Created by Werner on 09.10.2016.
 */
public interface FileConfigInterface extends ConfigResources {
    Object read();
    void write(Object content);
    Boolean isCached();

    String getCachedContent();
    void setCachedContent(String cachedContent);

    String getUrlPath();
    URL findUrl() ;
    URL getUrl();
    URL createUrl();

    String getFileName();
    String getFilePath();
    boolean hasFileName();
    String getHostKey();
    HostConfig getHostConfig();

}
