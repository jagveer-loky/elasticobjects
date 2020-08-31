package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderJsonCalls;
import org.junit.Test;

import java.util.List;

public class ConfigKeysCallTest {
    @Test
    public void givenEoWithModelConfig_whenExecute_thenResultIsOrderedList() {
        EO eo = ProviderJsonCalls.CONFIG_KEYS_CALL_MODEL_CONFIG.createMapEo();
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat((List) eo.get("keys")).isNotEmpty();
    }


}
