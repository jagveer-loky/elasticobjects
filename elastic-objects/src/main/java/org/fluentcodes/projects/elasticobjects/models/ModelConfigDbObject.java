package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.PermissionProperties;
import org.fluentcodes.projects.elasticobjects.calls.PermissionRole;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.ResourceConfig.ROLE_PERMISSIONS;

/**
 * Created by Werner on 01.11.2020.
 */
public class ModelConfigDbObject extends ModelConfigObject implements ModelConfigDbProperties, PermissionProperties {
    private final PermissionRole permissionRole;
    public ModelConfigDbObject(EOConfigsCache configsCache, Map map) {
        super(configsCache, map);
        this.permissionRole = new PermissionRole((Map)map.get(ROLE_PERMISSIONS));
    }

    public PermissionRole getPermissionRole() {
        return permissionRole;
    }
}
