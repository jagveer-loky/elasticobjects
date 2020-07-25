package org.fluentcodes.projects.elasticobjects.calls.file;


import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.Permissions;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileCallRead extends CallResource<String> {

    public FileCallRead() {
        super(Permissions.READ);
    }

    public FileCallRead(final String configKey) {
        super(Permissions.READ);
        setConfigKey(configKey);
    }


    public FileCallRead(final EOConfigsCache cache, final String configKey) {
        super(Permissions.READ);
        setConfigKey(configKey);
    }

    public FileCallRead setConfigKey(final String configKey) {
        return (FileCallRead) super.setConfigKey(configKey);
    }


    public ConfigResourcesFile getFileConfig()  {
        return ((ConfigResourcesFile) getConfig());
    }

    public String execute() { return execute(null);}

    @Override
    public String execute(final EO eo)  {
        return getFileConfig().createIO().read();
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return ConfigResourcesFile.class;
    }
}
