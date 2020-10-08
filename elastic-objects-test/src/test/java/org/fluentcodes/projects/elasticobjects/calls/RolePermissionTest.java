package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 11.10.2016.
 */
public class RolePermissionTest {
    private static final Logger LOG = LogManager.getLogger(RolePermissionTest.class);

    @Test
    public void testRoles()  {
        
        RolePermissions rolePermissions = new RolePermissions();
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
        permission = rolePermissions.getPermissions(Arrays.asList(RolePermissions.SUPERADMIN));
        Assert.assertEquals(PermissionType.EXECUTE, permission);
    }

}
