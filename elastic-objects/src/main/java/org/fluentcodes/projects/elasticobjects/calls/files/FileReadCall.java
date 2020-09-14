package org.fluentcodes.projects.elasticobjects.calls.files;


import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileReadCall extends CallResource{

    public FileReadCall() {
        super(PermissionType.READ);
    }

    public FileReadCall(final String configKey) {
        super(PermissionType.READ);
        setConfigKey(configKey);
    }

    public FileReadCall setConfigKey(final String configKey) {
        return (FileReadCall) super.setConfigKey(configKey);
    }


    public FileConfig getFileConfig()  {
        return ((FileConfig) getConfig());
    }

    @Override
    public String execute(final EO eo)  {
        init(eo);
        String result = (String) getFileConfig().read();
        return createReturnString(eo, result);
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }
}
