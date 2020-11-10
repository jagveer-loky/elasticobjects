package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.commands.ConfigDirectoryReadCommand;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;

/**
 * Created by werner.diwischek on 2.10.2020.
 */
public class DirectoryReadCall extends FileReadCall {
    private String fileName;
    public DirectoryReadCall() {
        super();
    }

    public DirectoryReadCall(final String configKey) {
        super(configKey);
    }

    public DirectoryReadCall(final String configKey, final String fileName) {
        super(configKey);
        this.fileName = fileName;
    }


    @Override
    public String execute(final EO eo)  {
        String result = read(eo);
        return createReturnString(eo, result);
    }

    public String read(final EO eo)  {
        if (!hasFileName()) {
            throw new EoException("No fileName provided for DirectoryConfig read.");
        }

        DirectoryConfig config = (DirectoryConfig)init(PermissionType.READ, eo);
        if (config.hasCachedContent(fileName)) {
            return config.getCachedContent(fileName);
        }
        final String replaceFileName = ParserSqareBracket.replacePathValues(fileName, eo);
        if (replaceFileName.contains("..")) {
            throw new EoException("FileName in call for read '"+ replaceFileName + "' has some backPropagation!");
        }
        if (!replaceFileName.matches(config.getFileName())) {
            throw new EoException("fileName in call for read '"+ replaceFileName + "' does not match fileName in  DirectoryConfig '" + getFileName() + "'.");
        }

        final String filePath = ParserSqareBracket.replacePathValues(config.getFilePath() + "/" + replaceFileName, eo);
        final String content = new IOString().setFileName(filePath).read();
        if (config.isCached()) {
            config.setCachedContent(fileName, content);
        }
        return content;
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

}
