package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created 21.9.2020
 */
public class TemplateDirResourceCallTest {

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(TemplateDirResourceCall.class);
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(TemplateDirResourceCall.class);
    }
}
