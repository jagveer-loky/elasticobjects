package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * Created by Werner on 7.8.2020.
 */
public class ModelMethodsTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(ModelConfigInterfaceMethods.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(ModelConfigInterfaceMethods.class);
    }

}
