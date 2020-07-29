package org.fluentcodes.projects.elasticobjects.fileprovider;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class TestProviderCallTemplateTest {
    @Test
    public void testHealth() {
        for (TestProviderCallTemplate template: TestProviderCallTemplate.values()) {
            Assertions.assertThat(template.content()).isNotEmpty();
        }
    }
}
