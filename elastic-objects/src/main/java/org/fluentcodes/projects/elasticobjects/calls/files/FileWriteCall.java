package org.fluentcodes.projects.elasticobjects.calls.files;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.tools.xpect.IOString;

import java.net.URL;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileWriteCall extends CallResource {
    private String classPath;
    public FileWriteCall() {
        super(PermissionType.WRITE);
    }

    public FileWriteCall(final String configKey) {
        super(PermissionType.READ);
        setConfigKey(configKey);
    }

    public FileConfig getFileConfig()  {
        return ((FileConfig) getConfig());
    }

    @Override
    public String execute(final EO eo)  {
        init(eo);
        hasPermissions(eo.getRoles());
        String content = null;
        if (eo.isScalar()) {
            content = eo.get().toString();
        }
        else {
            content = new EOToJSON().toJSON(eo);
        }
        write(eo.getConfigsCache(), content);
        return content;
    }

    public void write(EOConfigsCache configsCache, Object content)  {
        resolve(configsCache);
        String url = getFileConfig().createUrl().getFile();
        if (hasClassPath()) {
            url = getClassPath() + Path.DELIMITER + url;
        }
        write(url, content);
    }

    public static void write(String targetFile, Object content)  {
        new IOString().setFileName(targetFile).write((String)content);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

    public boolean hasClassPath() {
        return classPath!=null && !classPath.isEmpty();
    }

    public String getClassPath() {
        return classPath;
    }

    public void setClassPath(String classPath) {
        this.classPath = classPath;
    }
}
