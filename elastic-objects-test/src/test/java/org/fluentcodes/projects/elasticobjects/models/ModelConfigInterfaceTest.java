package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 7.8.2020.
 */
public class ModelConfigInterfaceTest {
    private static final Logger LOG = LogManager.getLogger(ModelConfigInterfaceTest.class);

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(ModelConfigInterface.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ModelConfigInterface.class);
    }

}
