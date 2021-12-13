package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

import java.util.List;

public class ConfigKeysCallTest {
    public static final String DATA = "{\n" +
            "  \"(ConfigKeysCall)keys\": {\n" +
            "    \"configType\": \"ModelConfig\"\n" +
            "  }\n" +
            "}";
    static final EO DATA_EO = ProviderConfigMaps.createEo(DATA);

    @Test
    public void givenEoWithModelConfig_whenExecute_thenResultIsOrderedList() {
        DATA_EO.execute();
        Assertions.assertThat(DATA_EO.getLog()).isEmpty();
        Assertions.assertThat((List) DATA_EO.get("keys")).isNotEmpty();
    }


}
