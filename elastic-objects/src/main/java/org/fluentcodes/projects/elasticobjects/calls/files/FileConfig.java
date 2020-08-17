package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.calls.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserTemplate;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 09.10.2016.
 */
public class FileConfig extends ConfigResourcesImpl {
    public static final String CLASSPATH = "classpath";
    private final String fileName;
    private final String filePath;
    private final String hostKey;
    private final Boolean cached;
    private HostConfig hostCache;

    private String cachedContent;

    public FileConfig(final EOConfigsCache provider, Builder builder) {
        super(provider, builder);
        this.fileName = builder.fileName;
        this.filePath = builder.filePath;
        this.cached = builder.cached;
        this.hostKey = builder.hostKey;
        if (builder.isExpanded()) {
            this.hostCache = new HostConfig(provider, builder);
        }
    }

    public boolean hasCachedContent() {
        return cachedContent != null && !cachedContent.isEmpty();
    }

    public String getCachedContent() {
        return cachedContent;
    }

    public void setCachedContent(String cachedContent) {
        this.cachedContent = cachedContent;
    }

    public String getUrlPath()  {
        String hostPath = getHostConfig().getUrlPath();
        if (hostPath == null || hostPath.isEmpty()) {
            return filePath + "/" + fileName;
        }
        return new ParserTemplate(hostPath + "" + filePath + "/" + fileName).parse();
    }

    public URL findUrl(String fileName)  {
        try {
            Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(fileName.replaceAll("^/+", ""));
            while (urls.hasMoreElements()) {
                return urls.nextElement();
            }
            throw new EoException("Could not find " + fileName.replaceAll("^/+", ""));
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    public URL getUrl()  {
        if (fileName == null || fileName.equals("")) {
            throw new EoException("No name in file provided!");
        }
        if (FileConfig.CLASSPATH.equals(filePath)) {
            return this.findUrl(this.fileName);
        } else if (filePath.startsWith(FileConfig.CLASSPATH)) {
            return this.findUrl(filePath.replace(FileConfig.CLASSPATH, "") + "/" + this.fileName);
        } else {
            String urlString = getUrlPath();
            try {
                return new URL(new ParserTemplate(urlString).parse());
            } catch (MalformedURLException e) {
                throw new EoException(e);
            }
        }
    }

    public String read()  {
        if (hasCachedContent()) {
            return getCachedContent();
        }
        URL url = getUrl();
        String content = new IOString().setFileName(url.getFile()).read();
        if (isCached()) {
            setCachedContent(content);
        }
        return content;
    }

    /**
     * Write the file direct without the usage of
     *
     * @param content
     * @
     */
    public void write(String content)  {
        if (isCached()) {
            throw new EoException("A fileCached file could not persisted!");
        }
        URL url = getUrl();
        File filePath = new File(url.getPath());
        if (!filePath.getParentFile().exists()) {
            filePath.getParentFile().mkdirs();
        }
        new IOString().setFileName(url.getFile()).write(content);
    }

    public void write(URL url, String content)  {
        BufferedWriter out = null;
        try {
            out = new BufferedWriter(new FileWriter(url.getFile()));
            out.write(content);
        }
        catch (Exception e) {
            throw new EoException(e);
        }
        finally {
            if (out != null) {
                try {
                    out.close();
                } catch (IOException e) {
                    throw new EoException(e);
                }
            }
        }
    }

    /**
     * File cached
     */
    public String getFileName() {
        return this.fileName;
    }

    public String getFilePath() {
        return this.filePath;
    }

    public boolean hasFileName() {
        return fileName != null && !fileName.isEmpty();
    }

    /**
     * A key for host objects.
     */
    public String getHostKey() {
        return this.hostKey;
    }

    /**
     * File cached
     */

    /**
     * The field for hostCache e.g. defined in {@link FileConfig}
     */
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
    public Boolean isCached() {
        return this.cached;
    }

    public static class Builder extends HostConfig.Builder {
        public static final String ADAPTER_PATH = "content";
        private String fileName;
        private String filePath;
        private Boolean cached;
        private String hostKey;

        public Builder() {
        }

        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            super.prepare(configsCache, values);
            fileName = (String) configsCache.transform(F_FILE_NAME, values);
            if (fileName == null) {
                fileName = (String) configsCache.transform(NATURAL_ID, values);
            }
            filePath = (String) configsCache.transform(F_FILE_PATH, values, FileConfig.CLASSPATH);
            cached = (Boolean) configsCache.transform(F_CACHED, values, false);
            hostKey = (String) configsCache.transform(F_HOST_KEY, values, H_LOCALHOST);
        }

        @Override
        public Config build(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            prepare(configsCache, values);
            return new FileConfig(configsCache, this);
        }
    }
}
