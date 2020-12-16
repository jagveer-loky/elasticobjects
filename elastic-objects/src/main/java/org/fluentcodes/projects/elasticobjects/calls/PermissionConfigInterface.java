package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;

import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 */
public interface PermissionConfigInterface extends ConfigConfigInterface {
    PermissionRole getRolePermissions();
    default boolean hasPermissionRole() {
        return getRolePermissions() != null;
    }
}
