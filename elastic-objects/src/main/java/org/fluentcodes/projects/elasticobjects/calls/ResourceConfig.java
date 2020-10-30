package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigImpl;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ResourceConfig extends ConfigImpl implements ResourceProperties {
    public static final String ROLE_PERMISSIONS = "rolePermissions";
    private final PermissionRole permissionRole;

    public ResourceConfig(EOConfigsCache configsCache, Map map) {
        super(configsCache, map);
        this.permissionRole = new PermissionRole((Map)map.get(ROLE_PERMISSIONS));
    }

    public PermissionRole getPermissionRole() {
        return permissionRole;
    }

    public PermissionType getPermissions(String... roleKeys)  {
        return permissionRole.getPermissions(Arrays.asList(roleKeys));
    }

    public PermissionType getPermissions(List<String> roleKeys)  {
        return permissionRole.getPermissions(roleKeys);
    }

    public boolean hasPermissions(PermissionType permission, List<String> roleKeys)  {
        return permission.value() <= permissionRole.getPermissions(roleKeys).value();
    }
}
