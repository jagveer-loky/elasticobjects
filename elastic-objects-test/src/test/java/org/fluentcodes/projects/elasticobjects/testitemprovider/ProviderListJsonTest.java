package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

public class ProviderListJsonTest {
    // TODO check the reason failing probably the default setting of the _LOG_LEVEL
    @Ignore
    @Test
    public void testHealth() {
        for (ProviderListJson ENTRY: ProviderListJson.values()) {
            System.out.println("JSON: " + ENTRY.name());
            Assertions.assertThat(ENTRY.content()).isNotEmpty();
            EO eo = ENTRY.createEoDev();
            if (!ENTRY.name().contains("EMPTY")) {
                Assertions.assertThat(eo.isEoEmpty()).isFalse();
            }
            else {
                Assertions.assertThat(eo.isEoEmpty()).isTrue();
            }
        }
    }
    @Test
    public void testSmall() {
        String content = ProviderListJson.SMALL.content();
        EO eo = EoRoot.of(ProviderRootDevScope.EO_CONFIGS)
                .mapObject(ProviderListJson.SMALL.content());
        Assertions.assertThat(eo.getModelClass()).isEqualTo(List.class);
        Assertions.assertThat(eo.get("0")).isEqualTo("test");
        EO eoCreated = ProviderListJson.SMALL.createEoDev();
    }
}
