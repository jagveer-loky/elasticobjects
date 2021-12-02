package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEo;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by Werner on 17.11.2021.
 */
public interface IModelConfigTests {

    Class<?> getModelConfigClass();

    void compareModelConfig();

    void compareBeanFromModelConfig();

    default ModelConfig getModelConfig() {
        return ProviderConfigMaps.CONFIG_MAPS.findModel(getModelConfigClass());
    }

    default void assertModelConfigEqualsPersisted() {
        assertThat(getModelConfig().toString())
                .isEqualTo(XpectEo.load(getModelConfig()));
    }

    default void assertBeanFromModelConfigEqualsPersisted() {
        final ModelBean fieldBean = getModelConfig().createBean();
        final String persisted = XpectEo.load(fieldBean);
        final String serialized = new EOToJSON()
                .toJson(getModelConfig().getConfigMaps(), fieldBean);
        assertThat(serialized)
                .isEqualTo(persisted);
    }

}
