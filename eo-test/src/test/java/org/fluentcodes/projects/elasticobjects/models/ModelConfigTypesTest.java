package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDev;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

public class ModelConfigTypesTest {
    @Test
    public void testCreate() {
        EOConfigsCache cache = ProviderRootDev.EO_CONFIGS;
        ModelConfigTypes type = ModelConfigTypes.OBJECT;
        Map values = new HashMap();
        values.put(Model.NATURAL_ID, Model.NATURAL_ID);
        ModelConfig config = type.createConfig(cache, values);
        Assertions.assertThat(config.getClass()).isEqualTo(ModelConfigObject.class);
        Assertions.assertThat(config.getNaturalId()).isEqualTo(Model.NATURAL_ID);
    }
}
