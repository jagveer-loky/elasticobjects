package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.models.ConfigBean;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ModelBean.toPermissionRole;

/*.{javaHeader}|*/

/**
 * A bean container class for Field values
 *
 * @author Werner Diwischek
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 13:07:13 CET 2021
 */
public class PermissionBean extends ConfigBean implements PermissionInterface {
    /*.{}.*/
    private PermissionRole rolePermissions;

    public PermissionBean(final String naturalId, final Map<String, Object> map) {
        super(naturalId, map);
    }

    public PermissionBean() {
        super();
    }

    public void merge(final Map configMap) {
        super.merge(configMap);
        mergeRolePermissions(configMap.get(ROLE_PERMISSIONS));
    }

    /*.{javaAccessors}|*/
    @Override
    public PermissionRole getRolePermissions() {
        return this.rolePermissions;
    }

    public PermissionBean setRolePermissions(final PermissionRole rolePermissions) {
        this.rolePermissions = rolePermissions;
        return this;
    }

    /*.{}.*/

    private void mergeRolePermissions(final Object value) {
        if (value == null) return;
        if (hasRolePermissions()) return;
        setRolePermissions(toPermissionRole(value));
    }

}
