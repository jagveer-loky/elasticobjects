package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2017.
 */
public class CallImplTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ConfigModelChecks.createThrowsException(CallImpl.class);
    }
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(CallImpl.class);
    }


}
