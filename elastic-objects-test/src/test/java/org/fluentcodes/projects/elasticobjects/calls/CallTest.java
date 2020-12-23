package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2020.
 */
public class CallTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(Call.class);
    }
    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(Call.class);
    }
}
