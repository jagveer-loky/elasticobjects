package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Map;

public class EoConfigMapModelsTest {
    @Test
    public void DEV__find_Map__notNull() {
        EOConfigMapModels models = new EOConfigMapModels(null, Scope.DEV);
        ConfigConfigInterface config = models.find(Map.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
    }

    @Test
    public void TEST__find_Map__notNull() {
        EOConfigMapModels models = new EOConfigMapModels(null, Scope.TEST);
        ConfigConfigInterface config = models.find(Map.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
    }
}
