package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 7.8.2020.
 */
public class ModelInterfaceTest {
    private static final Logger LOG = LogManager.getLogger(ModelInterfaceTest.class);

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(ModelInterface.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ModelInterface.class);
    }

}
