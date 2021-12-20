package org.fluentcodes.projects.elasticobjects.io;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps.CONFIG_MAPS;
import static org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev.CONFIG_MAPS_DEV;

public class IOClasspathEoFlatMapTest {
    @Test
    public void DEV_ModelConfigMapMap() {
        IOClasspathEOFlatMap<Map> io = new IOClasspathEOFlatMap<>(CONFIG_MAPS_DEV, "ModelConfig.json", Map.class);
        Map<String, Map> modelMap = io.read();
        Assertions.assertThat(modelMap).isNotNull();
        Assertions.assertThat(modelMap.get("ModelBean")).isNotNull();
    }
}
