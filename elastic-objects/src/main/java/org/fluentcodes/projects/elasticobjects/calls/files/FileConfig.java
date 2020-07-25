package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigResourcesImpl;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

/**
 * Created by Werner on 09.10.2016.
 */
public class ConfigResourcesFile extends ConfigResourcesImpl {
    public static final String CLASSPATH = "classpath";
    private final String fileName;
    private final String filePath;
    private final String fileKey;
    private final String hostKey;
    private final Boolean cached;
    private ConfigResourcesHost hostCache;

    private String cachedContent;

    public ConfigResourcesFile(final EOConfigsCache provider, Builder builder) {
        super(provider, builder);
        this.fileName = builder.fileName;
        this.filePath = builder.filePath;
        this.fileKey = builder.fileKey;
        this.cached = builder.cached;
        this.hostKey = builder.hostKey;


        if (builder.isExpanded()) {
            this.hostCache = new ConfigResourcesHost(provider, builder);
        }
    }

    public FileIO createIO() {
        return new FileIO(this);
    }

    @Override
    public String getKey() {
        return fileKey;
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
        return ReplaceUtil.replace(hostPath + "" + filePath + "/" + fileName);
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
        if (ConfigResourcesFile.CLASSPATH.equals(filePath)) {
            return this.findUrl(this.fileName);
        } else if (filePath.startsWith(ConfigResourcesFile.CLASSPATH)) {
            return this.findUrl(filePath.replace(ConfigResourcesFile.CLASSPATH, "") + "/" + this.fileName);
        } else {
            String urlString = getUrlPath();
            try {
                return new URL(ReplaceUtil.replace(urlString, new HashMap<>()));
            } catch (MalformedURLException e) {
                throw new EoException(e);
            }
        }
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
     * A key for file objects.
     */
    public String getFileKey() {
        return this.fileKey;
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
     * The field for hostCache e.g. defined in {@link ConfigResourcesFile}
     */
    public ConfigResourcesHost getHostConfig()  {
        if (this.hostCache == null) {
            if (this.getConfigsCache() == null) {
                throw new EoException("Config could not be initialized with a null provider for 'hostCache' - 'hostKey''!");
            }
            this.hostCache = (ConfigResourcesHost) getConfigsCache().find(ConfigResourcesHost.class, hostKey);
        }
        return this.hostCache;
    }

    /**
     * If true will config the readed file within the config object.
     */
    public Boolean isCached() {
        return this.cached;
    }

    public static class Builder extends ConfigResourcesHost.Builder {
        public static final String ADAPTER_PATH = "content";
        private String fileName;
        private String filePath;
        private String fileKey;
        private Boolean cached;
        private String hostKey;

        public Builder() {

        }

        protected void prepare(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            super.prepare(configsCache, values);
            fileKey = (String) configsCache.transform(F_FILE_KEY, values);
            if (fileKey == null) {
                throw new EoException("fileKey is not add!");
            }
            fileName = (String) configsCache.transform(F_FILE_NAME, values, fileKey);
            filePath = (String) configsCache.transform(F_FILE_PATH, values, ConfigResourcesFile.CLASSPATH);
            cached = (Boolean) configsCache.transform(F_CACHED, values, false);
            hostKey = (String) configsCache.transform(F_HOST_KEY, values, H_LOCALHOST);
        }

        @Override
        public Config build(final EOConfigsCache configsCache, final Map<String, Object> values)  {
            prepare(configsCache, values);
            return new ConfigResourcesFile(configsCache, this);
        }
    }
}
