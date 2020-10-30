package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.PermissionType;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;

/**
 * Reads and writes database tables.
 * Created by werner.diwischek on 29.10.20.
 */
public abstract class DbModelCall extends DbCall  {
    public DbModelCall(PermissionType permission)  {
        super(permission);
    }

    public DbModelCall(PermissionType permission, final String hostConfigKey)  {
        super(permission, hostConfigKey);
    }

    @Override
    public boolean init (EO eo) {
        ModelConfigInterface modelConfig = eo.getModel();
        if (!modelConfig.isObject()) {
            throw new EoException("ModelConfig for " + modelConfig.getModelKey() + " is not an object!");
        }
        super.init(eo);
        return true;
    }
}
