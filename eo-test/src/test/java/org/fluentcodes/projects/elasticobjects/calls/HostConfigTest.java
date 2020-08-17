package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.projects.elasticobjects.models.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.models.Model.NATURAL_ID;

/**
 * Created by Werner on 11.10.2016.
 */
public class HostConfigTest {
    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(HostConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(HostConfig.class);
    }

    @Test
    public void givenConfigEntries_whenResolve_thenNoErrors()  {
        ConfigChecks.resolveConfigs(HostConfig.class);
    }

    @Test
    public void resolveConfigurations()  {
        ConfigChecks.resolveConfigurations(HostConfig.class);
    }

    @Test
    public void compareConfigurations()  {
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
        map.put(F_PROTOCOL, F_PROTOCOL);
        map.put(NATURAL_ID, F_HOST_KEY);
        map.put(F_PORT, S_INTEGER);
        map.put(F_USER, F_USER);
        map.put(F_PASSWORD, F_PASSWORD);
        HostConfig cache = (HostConfig) new HostConfig.Builder().build(ProviderRootTestScope.EO_CONFIGS, map);
        Assert.assertEquals(H_LOCALHOST, cache.getHostName());
        Assert.assertEquals(F_HOST_KEY, cache.getKey());
    }

    @Test
    public void givenConfigMapHost_whenNew_thenEntryFromFiles()  {
        EOConfigMap cache = new EOConfigMapImmutable(ProviderRootTestScope.EO_CONFIGS, HostConfig.class);
        Assert.assertNotNull(cache.find(H_LOCALHOST));
    }

}
