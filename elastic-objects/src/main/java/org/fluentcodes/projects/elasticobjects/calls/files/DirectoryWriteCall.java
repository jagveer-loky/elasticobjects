package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.tools.xpect.IOString;

/**
 * Created by werner.diwischek on 2.10.2020.
 */
public class DirectoryWriteCall extends FileWriteCall {
    private String fileName;
    public DirectoryWriteCall() {
        super();
    }

    public DirectoryWriteCall(final String configKey) {
        super(configKey);
    }

    @Override
    public String execute(final EO eo)  {
        if (!init(eo)) {
            return "";
        }
        return write(eo);
    }

    public String write(final EO eo)  {
        if (!hasFileName()) {
            throw new EoException("No fileName is set for " + this.getClass().getSimpleName() + " with config '" + getFileConfigKey() + "'.");
        }
        if (fileName.contains("..")) {
            throw new EoException("FileName in call for write '"+ fileName + "' has some backPropagation!");
        }
        DirectoryConfig directoryConfig = (DirectoryConfig)init(PermissionType.READ, eo);
        if (!fileName.matches(directoryConfig.getFileName())) {
            throw new EoException("FileName in call for read '"+ fileName + "' does not match fileName in  DirectoryConfig '" + getFileName() + "'.");
        }

        String url = directoryConfig.getFilePath() + "/" + fileName;
        if (ParserSqareBracket.containsStartSequence(url)) {
            url = new ParserSqareBracket(url).parse(eo);
        }
        new IOString().setFileName(url).write(getContent());
        return "Written content with  length " + getContent().length() + " to file '" + url + "'" ;
    }

    public boolean hasFileName() {
        return fileName != null && !fileName.isEmpty();
    }

    public String getFileName() {
        return fileName;
    }

    public DirectoryWriteCall setFileName(String fileName) {
        this.fileName = fileName;
        return this;
    }
}
