package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.fluentcodes.projects.elasticobjects.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 11.10.2016.
 */
public class HostConfigTest {
    @Test
    public void givenModelClass_whenCreate_thenExceptionThrown()  {
        ConfigModelChecks.createThrowException(HostConfig.class);
    }

    @Test
    public void givenModel_whenCompare_thenEqual()  {
        ConfigModelChecks.compare(HostConfig.class);
    }

    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors()  {
        ConfigChecks.resolveConfigs(HostConfig.class);
    }

    @Test
    public void whenResolveConfigEntries_thenNoError()  {
        ConfigChecks.resolveConfigEntries(HostConfig.class);
    }

    @Test
    public void whenCompareConfigurations_thenXpected()  {
        ConfigChecks.compareConfigurations(HostConfig.class);
    }


    @Test
    public void byAdapterPermissions()  {
        //EO adapter = JSONReader.readAdapterBean(TestObjectProvider.EO_CONFIGS, HOST_PERMISSION, null);
        //AssertEO.compare(adapter);
    }

    @Test
    public void createWithBean()  {
        Map<String, Object> map = new HashMap<>();
        map.put(F_HOST_NAME, H_LOCALHOST);
        map.put(F_HOST_KEY, F_HOST_KEY);
        map.put(F_PROTOCOL, F_PROTOCOL);
        map.put(F_PORT, S_INTEGER);
        map.put(F_USER, F_USER);
        map.put(F_PASSWORD, F_PASSWORD);
        HostConfig cache = (HostConfig) new HostConfig.Builder().build(ProviderRootTest.EO_CONFIGS, map);
        Assert.assertEquals(H_LOCALHOST, cache.getHostName());
        Assert.assertEquals(F_HOST_KEY, cache.getHostKey());
    }

    @Test
    public void givenConfigMapHost_whenNew_thenEntryFromFiles()  {
        EOConfigMap cache = new EOConfigMapImmutable(ProviderRootTest.EO_CONFIGS, HostConfig.class);
        Assert.assertNotNull(cache.find(H_LOCALHOST));
    }

}
