package org.fluentcodes.projects.elasticobjects.calls;


import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.config.FileConfig;
import org.fluentcodes.projects.elasticobjects.config.Permissions;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileCallWrite extends ResourceCall<Boolean>{

    public FileCallWrite() {
        super(Permissions.WRITE);
    }

    public FileCallWrite(final String configKey) {
        super(Permissions.READ);
        setConfigKey(configKey);
    }

    public FileConfig getFileConfig()  {
        return ((FileConfig) getConfig());
    }

    @Override
    public Boolean execute(final EO eo)  {
        getFileConfig().createIO().write(eo.get());
        return true;
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }
}
