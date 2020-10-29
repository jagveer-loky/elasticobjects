package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/**
 * Reads and writes database tables.
 * Created by werner.diwischek on 29.10.20.
 */
public abstract class DbModelCall extends DbCall  {
    private String modelKey;
    private ModelConfig modelConfig;

    public DbModelCall(PermissionType permission)  {
        super(permission);
    }

    public DbModelCall(PermissionType permission, final String hostConfigKey)  {
        super(permission, hostConfigKey);
    }

    public DbModelCall(PermissionType permission, final String hostConfigKey, final String modelKey)  {
        super(permission, hostConfigKey);
        this.modelKey = modelKey;
    }

    @Override
    public boolean init (EO eo) {
        if (!hasModelKey()) {
            throw new EoException("No modelKey is set!");
        }
        super.init(eo);
        this.modelConfig = eo.getConfigsCache().findModel(modelKey);
        return true;
    }

    public String getModelKey() {
        return modelKey;
    }

    public void setModelKey(String modelKey) {
        this.modelKey = modelKey;
    }

    public boolean hasModelKey() {
        return modelKey != null && !modelKey.isEmpty();
    }

    public ModelConfig getModelConfig() {
        return modelConfig;
    }
}
