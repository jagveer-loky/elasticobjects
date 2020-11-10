package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.HostCall;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigDbObject;

/**
 * Reads and writes database tables.
 * Created by werner.diwischek on 29.10.20.
 */
public abstract class DbModelCall extends HostCall {
    private String modelConfigKey;
    private ModelConfigDbObject modelConfigDbObject;
    public DbModelCall()  {
        super();
    }

    public DbModelCall(final String hostConfigKey)  {
        super(hostConfigKey);
    }

    public void setConfigKey(final String configKey) {
        this.modelConfigKey = configKey;
    }

    protected ModelConfigDbObject init(final PermissionType permissionType, final EO eo) {
        modelConfigKey = eo.getModelClass().getSimpleName();
        modelConfigDbObject = (ModelConfigDbObject) eo.getConfigsCache().findModel(modelConfigKey);
        modelConfigDbObject.hasPermissions(permissionType, eo.getRoles());
        if (!hasHostConfigKey()) {
            if (modelConfigDbObject.hasHostConfigKey()) {
                setHostConfigKey(modelConfigDbObject.getHostConfigKey());
            }
            else {
                setHostConfigKey(DbConfig.H2_BASIC);
            }
        }
        super.initHostConfig(permissionType, eo);
        return modelConfigDbObject;
    }

    protected DbConfig getDbConfig() {
        return (DbConfig)getHostConfig();
    }

    public boolean hasModelConfigKey() {
        return modelConfigKey != null && !modelConfigKey.isEmpty();
    }

    public String getModelConfigKey() {
        return modelConfigKey;
    }

    public void setModelConfigKey(String modelConfigKey) {
        this.modelConfigKey = modelConfigKey;
    }
}
