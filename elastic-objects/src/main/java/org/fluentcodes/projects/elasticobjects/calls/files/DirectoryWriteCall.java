package org.fluentcodes.projects.elasticobjects.calls.files;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.ResourceWriteCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserSqareBracket;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

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

    public DirectoryConfig getDirectoryConfig()  {
        return ((DirectoryConfig) getConfig());
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

    @Override
    public String execute(final EO eo)  {
        if (!init(eo)) {
            return "";
        }
        return write(eo);
    }

    public String write(final EO eo)  {
        if (!hasFileName()) {
            throw new EoException("No fileName is set for " + this.getClass().getSimpleName() + " with config '" + getConfigKey() + "'.");
        }
        if (fileName.contains("..")) {
            throw new EoException("FileName in call for read '"+ fileName + "' has some backPropagation!");
        }
        if (!fileName.matches(getDirectoryConfig().getFileName())) {
            throw new EoException("FileName in call for read '"+ fileName + "' does not match fileName in  DirectoryConfig '" + getFileName() + "'.");
        }

        String url = getDirectoryConfig().getFilePath() + "/" + fileName;
        if (ParserSqareBracket.containsStartSequence(url)) {
            url = new ParserSqareBracket(url).parse(eo);
        }
        FileWriteCall.write(url, getContent());
        return "Written content with  length " + getContent().length() + " to file '" + url + "'" ;
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

}
