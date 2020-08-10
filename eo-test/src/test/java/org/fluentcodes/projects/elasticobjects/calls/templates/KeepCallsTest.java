package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.junit.Test;

/**
 * Created 6.8.2020
 */
public class KeepCallsTest {

    @Test
    public void givenTestProvider_whenGetModelConfigParameters_thenXpected()  {
        ConfigModelChecks.compare(KeepCalls.class);
    }

    @Test
    public void givenModelClass_whenCreate_thenNoException()  {
        Assertions.assertThat(ConfigModelChecks.create(KeepCalls.class)).isNull();
    }
}
