package org.fluentcodes.projects.elasticobjects.calls.files;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.tools.xpect.IOBytes;
import org.fluentcodes.tools.xpect.IOString;

import java.util.HashMap;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileReadCall extends CallResource{

    public FileReadCall() {
        super(PermissionType.READ);
    }

    public FileReadCall(final String configKey) {
        super(PermissionType.READ);
        setConfigKey(configKey);
    }

    public FileReadCall setConfigKey(final String configKey) {
        return (FileReadCall) super.setConfigKey(configKey);
    }


    public FileConfig getFileConfig()  {
        return ((FileConfig) getConfig());
    }

    @Override
    public Object execute(final EO eo)  {
        init(eo);
        String result = (String) getFileConfig().read();
        return createReturnString(eo, result);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

    public String read()  {
        if (!hasConfig()) {
            throw new EoException("No config defined for configKey " + getConfigKey());
        }
        if (!getFileConfig().hasFilePath()) {
            throw new EoException("No filePath in config defined for configKey " + getConfigKey());
        }
        if (!getFileConfig().hasFileName()) {
            throw new EoException("No fileName in config defined for configKey " + getConfigKey());
        }
        return (String)getFileConfig().read();
    }

    public static String read(final String filePath, final String fileName)  {
        if (filePath==null || filePath.isEmpty()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }
        if (fileName==null || fileName.isEmpty()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }
        String fileAndPath = replace(filePath + Path.DELIMITER + fileName);

        return new IOString().setFileName(fileAndPath).read();
    }

    public static byte[] readBinary(final String filePath, final String fileName)  {
        if (filePath==null || filePath.isEmpty()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }
        if (fileName==null || fileName.isEmpty()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }
        return new IOBytes().setFileName(filePath + Path.DELIMITER + fileName).read();
    }
}
