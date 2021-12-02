package org.fluentcodes.projects.elasticobjects.calls;

/*.{javaHeader}|*/

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;

/**
 * 
 * Interface with permissions methods  
 * @author null
 * @creationDate Wed Dec 16 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:52:16 CET 2021
 */
public interface PermissionInterface {
/*.{}.*/

/*.{javaStaticNames}|*/
   String ROLE_PERMISSIONS = "rolePermissions";
/*.{}.*/

/*.{javaAccessors}|*/
   PermissionRole getRolePermissions();
   default boolean hasRolePermissions() {
      return getRolePermissions() != null;
   }
/*.{}.*/
default boolean hasPermissions(PermissionType callPermission, List<String> roleKeys)  {
   int rolePermission = getRolePermissions().getPermissions(roleKeys).value();
   if (callPermission.value() > rolePermission) {
      throw new EoException("No permissions for roles " + roleKeys + ": " + callPermission.name() + "(" + callPermission.value() + ")");
   }
   return true;
}

}
