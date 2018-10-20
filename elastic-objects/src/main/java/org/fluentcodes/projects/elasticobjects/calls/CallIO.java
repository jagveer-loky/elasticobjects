package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.config.ConfigIO;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.RolePermissions;

/**
 * Created by werner.diwischek on 03.12.16.
 */
public class CallIO extends Call {
    private RolePermissions rolePermissions;

    public CallIO(EOConfigsCache provider, String cacheKey) throws Exception {
        super(provider, cacheKey);
        if (getAssetConfig().getRolePermissions() != null) {
            rolePermissions = getAssetConfig().getRolePermissions();
        } else {
            rolePermissions = new RolePermissions();
        }
    }

    public CallIO(EOConfigsCache provider) throws Exception {
        super(provider);
        rolePermissions = new RolePermissions();
    }

    public ConfigIO getAssetConfig() {
        return (ConfigIO) getConfig();
    }

    public RolePermissions getRolePermissions() {
        return rolePermissions;
    }

    public void setRolePermissions(final RolePermissions rolePermissions) {
        this.rolePermissions = rolePermissions;
    }
}
