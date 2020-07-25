package org.fluentcodes.projects.elasticobjects.calls.file;


import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.config.Config;
import org.fluentcodes.projects.elasticobjects.config.Permissions;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileCallWrite extends CallResource<Boolean> {

    public FileCallWrite() {
        super(Permissions.WRITE);
    }

    public FileCallWrite(final String configKey) {
        super(Permissions.READ);
        setConfigKey(configKey);
    }

    public ConfigResourcesFile getFileConfig()  {
        return ((ConfigResourcesFile) getConfig());
    }

    @Override
    public Boolean execute(final EO eo)  {
        getFileConfig().createIO().write(eo.get());
        return true;
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return ConfigResourcesFile.class;
    }
}
