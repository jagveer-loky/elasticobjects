package org.fluentcodes.projects.elasticobjects.testitemprovider;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;

/**
 * Created by Werner on 18.11.2021.
 */
public interface IModelConfigCreateTests extends IModelConfigTests {

    void create_noEoException();

    default void assertCreateNoException() {
        Object object = getModelConfig().create();
        Assertions.assertThat(object).isNotNull();
    }

    default Object assertSetGet(final String fieldKey, final Object value) {
        Object object = getModelConfig().create();
        ModelConfig model = ProviderConfigMaps.CONFIG_MAPS.findModel(getModelConfigClass());
        model.set(fieldKey, object, value);
        Assertions.assertThat(model.get(fieldKey, object)).isEqualTo(value);
        return object;
    }
}
