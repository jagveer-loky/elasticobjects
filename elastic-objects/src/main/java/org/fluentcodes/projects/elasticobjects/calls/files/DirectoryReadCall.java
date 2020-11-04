package org.fluentcodes.projects.elasticobjects.calls.files;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.ResourceReadCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.tools.xpect.IORuntimeException;
import org.fluentcodes.tools.xpect.IOString;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;

/**
 * Created by werner.diwischek on 2.10.2020.
 */
public class DirectoryReadCall extends ResourceReadCall {
    private String fileName;
    public DirectoryReadCall() {
        super();
    }

    public DirectoryReadCall(final String configKey) {
        super(configKey);
    }

    public DirectoryConfig getDirectoryConfig()  {
        return ((DirectoryConfig) getConfig());
    }

    public boolean hasFileName() {
        return fileName != null && !fileName.isEmpty();
    }

    public String getFileName() {
        return fileName;
    }

    public DirectoryReadCall setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }

    @Override
    public String execute(final EO eo)  {
        if (!init(eo)) {
            return "";
        }
        String result = read(eo);
        return createReturnString(eo, result);
    }

    public String read(final EO eo)  {
        if (!hasFileName()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }
        if (fileName.contains("..")) {
            throw new EoException("FileName in call for read '"+ fileName + "' has some backPropagation!");
        }
        if (!fileName.matches(getDirectoryConfig().getFileName())) {
            throw new EoException("fileName in call for read '"+ fileName + "' does not match fileName in  DirectoryConfig '" + getFileName() + "'.");
        }
        if (getDirectoryConfig().hasCachedContent(fileName)) {
            return getDirectoryConfig().getCachedContent(fileName);
        }
        String content = FileReadCall.read(eo, getDirectoryConfig().getFilePath() + "/" + fileName);
        if (getDirectoryConfig().isCached()) {
            getDirectoryConfig().setCachedContent(fileName, content);
        }
        return content;
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

}
