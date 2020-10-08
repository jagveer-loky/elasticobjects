package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TestProviderJsonCallsTest {
    @Test
    public void testHealth() {
        for (TestProviderJsonCalls json: TestProviderJsonCalls.values()) {
            Assertions.assertThat(json.content()).isNotEmpty();
        }
    }
}
