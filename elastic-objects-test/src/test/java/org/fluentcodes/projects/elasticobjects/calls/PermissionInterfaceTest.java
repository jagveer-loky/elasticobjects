package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2017.
 */
public class PermissionConfigInterfaceTest {
    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(PermissionConfigInterface.class);
    }

    @Test
    public void createModelConfig()  {
        ModelConfigChecks.createThrowsException(PermissionConfigInterface.class);
    }

}
