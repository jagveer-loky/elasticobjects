package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 */
public interface PermissionProperties extends Config {
    EOConfigsCache getConfigsCache();

    PermissionRole getPermissionRole();
    default PermissionType getPermissions(String... roleKeys)  {
        return getPermissionRole().getPermissions(Arrays.asList(roleKeys));
    }
    default PermissionType getPermissions(List<String> roleKeys)  {
        return getPermissionRole().getPermissions(roleKeys);
    }

    default boolean hasPermissions(PermissionType callPermission, List<String> roleKeys)  {
        int rolePermission = getPermissionRole().getPermissions(roleKeys).value();
        if (callPermission.value() > rolePermission) {
            throw new EoException("No permissions for roles " + roleKeys + ": " + callPermission.name() + "(" + callPermission.value() + ")");
        }
        return true;
    }
}
