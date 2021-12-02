package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.junit.Test;

import java.util.TreeSet;

/**
 * Created by Werner on 11.10.2016.
 */
public class ModelConfigDevTest {
    @Test
    public void check() {
        ConfigMaps cache = ProviderConfigMapsDev.CONFIG_MAPS_DEV;
        TreeSet<String> keys = new TreeSet<>(cache.getConfigKeys(ModelConfig.class));
        for (String key: keys) {
            ModelConfig model = cache.findModel(key);
            Assertions.assertThat(model).isNotNull();
        }
    }
}
