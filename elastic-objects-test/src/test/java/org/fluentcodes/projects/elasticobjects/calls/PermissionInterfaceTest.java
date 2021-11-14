package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2017.
 */
public class PermissionInterfaceTest {
    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(PermissionInterface.class);
    }

    @Test
    public void createModelConfig()  {
        ModelConfigChecks.createThrowsException(PermissionInterface.class);
    }

}
