package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigImpl;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ConfigResourcesImpl extends ConfigImpl {
    public static final String ROLE_PERMISSIONS = "rolePermissions";
    private final RolePermissions rolePermissions;

    public ConfigResourcesImpl(EOConfigsCache configsCache, Map map) {
        super(configsCache, map);
        this.rolePermissions = new RolePermissions((Map)map.get(ROLE_PERMISSIONS));
    }

    public RolePermissions getRolePermissions() {
        return rolePermissions;
    }

    public PermissionType getPermissions(String... roleKeys)  {
        return rolePermissions.getPermissions(Arrays.asList(roleKeys));
    }

    public PermissionType getPermissions(List<String> roleKeys)  {
        return rolePermissions.getPermissions(roleKeys);
    }

    public boolean hasPermissions(PermissionType permission, List<String> roleKeys)  {
        return permission.value() <= rolePermissions.getPermissions(roleKeys).value();
    }
}
