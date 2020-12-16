package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;

/**
 * Created by Werner on 10.10.2016.
 */
public abstract class PermissionConfig extends ConfigConfig implements PermissionConfigInterfaceMethods {
    public static final String ROLE_PERMISSIONS = "rolePermissions";
    private final PermissionRole rolePermissions;

    public PermissionConfig(PermissionBean bean) {
        super(bean);
        this.rolePermissions = new PermissionRole(bean.getRolePermissions());
    }

    public PermissionRole getRolePermissions() {
        return rolePermissions;
    }

 }
