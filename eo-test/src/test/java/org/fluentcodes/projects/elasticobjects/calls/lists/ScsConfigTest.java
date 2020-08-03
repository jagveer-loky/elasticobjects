package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;

import org.junit.Test;

/**
 * Created by Werner on 11.10.2016.
 */
public class ScsConfigTest {
    private static final Logger LOG = LogManager.getLogger(ScsConfigTest.class);

    @Test
    public void givenFoundModel_whenCreateInstance_thenExceptionThrown()  {
        ConfigChecks.findModelAndCreateInstanceExceptionThrown(ScsConfig.class);
    }

    @Test
    public void whenResolveConfigEntries_thenNoError()  {
        ConfigChecks.resolveConfigEntries(ScsConfig.class);
    }

    @Test
    public void whenCompareConfigurations_thenEqual()  {
        ConfigChecks.compareConfigurations(ScsConfig.class);
    }

}
