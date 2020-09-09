package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResources;
import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserTemplate;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.tools.xpect.IOString;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 09.10.2016.
 */
public interface FileConfigInterface extends ConfigResources {
    Object read();
    void write(Object content);
    Boolean isCached();

    boolean hasCachedContent();
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
