package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.tools.xpect.XpectEo;

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
        ModelBean fieldBean = getModelConfig().createBean();

        assertThat(new EOToJSON()
                .setSerializationType(JSONSerializationType.STANDARD)
                .toJson(getModelConfig().getConfigMaps(), fieldBean))
                .isEqualTo(XpectEo
                        .load(getModelConfig().getConfigMaps(), fieldBean));
    }

}
