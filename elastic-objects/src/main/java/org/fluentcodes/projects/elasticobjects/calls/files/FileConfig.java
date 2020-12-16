package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.calls.PermissionConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserCurlyBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

import java.io.File;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Enumeration;
import java.util.Map;

/**
 * Created by Werner on 09.10.2016.
 */
public class FileConfig extends PermissionConfig implements FileConfigInterfaceMethods {
    public static final String FILE_NAME = "fileName";
    public static final String FILE_PATH = "filePath";
    public static final String CACHED = "cached";

    private final String fileName;
    private final String filePath;
    private final String hostConfigKey;
    private final Boolean cached;

    private String cachedContent;

    public FileConfig(Map map) {
        this(new FileBean(map));
    }

    public FileConfig(ConfigBean bean) {
        this((FileBean) bean);
    }

    public FileConfig(FileBean bean) {
        super(bean);
        this.fileName = bean.getFileName();
        this.filePath = bean.getFilePath();
        this.cached = bean.getCached();
        this.hostConfigKey = bean.getHostConfigKey();
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
    public String getUrlPath(final HostConfig hostConfig )  {
        String hostPath = hostConfig.getUrlPath();
        if (hostPath == null || hostPath.isEmpty()) {
            return filePath + "/" + fileName;
        }
        return new ParserCurlyBracket(hostPath + "" + filePath + "/" + fileName).parse();
    }

    protected HostConfig resolveHostConfig(final EO eo, final String hostConfigKey) {
        if (hostConfigKey == null||hostConfigKey.isEmpty()) {
            if (hasHostConfigKey()) {
                return eo.getConfigsCache().findHost(getHostConfigKey());
            }
            throw new EoException("No default host key is set. No hostKey provided.");
        }
        return eo.getConfigsCache().findHost(hostConfigKey);
    }

    public URL findUrl(final EO eo, final String hostConfigKey)  {
        return findUrl(resolveHostConfig(eo, hostConfigKey));
    }

    @Override
    public URL findUrl(HostConfig hostConfig)  {
        URL url = createUrl(hostConfig);
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
    public URL getUrl(HostConfig hostConfig)  {
        if (!hasFileName()) {
            throw new EoException("No name in file provided '" + getNaturalId() + "'!");
        }
        URL url = createUrl(hostConfig);
        String urlString = getUrlPath(hostConfig);
        try {
            String replaceUrl = new ParserCurlyBracket(urlString).parse();
            return new URL(replaceUrl);
        } catch (MalformedURLException e) {
            throw new EoException(e);
        }
    }
    @Override
    public URL createUrl(HostConfig hostConfig)  {
        if (fileName == null || fileName.equals("")) {
            throw new EoException("No name in file provided '" + getNaturalId() + "'!");
        }
        if (filePath == null || filePath.equals("")) {
            throw new EoException("No path in file provided '" + getNaturalId() + "'!");
        }
        String urlString = getUrlPath(hostConfig);
        try {
            String replaceUrl = new ParserCurlyBracket(urlString).parse();
            return new URL(replaceUrl);
        } catch (MalformedURLException e) {
            throw new EoException(e);
        }
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
    @Override
    public boolean hasFilePath() {
        return filePath != null && !filePath.isEmpty();
    }

    /**
     * A key for host objects.
     */
    @Override
    public String getHostConfigKey() {
        return this.hostConfigKey;
    }

    /**
     * If true will config the readed file within the config object.
     */
    @Override
    public Boolean getCached() {
        return this.cached;
    }
}
