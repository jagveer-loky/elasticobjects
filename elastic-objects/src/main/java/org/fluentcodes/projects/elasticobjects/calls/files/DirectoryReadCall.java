package org.fluentcodes.projects.elasticobjects.calls.files;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Created by werner.diwischek on 2.10.2020.
 */
public class DirectoryReadCall extends FileReadCall{
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
        init(eo);
        if (!hasFileName()) {
            throw new EoException("No fileName is set for DirectoryReadCall with config '" + getConfigKey() + "'.");
        }
        String result = (String) getDirectoryConfig().read(getFileName());
        return createReturnString(eo, result);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }

}
