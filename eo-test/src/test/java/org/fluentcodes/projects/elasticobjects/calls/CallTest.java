package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2020.
 */
public class CallTest {
    private static final Logger LOG = LogManager.getLogger(CallTest.class);

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(Call.class);
    }
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(Call.class);
    }
}
