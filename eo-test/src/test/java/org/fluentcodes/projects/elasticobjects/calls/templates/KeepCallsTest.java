package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.junit.Test;

/**
 * Created 6.8.2020
 */
public class KeepCallsTest {

    @Test
    public void givenTestProvider_whenGetModelConfigParameters_thenXpected()  {
        ConfigChecks.findModelAndCompare(KeepCalls.class);
    }

    @Test
    public void givenFoundModel_whenCreateInstance_thenNull()  {
        Assertions.assertThat(ConfigChecks.findModelAndCreateInstance(KeepCalls.class)).isNull();
    }
}
