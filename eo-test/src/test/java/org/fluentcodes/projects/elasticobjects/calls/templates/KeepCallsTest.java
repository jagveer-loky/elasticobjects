package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created 6.8.2020
 */
public class KeepCallsTest {

    @Test
    public void gcompareModelConfig()  {
        ConfigModelChecks.compare(KeepCalls.class);
    }

    @Test
    public void createByModelConfig()  {
        Assertions.assertThat(ConfigModelChecks.create(KeepCalls.class)).isNull();
    }
}
