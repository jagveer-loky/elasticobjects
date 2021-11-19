package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Map;

public class ConfigMapsTest {
    @Test
    public void scopeDev() {
        ConfigMaps configMaps = new ConfigMaps(Scope.DEV);
        Assertions.assertThat(configMaps).isNotNull();
        Assertions.assertThat(configMaps.findModel(Map.class)).isNotNull();
    }

    @Test
    public void scopeTest() {
        ConfigMaps configMaps = new ConfigMaps(Scope.TEST);
        Assertions.assertThat(configMaps).isNotNull();
        Assertions.assertThat(configMaps.findModel(Map.class)).isNotNull();
    }
}
