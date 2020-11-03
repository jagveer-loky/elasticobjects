package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.calls.generate.java.GenerateJavaCall;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateJavaCallTest {

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(GenerateJavaCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(GenerateJavaCall.class);
    }
}
