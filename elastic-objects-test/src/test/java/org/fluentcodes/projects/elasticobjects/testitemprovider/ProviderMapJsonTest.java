package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ProviderMapJsonTest {
    @Test
    public void testHealth() {
        for (ProviderMapJson json: ProviderMapJson.values()) {
            Assertions.assertThat(json.content()).isNotEmpty();
        }
    }
}
