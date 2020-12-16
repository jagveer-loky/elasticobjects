package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;

/**
 * Created by Werner on 10.12.2020.
 */
public interface Permission extends PermissionConfigInterface {
    Permission setRolePermissions(PermissionRole permissionRole);
    default void mergePermission(Object value) {
        if (value == null) {
            return;
        }
        if (hasPermissionRole()) {
            return;
        }
        if (value instanceof Map) {
            setRolePermissions(new PermissionRole((Map) value));
        }
        else if (value instanceof PermissionRole) {
            setRolePermissions(new PermissionRole((PermissionRole) value));
        }
        else {
            throw new EoException("Unsupported type for permissionRole '" + value.getClass().getSimpleName() + "'");
        }
    }
}
