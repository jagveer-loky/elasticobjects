package org.fluentcodes.projects.elasticobjects.calls.files;


import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.calls.CallResource;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Created by werner.diwischek on 9.7.2020.
 */
public class FileWriteCall extends CallResource {

    public FileWriteCall() {
        super(PermissionType.WRITE);
    }

    public FileWriteCall(final String configKey) {
        super(PermissionType.READ);
        setConfigKey(configKey);
    }

    public FileConfig getFileConfig()  {
        return ((FileConfig) getConfig());
    }

    @Override
    public String execute(final EO eo)  {
        resolve(eo.getConfigsCache());
        hasPermissions(eo.getRoles());
        String result = null;
        if (eo.isScalar()) {
            result = eo.get().toString();
        }
        else {
            result = new EOToJSON().toJSON(eo);
        }
        getFileConfig().write(result);
        return result;
    }

    @Override
    public Class<? extends Config> getConfigClass()  {
        return FileConfig.class;
    }
}
