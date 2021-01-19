package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;
/*=>{javaHeader}|*/
/**
 * 
 * Interface with permissions methods  
 * @author null
 * @creationDate null
 * @modificationDate Thu Jan 14 12:54:22 CET 2021
 */
public interface PermissionBeanInterface extends PermissionConfigInterface  {
/*=>{}.*/

/*=>{javaAccessors}|*/
   PermissionBeanInterface setRolePermissions(final PermissionRole rolePermissions);
   default void mergeRolePermissions(final Object value) {
      if (value == null) return;
      if (hasRolePermissions()) return;
      setRolePermissions(ScalarConverter.toPermissionRole(value));
   }

/*=>{}.*/
}
