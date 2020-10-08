package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TestProviderJsonTest {
    @Test
    public void testHealth() {
        for (TestProviderJson json: TestProviderJson.values()) {
            Assertions.assertThat(json.content()).isNotEmpty();
        }
    }
}
