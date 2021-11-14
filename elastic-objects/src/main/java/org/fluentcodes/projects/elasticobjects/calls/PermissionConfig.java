package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;

import java.util.List;

/*=>{javaHeader}|*/
/**
 * 
 * Abstract implementation for resources with permissions methods  
 * @author null
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:50:34 CET 2021
 */
public abstract class PermissionConfig extends ConfigConfig implements PermissionInterface {
/*=>{}.*/

    private final PermissionRole rolePermissions;

    public PermissionConfig(PermissionBean bean) {
        super(bean);
        this.rolePermissions = new PermissionRole(bean.getRolePermissions());
    }
    /*=>{javaAccessors}|*/
   @Override
   public PermissionRole getRolePermissions() {
      return this.rolePermissions;
   }
/*=>{}.*/

    public boolean hasPermissions(PermissionType callPermission, List<String> roleKeys)  {
        int rolePermission = getRolePermissions().getPermissions(roleKeys).value();
        if (callPermission.value() > rolePermission) {
            throw new EoException("No permissions for roles " + roleKeys + ": " + callPermission.name() + "(" + callPermission.value() + ")");
        }
        return true;
    }

 }
