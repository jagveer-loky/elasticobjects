package org.fluentcodes.projects.elasticobjects.calls;


import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.FileConfig;
import org.fluentcodes.projects.elasticobjects.config.HostConfig;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.utils.FileUtil;
import org.fluentcodes.projects.elasticobjects.utils.ReplaceUtil;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.io.File;
import java.net.URL;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class FileCall extends HostCall {
    private String filePath;

    public FileCall(EOConfigsCache eoConfigsCache, String fileKey) throws Exception {
        super(eoConfigsCache, fileKey);
    }

    public FileConfig getFileConfig() throws Exception {
        return ((FileConfig) getConfig());
    }

    @Override
    public HostConfig getHostConfig() throws Exception {
        return getFileConfig().getHostConfig();
    }

    public void mapAttributes(final Map attributes) {
        if (attributes == null) {
            return;
        }
        super.mapAttributes(attributes);
        setFilePath(attributes.get(F_FILE_PATH));
    }


    public void mergeConfig() {
        super.mergeConfig();
        try {
            setFilePath(getFileConfig().getFilePath());
        } catch (Exception e) {
            e.printStackTrace();
        }
        //this.fileName = setFilePath(getFileConfig().getFileName());

    }


    public String getFilePath() {
        return filePath;
    }

    public FileCall setFilePath(Object entry) {
        if (entry == null) {
            return this;
        }
        if (this.filePath != null && !this.filePath.isEmpty()) {
            return this;
        }
        this.filePath = ScalarConverter.toString(entry);
        return this;
    }

    public boolean hasFilePath() {
        return this.filePath != null && !filePath.isEmpty();
    }

    public URL findUrl(String fileName) throws Exception {
        Enumeration<URL> urls = Thread.currentThread().getContextClassLoader().getResources(fileName.replaceAll("^/+", ""));
        while (urls.hasMoreElements()) {
            return urls.nextElement();
        }
        throw new Exception("Could not find " + fileName.replaceAll("^/+", ""));
    }

    public URL getUrl(EO adapter, Map attributes) {
        try {
            if (getFileConfig().getFilePath().startsWith(FileConfig.CLASSPATH)) {
                String fileName = ReplaceUtil.replace(getFileConfig().getFilePath() + "/" + getFileConfig().getFileName(), adapter);
                fileName = fileName.replace(FileConfig.CLASSPATH, "");
                fileName = fileName.replaceAll("^/+", "");
                return findUrl(fileName);
            } else {
                String urlString = ReplaceUtil.replace(getUrlPath(), adapter, attributes);
                return new URL(ReplaceUtil.replace(urlString, new HashMap<>()));
            }
        } catch (Exception e) {
            adapter.error("Problem resolving url: " + e.getMessage());
            return null;
        }

    }

    public String getUrlPath() throws Exception {
        return getFileConfig().getUrlPath();
    }

    public URL getUrl() throws Exception {
        return getFileConfig().getUrl();
    }

    public File getFile() throws Exception {
        return new File(getUrl().getFile());
    }

    public boolean exists() throws Exception {
        return getFile().exists();
    }

    public String write(final EO adapter) throws Exception {
        return write(adapter, new HashMap());
    }

    public String write(final EO adapter, final Map attributes) throws Exception {
        if (adapter == null) {
            throw new Exception("Write only works with a non null adapter!");
        }
        mapAttributes(attributes);
        mergeConfig();
        Object content = null;
        try {
            content = adapter.get(getMapPath());
        } catch (Exception e) {
            e.printStackTrace();
            adapter.warn("Could not find entry for " + getMapPath());
            return "";
        }
        if (content == null) {
            adapter.warn("Null value in  " + getMapPath());
            return "";
        }
        return write(adapter, attributes, content);
    }

    public String write(final EO adapter, final Map attributes, Object content) throws Exception {
        if (adapter == null) {
            throw new Exception("Write only works with a non null adapter!");
        }
        if (content == null) {
            adapter.error("No content defined for " + getFileConfig().getFileKey());
            return "";
        }
        if (!getFileConfig().hasFileName()) {
            adapter.error("No content defined for " + getFileConfig().getFileKey());
            return "";
        }
        try {
            URL url = getUrl(adapter, attributes);
            String contentAsString = ScalarConverter.toString(content);
            getFileConfig().write(url, contentAsString);
        } catch (Exception e) {
            adapter.error("Could not write to file" + e.getMessage());
            return "";
        }
        return "";
    }

    public String read(final EO adapter) throws Exception {
        return read(adapter, new HashMap());
    }

    public String read(final EO adapter, final Map attributes) throws Exception {
        if (adapter == null) {
            throw new Exception("Read only works with a non null adapter!");
        }
        mapAttributes(attributes);
        mergeConfig();
        try {
            URL url = getUrl(adapter, attributes);
            String content = FileUtil.readFile(url);
            if (hasMapPath() && this.getClass() == FileCall.class) {
                EO child = setToPath(adapter, new LinkedHashMap<>());
                child
                        .add(getMapPath())
                        .set(content);
            }
            return content;
        } catch (Exception e) {
            adapter.error("Could not set from file" + e.getMessage());
            return "";
        }
    }

    public String read() throws Exception {
        return getFileConfig().createIO().read();
    }

    /**
     * Write the file direct without the usage of
     *
     * @param content
     * @throws Exception
     */
    public void write(final String content) throws Exception {
        getFileConfig().createIO().write(content);
    }

}
