package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.calls.PermissionConfig.ROLE_PERMISSIONS;

/**
 * Created by Werner on 10.12.2020.
 */
public abstract class PermissionBean extends ConfigBean implements Permission {
    private PermissionRole rolePermissions;

    public PermissionBean() {
        super();
    }

    protected void merge(final Map values) {
        super.merge(values);
        mergePermission(values.get(ROLE_PERMISSIONS));
    }

    @Override
    public PermissionRole getRolePermissions() {
        return rolePermissions;
    }

    @Override
    public PermissionBean setRolePermissions(PermissionRole value) {
        this.rolePermissions = value;
        return this;
    }
 }
