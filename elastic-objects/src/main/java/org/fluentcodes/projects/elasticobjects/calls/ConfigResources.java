package org.fluentcodes.projects.elasticobjects.calls;

import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 */
public interface ConfigResources {

    RolePermissions getRolePermissions();

    PermissionType getPermissions(String... roleKeys);

    PermissionType getPermissions(List<String> roleKeys) ;

    boolean hasPermissions(PermissionType permission, List<String> roleKeys) ;
}
