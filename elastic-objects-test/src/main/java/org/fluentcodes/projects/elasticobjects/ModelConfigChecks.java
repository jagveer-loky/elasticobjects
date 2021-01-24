package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterfaceMethods;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;

import java.util.Map;

public class ModelConfigChecks {

    public static Object createSetGet(final String beanName, final String fieldName, Object value)  {
        ModelConfig config = ProviderRootTestScope.EO_CONFIGS
                .findModel(beanName);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(fieldName, object, value);
        Assertions.assertThat(config.get(fieldName, object)).isEqualTo(value);
        return object;
    }


    public static ModelConfig createThrowsException(final Class modelClass) {
        final ModelConfig model = ProviderRootTestScope
                .EO_CONFIGS
                .findModel(modelClass);
        Assertions.assertThat(model.getModelClass()).isEqualTo(modelClass);
        Assertions
                .assertThatThrownBy(
                        () -> {
                            model.create();
                        })
                .isInstanceOf(EoException.class)
                .hasMessageContaining("ModelConfig has no create flag -> no empty instance will created for '" + modelClass.getSimpleName() + "'");
        return model;
    }

    public static ModelConfig resolve(final Class modelClass) {
        final ModelConfig model = ProviderRootTestScope
                .EO_CONFIGS
                .findModel(modelClass);
        Assertions.assertThat(model.getModelClass()).isEqualTo(modelClass);
        return model;
    }

    public static Object create(final Class modelClass)  {
        ModelConfig config = ProviderRootTestScope.EO_CONFIGS.findModel(modelClass);
        Assertions.assertThat(config).isNotNull();
        if (config.isScalar()) {
            return config.create();
        }
        else if (config.isCreate()) {
            return config.create();
        }

        throw new EoException("Method expects a create model type " + modelClass.getSimpleName());
    }

    public static void compare(final Class modelClass) {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(modelClass);
        EO cloneMap = EoRoot.ofClass(ProviderRootTestScope.EO_CONFIGS, Map.class);
        cloneMap.setSerializationType(JSONSerializationType.STANDARD);
        cloneMap.mapObject(modelConfig);
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.STANDARD)
                .build()
                .compareAsString(cloneMap);
    }

    public static final Object checkSetGet(final Class modelClass, final String fieldKey, final Object value) {
        Object object = create(modelClass);
        ModelConfig model = ProviderRootTestScope.EO_CONFIGS.findModel(modelClass);
        model.set(fieldKey, object, value);
        Assertions.assertThat(model.get(fieldKey, object)).isEqualTo(value);
        return object;
    }
}
