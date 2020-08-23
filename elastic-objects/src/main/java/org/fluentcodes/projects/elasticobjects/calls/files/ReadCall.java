package org.fluentcodes.projects.elasticobjects.calls.files;


import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.models.Config;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class ReadCall extends CallResource<String> {
    private String configType;
    private String configKey;
    public ReadCall() {
        super(PermissionType.READ);
    }

    public ReadCall(final String configKey) {
        super(PermissionType.READ);
        setConfigKey(configKey);
    }

    public ReadCall setConfigKey(final String configKey) {
        return (ReadCall) super.setConfigKey(configKey);
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
