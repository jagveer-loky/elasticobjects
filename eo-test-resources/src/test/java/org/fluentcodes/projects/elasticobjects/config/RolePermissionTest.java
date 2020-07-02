package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 11.10.2016.
 */
public class RolePermissionTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(RolePermissionTest.class);

    @Test
    public void testRoles() throws Exception {
        TestHelper.printStartMethod();
        RolePermissions rolePermissions = new RolePermissions();
        rolePermissions.setRead(R_TEST_ROLE_READ);
        rolePermissions.setWrite(R_TEST_ROLE_WRITE);
        rolePermissions.setCreate(R_TEST_ROLE_CREATE);
        rolePermissions.setDelete(R_TEST_ROLE_DELETE);
        rolePermissions.setExecute(R_TEST_ROLE_EXECUTE);

        Permissions permission = rolePermissions.getPermissions(null);
        Assert.assertEquals(Permissions.EXECUTE, permission);
        permission = rolePermissions.getPermissions(new ArrayList<>());
        Assert.assertEquals(Permissions.NOTHING, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_READ));
        Assert.assertEquals(Permissions.READ, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_WRITE));
        Assert.assertEquals(Permissions.WRITE, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_CREATE));
        Assert.assertEquals(Permissions.CREATE, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_DELETE));
        Assert.assertEquals(Permissions.DELETE, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(R_TEST_ROLE_EXECUTE));
        Assert.assertEquals(Permissions.EXECUTE, permission);
        permission = rolePermissions.getPermissions(Arrays.asList(RolePermissions.SUPERADMIN));
        Assert.assertEquals(Permissions.EXECUTE, permission);
    }

}
