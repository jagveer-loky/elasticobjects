package org.fluentcodes.projects.elasticobjects.calls;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_TEST_ROLE_CREATE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_TEST_ROLE_DELETE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_TEST_ROLE_EXECUTE;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_TEST_ROLE_READ;
import static org.fluentcodes.projects.elasticobjects.EoTestStatic.R_TEST_ROLE_WRITE;

/**
 * Created by Werner on 11.10.2016.
 */
public class RolePermissionBeanInterfaceTest {

    @Test
    public void testRoles()  {
        
        PermissionRole rolePermissions = new PermissionRole();
        rolePermissions.setRead(R_TEST_ROLE_READ);
        rolePermissions.setWrite(R_TEST_ROLE_WRITE);
        rolePermissions.setCreate(R_TEST_ROLE_CREATE);
        rolePermissions.setDelete(R_TEST_ROLE_DELETE);
        rolePermissions.setExecute(R_TEST_ROLE_EXECUTE);

        PermissionType permission = rolePermissions.getPermissions(null);
        Assert.assertEquals(PermissionType.EXECUTE, permission);
        permission = rolePermissions.getPermissions(new ArrayList<>());
        Assert.assertEquals(PermissionType.NOTHING, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_READ));
        Assert.assertEquals(PermissionType.READ, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_WRITE));
        Assert.assertEquals(PermissionType.WRITE, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_CREATE));
        Assert.assertEquals(PermissionType.CREATE, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_DELETE));
        Assert.assertEquals(PermissionType.DELETE, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_EXECUTE));
        Assert.assertEquals(PermissionType.EXECUTE, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(PermissionRole.SUPERADMIN));
        Assert.assertEquals(PermissionType.EXECUTE, permission);
    }

}
