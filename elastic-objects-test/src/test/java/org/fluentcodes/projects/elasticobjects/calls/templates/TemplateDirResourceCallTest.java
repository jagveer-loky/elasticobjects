package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * Created 21.9.2020
 */
public class TemplateDirResourceCallTest {

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(TemplateDirResourceCall.class);
    }

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(TemplateDirResourceCall.class);
    }
}
