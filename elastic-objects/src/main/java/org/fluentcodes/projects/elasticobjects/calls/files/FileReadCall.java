package org.fluentcodes.projects.elasticobjects.calls.files;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.calls.ResourceReadCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.tools.xpect.IOBytes;
import org.fluentcodes.tools.xpect.IOString;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileReadCall extends ResourceReadCall {

    public FileReadCall() {
        super();
    }

    public FileReadCall(final String configKey) {
        super();
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
        if (!init(eo)) {
            return null;
        }
        if (getFileConfig().hasCachedContent()) {
            return getFileConfig().getCachedContent();
        }
        String filePath = getFileConfig().getFilePath() + "/" + getFileConfig().getFileName();
        String result = read(eo, filePath);
        if (getFileConfig().isCached()) {
            getFileConfig().setCachedContent(result);
        }
        return createReturnString(eo, result);
    }

    public static String read(final EO eo, String filePath)  {
        filePath = ParserSqareBracket.replacePathValues(filePath, eo);
        return new IOString().setFileName(filePath).read();
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
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
