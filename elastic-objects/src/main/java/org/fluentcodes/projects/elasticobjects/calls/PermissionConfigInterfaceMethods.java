package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Arrays;
import java.util.List;

/*=>{javaHeader}|*/
/**
 * 
 * Interface with permissions methods  
 * @author null
 * @creationDate Sun Nov 01 00:00:00 CET 2020
 * @modificationDate Thu Jan 14 12:40:27 CET 2021
 */
public interface PermissionConfigInterfaceMethods extends PermissionConfigInterface  {
/*=>{}.*/

/*=>{javaAccessors}|*/
/*=>{}.*/
    /*
    default PermissionType getPermissions(String... roleKeys)  {
        return getRolePermissions().getPermissions(Arrays.asList(roleKeys));
    }
    default PermissionType getPermissions(List<String> roleKeys)  {
        return getRolePermissions().getPermissions(roleKeys);
    }
    */
    default boolean hasPermissions(PermissionType callPermission, List<String> roleKeys)  {
        int rolePermission = getRolePermissions().getPermissions(roleKeys).value();
        if (callPermission.value() > rolePermission) {
            throw new EoException("No permissions for roles " + roleKeys + ": " + callPermission.name() + "(" + callPermission.value() + ")");
        }
        return true;
    }
}
