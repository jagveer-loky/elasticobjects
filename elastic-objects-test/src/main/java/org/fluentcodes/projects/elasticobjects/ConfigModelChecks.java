package org.fluentcodes.projects.elasticobjects;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigCall;
import org.fluentcodes.projects.elasticobjects.calls.files.FileBean;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ConfigBean;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.FieldBean;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterfaceMethods;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.calls.files.FileConfig.FILE_NAME;

public class ConfigModelChecks {

    public static Object createSetGet(final String beanName, final String fieldName, Object value)  {
        ModelConfig config = ProviderRootTestScope.EO_CONFIGS
                .findModel(beanName);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(fieldName, object, value);
        Assertions.assertThat(config.get(fieldName, object)).isEqualTo(value);
        return object;
    }

    public static Object createConfig(final ModelBean configBean, final String fieldName, Object value)  {
        Assertions.assertThat(configBean).isNotNull();
        ModelConfigInterfaceMethods config = (ModelConfigInterfaceMethods)configBean.createConfig();
        //Assertions.assertThat(config.get(fieldName)).isEqualTo(value);
        return config;
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
                .hasMessageContaining("ModelConfig has no create flag -> no empty instance will create for '" + modelClass.getSimpleName() + "'");
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
        ConfigCall call = new ConfigCall(ModelConfig.class, modelClass.getSimpleName());
        EO eo = ProviderRootTestScope.createEo();
        List result = (List) call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.EO)
                .build()
                .compareAsString(result);
    }

    public static final Object checkSetGet(final Class modelClass, final String fieldKey, final Object value) {
        Object object = create(modelClass);
        ModelConfig model = ProviderRootTestScope.EO_CONFIGS.findModel(modelClass);
        model.set(fieldKey, object, value);
        Assertions.assertThat(model.get(fieldKey, object)).isEqualTo(value);
        return object;
    }
}
