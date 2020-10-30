package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModuleProperties;

import java.util.List;

/**
 * Created by Werner on 10.10.2016.
 */
public interface ResourceProperties extends ModuleProperties {
    EOConfigsCache getConfigsCache();

    PermissionRole getPermissionRole();
    PermissionType getPermissions(String... roleKeys);
    PermissionType getPermissions(List<String> roleKeys) ;
    boolean hasPermissions(PermissionType permission, List<String> roleKeys) ;
}
