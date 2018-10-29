package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.JSONReader;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.H_TEST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 11.10.2016.
 */
public class HostConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(HostConfigTest.class);
    private static final String HOST_SCOPE = "data/hostScope.json";
    private static final String HOST_PERMISSION = "data/hostPermission.json";

    @Test
    public void byAdapterPermissions() throws Exception {
        EO adapter = JSONReader.readAdapterBean(TestObjectProvider.EO_CONFIGS_CACHE, HOST_PERMISSION, null);
        AssertEO.compare(adapter);
    }

    @Test
    public void readHostConfigMain() throws Exception {
        TestHelper.printStartMethod();
        Map<String, Config> configMap = TestConfig.readConfigMapFromFile(CONFIG_HOST_TEST, HostConfig.class);
    }

    @Test
    public void byAdapterScope() throws Exception {
        EO adapter = JSONReader.readAdapterBean(TestObjectProvider.EO_CONFIGS_CACHE, HOST_SCOPE, null);
        AssertEO.compare(adapter);
    }


    @Test
    public void withHostKeyTest() throws Exception {
        final HostConfig config = (HostConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(HostConfig.class, H_TEST);
        Assert.assertEquals(F_DESCRIPTION, config.getDescription());

        Assert.assertEquals(F_HOST_KEY, config.getHostKey());
        Assert.assertEquals(F_HOST_NAME, config.getHostName());

        Assert.assertEquals(F_PASSWORD, config.getPassword());
        Assert.assertEquals(new Integer(1), config.getPort());
        Assert.assertEquals(F_PROTOCOL, config.getProtocol());
        Assert.assertEquals(F_USER, config.getUser());
        Assert.assertEquals(F_PROTOCOL + "://" + F_HOST_NAME + ":" + S_INTEGER, config.getUrlPath());

        Assert.assertEquals(join(CON_COMMA, R_TEST_ROLE_READ, R_GUEST), config.getRolePermissions().getRead());
        Assert.assertEquals(join(CON_COMMA, R_TEST_ROLE_WRITE, R_ADMIN), config.getRolePermissions().getWrite());
        Assert.assertEquals(join(CON_COMMA, R_TEST_ROLE_CREATE, R_ADMIN), config.getRolePermissions().getCreate());
        Assert.assertEquals(R_ADMIN, config.getRolePermissions().getDelete());
        Assert.assertEquals(R_SUPER_ADMIN, config.getRolePermissions().getExecute());
        Assert.assertEquals(Permissions.READ, config.getPermissions(R_TEST_ROLE_READ));
        Assert.assertEquals(Permissions.WRITE, config.getPermissions(R_TEST_ROLE_WRITE));
        Assert.assertEquals(Permissions.CREATE, config.getPermissions(R_TEST_ROLE_CREATE));
        Assert.assertEquals(Permissions.DELETE, config.getPermissions(R_ADMIN));
        Assert.assertEquals(Permissions.EXECUTE, config.getPermissions(R_SUPER_ADMIN));
        Assert.assertEquals(Permissions.READ, config.getPermissions(R_GUEST));
        Assert.assertEquals(Permissions.NOTHING, config.getPermissions(R_ANONYM));

        AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, config);

    }

    @Test
    public void createWithBean() throws Exception {
        TestHelper.printStartMethod();
        Map<String, Object> map = new HashMap<>();
        map.put(F_HOST_NAME, H_LOCALHOST);
        map.put(F_HOST_KEY, F_HOST_KEY);
        map.put(F_PROTOCOL, F_PROTOCOL);
        map.put(F_PORT, S_INTEGER);
        map.put(F_USER, F_USER);
        map.put(F_PASSWORD, F_PASSWORD);
        HostConfig cache = (HostConfig) new HostConfig.Builder().build(TestObjectProvider.EO_CONFIGS_CACHE, map);
        Assert.assertEquals(H_LOCALHOST, cache.getHostName());
        Assert.assertEquals(F_HOST_KEY, cache.getHostKey());
    }

    @Test
    public void getLocalHost() throws Exception {
        HostConfig cache = (HostConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(HostConfig.class, H_LOCALHOST);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, cache.getDescription());
    }

}
