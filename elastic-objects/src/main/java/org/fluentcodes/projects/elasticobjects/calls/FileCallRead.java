package org.fluentcodes.projects.elasticobjects.calls;


import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.FileConfig;
import org.fluentcodes.projects.elasticobjects.config.Permissions;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileCallRead extends ResourceCall<String> {

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


    public FileConfig getFileConfig()  {
        return ((FileConfig) getConfig());
    }

    public String execute() { return execute(null);}

    @Override
    public String execute(final EO eo)  {
        return getFileConfig().createIO().read();
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }
}
