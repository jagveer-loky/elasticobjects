package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateModelTemplateCallTest {

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(GenerateModelTemplateCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(GenerateModelTemplateCall.class);
    }
}
