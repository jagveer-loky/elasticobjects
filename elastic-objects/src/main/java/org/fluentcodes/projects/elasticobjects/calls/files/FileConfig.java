package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserTemplate;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;
import org.fluentcodes.tools.xpect.IOString;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.HostConfig.HOST_KEY;

/**
 * Created by Werner on 09.10.2016.
 */
public class FileConfig extends ConfigResourcesImpl implements FileConfigInterface {
    public static final String FILE_NAME = "fileName";
    public static final String FILE_PATH = "filePath";
    public static final String CACHED = "cached";

    private final String fileName;
    private final String filePath;
    private final String hostKey;
    private final Boolean cached;
    private HostConfig hostCache;

    private String cachedContent;

    public FileConfig(final EOConfigsCache provider, Map map) {
        super(provider, map);
        this.fileName = (String) map.get(FILE_NAME);
        this.filePath = (String) map.get(FILE_PATH);
        this.cached = map.containsKey(CACHED) ? ScalarConverter.toBoolean(CACHED) : false;
        this.hostKey = map.containsKey(HOST_KEY) ? (String) map.get(HOST_KEY) : HostConfig.LOCALHOST;
        this.hostCache = (HostConfig)provider.find(HostConfig.class, hostKey);
    }

    protected boolean hasCachedContent() {
        return cachedContent != null && !cachedContent.isEmpty();
    }
    @Override
    public String getCachedContent() {
        return cachedContent;
    }
    @Override
    public void setCachedContent(String cachedContent) {
        this.cachedContent = cachedContent;
    }
    @Override
    public String getUrlPath()  {
        String hostPath = getHostConfig().getUrlPath();
        if (hostPath == null || hostPath.isEmpty()) {
            return filePath + "/" + fileName;
        }
        return new ParserTemplate(hostPath + "" + filePath + "/" + fileName).parse();
    }

    @Override
    public URL findUrl()  {
        URL url = createUrl();
        final String localFileName = url.toString().replaceAll("^file:","");
        if (new File(localFileName).exists()) {
            return url;
        }
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(localFileName);
            while (urls.hasMoreElements()) {
                return urls.nextElement();
            }
            throw new EoException("Could not find " + fileName.replaceAll("^/+", ""));
        }
        catch (Exception e) {
            throw new EoException("Could not find " + fileName.replaceAll("^/+", ""), e);
        }
    }
    @Override
    public URL getUrl()  {
        if (!hasFileName()) {
            throw new EoException("No name in file provided '" + getNaturalId() + "'!");
        }
        URL url = createUrl();
        String urlString = getUrlPath();
        try {
            String replaceUrl = new ParserTemplate(urlString).parse();
            return new URL(replaceUrl);
        } catch (MalformedURLException e) {
            throw new EoException(e);
        }
    }
    @Override
    public URL createUrl()  {
        if (fileName == null || fileName.equals("")) {
            throw new EoException("No name in file provided '" + getNaturalId() + "'!");
        }
        if (filePath == null || filePath.equals("")) {
            throw new EoException("No path in file provided '" + getNaturalId() + "'!");
        }
        String urlString = getUrlPath();
        try {
            String replaceUrl = new ParserTemplate(urlString).parse();
            return new URL(replaceUrl);
        } catch (MalformedURLException e) {
            throw new EoException(e);
        }
    }
    @Override
    public Object read()  {
        if (hasCachedContent()) {
            return getCachedContent();
        }
        URL url = createUrl();
        String content = null;
        try {
            if (url.toString().startsWith("file:")) {
                content = new IOString().setFileName(url.getFile()).read();
            } else {
                throw new EoException("Only local files are implemented!");
            }
            if (isCached()) {
                setCachedContent(content);
            }
            return content;
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    /**
     * Write the file direct without the usage of
     *
     * @param content
     * @
     */
    @Override
    public void write(Object content)  {
        if (isCached()) {
            throw new EoException("A fileCached file could not persisted!");
        }
        URL url = createUrl();
        new IOString().setFileName(url.getFile()).write((String)content);
    }

    /**
     * File cached
     */
    @Override
    public String getFileName() {
        return this.fileName;
    }
    @Override
    public String getFilePath() {
        return this.filePath;
    }
    @Override
    public boolean hasFileName() {
        return fileName != null && !fileName.isEmpty();
    }

    /**
     * A key for host objects.
     */
    @Override
    public String getHostKey() {
        return this.hostKey;
    }

    /**
     * File cached
     */

    /**
     * The field for hostCache e.g. defined in {@link FileConfig}
     */
    @Override
    public HostConfig getHostConfig()  {
        if (this.hostCache == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'hostCache' - 'hostKey''!");
            }
            this.hostCache = (HostConfig) getConfigsCache().find(HostConfig.class, hostKey);
        }
        return this.hostCache;
    }

    /**
     * If true will config the readed file within the config object.
     */
    @Override
    public Boolean isCached() {
        return this.cached;
    }
}
