package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionProperties;
import org.fluentcodes.projects.elasticobjects.models.ConfigProperties;

import java.net.URL;

/**
 * Created by Werner on 09.10.2016.
 */
public interface FileConfigInterface extends PermissionProperties, ConfigProperties {
    String TEMPLATE = "template";
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

    default Boolean hasTemplate() {
        return isTemplate() != null;
    }
    default Boolean isTemplate() {
        return (Boolean) getProperties().get(TEMPLATE);
    }
    default FileConfigInterface getTemplate(Boolean template) {
        getProperties().put(TEMPLATE, template);
        return this;
    }

}
