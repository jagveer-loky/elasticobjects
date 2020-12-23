package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateModelTemplateCallTest {

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(GenerateModelTemplateCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(GenerateModelTemplateCall.class);
    }
}
