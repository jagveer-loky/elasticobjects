package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigImpl;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;

import java.util.Map;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class ResourceConfig extends ConfigImpl implements PermissionProperties {
    public static final String ROLE_PERMISSIONS = "rolePermissions";
    private final PermissionRole permissionRole;

    public ResourceConfig(EOConfigsCache configsCache, Map map) {
        super(configsCache, map);
        this.permissionRole = new PermissionRole((Map)map.get(ROLE_PERMISSIONS));
    }

    public PermissionRole getPermissionRole() {
        return permissionRole;
    }

 }
