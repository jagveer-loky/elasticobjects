package org.fluentcodes.projects.elasticobjects.calls.lists;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 11.10.2016.
 */
public class ScsConfigTest {
    private static final Logger LOG = LogManager.getLogger(ScsConfigTest.class);

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(ScsConfig.class);
    }

    @Test
    public void resolveModelConfig()  {
        ConfigModelChecks.resolve(ScsConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ScsConfig.class);
    }

}
