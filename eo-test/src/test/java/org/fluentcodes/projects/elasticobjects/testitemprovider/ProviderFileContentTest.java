package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;
import org.junit.Test;

public class ProviderFileContentTest {
    @Test
    public void testHealth() {
        for (ProviderFileContent ENTRY: ProviderFileContent.values()) {
            System.out.println("JSON: " + ENTRY.name());
            Assertions.assertThat(ENTRY.content()).isNotEmpty();
        }
    }
}
