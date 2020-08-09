package org.fluentcodes.projects.elasticobjects.calls.files;


import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileCallRead extends CallResource<String> {

    public FileCallRead() {
        super(PermissionType.READ);
    }

    public FileCallRead(final String configKey) {
        super(PermissionType.READ);
        setConfigKey(configKey);
    }

    public FileCallRead setConfigKey(final String configKey) {
        return (FileCallRead) super.setConfigKey(configKey);
    }


    public FileConfig getFileConfig()  {
        return ((FileConfig) getConfig());
    }

    @Override
    public String execute(final EO eo)  {
        init(eo);
        return getFileConfig().read();
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }
}
