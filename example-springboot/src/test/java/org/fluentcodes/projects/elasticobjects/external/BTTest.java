package org.fluentcodes.projects.elasticobjects.external;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.values.SinusValueCall;
import org.junit.Test;


public class BTTest {
    @Test
    public void create() {
        BasicTest basicTest = new BasicTest();
        Assertions.assertThat(basicTest).isNotNull();
    }
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(BasicTest.class);
    }
}
