package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class ExecutorCallTest {
    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(ExecutorCall.class);
    }

    @Test
    public void createModelConfig()  {
        ModelConfigChecks.createThrowsException(ExecutorCall.class);
    }
}
