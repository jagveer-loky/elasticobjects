package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Arrays;
import java.util.List;

/**
 * Created by Werner on 10.12.2020.
 */
public interface PermissionConfigInterfaceMethods extends PermissionConfigInterface {
    default PermissionType getPermissions(String... roleKeys)  {
        return getRolePermissions().getPermissions(Arrays.asList(roleKeys));
    }
    default PermissionType getPermissions(List<String> roleKeys)  {
        return getRolePermissions().getPermissions(roleKeys);
    }

    default boolean hasPermissions(PermissionType callPermission, List<String> roleKeys)  {
        int rolePermission = getRolePermissions().getPermissions(roleKeys).value();
        if (callPermission.value() > rolePermission) {
            throw new EoException("No permissions for roles " + roleKeys + ": " + callPermission.name() + "(" + callPermission.value() + ")");
        }
        return true;
    }
}
