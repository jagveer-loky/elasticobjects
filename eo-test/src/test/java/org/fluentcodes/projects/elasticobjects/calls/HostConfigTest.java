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
    public void givenConfigMapHost_whenNew_thenEntryFromFiles()  {
        EOConfigMap cache = new EOConfigMapImmutable(ProviderRootTestScope.EO_CONFIGS, HostConfig.class);
        Assert.assertNotNull(cache.find(H_LOCALHOST));
    }

}
