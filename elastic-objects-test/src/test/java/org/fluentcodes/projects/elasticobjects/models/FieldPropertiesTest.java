package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * Created by Werner on 27.8.2018.
 */
public class FieldPropertiesTest {

    @Test
    public void createByModelConfig_throwsException()  {
        ModelConfigChecks.createThrowsException(FieldConfig.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(FieldConfig.class);
    }

}
