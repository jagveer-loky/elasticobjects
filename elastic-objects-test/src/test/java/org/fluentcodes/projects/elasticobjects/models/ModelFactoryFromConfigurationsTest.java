package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

import java.util.Map;

public class ModelFactoryFromConfigurationsTest {

    @Test
    public void createBeanMap__get_ConfigCall_get_configFilter_getModelKeys__String() {
        Map<String, ModelBean> beanMap = new ModelFactoryFromConfigurations(ProviderConfigMaps.CONFIG_MAPS).createBeanMap();
        ModelBean modelBean = beanMap.get("ConfigCall");
        Assertions.assertThat( modelBean
                .getFieldBean("moduleScope").getModelKeys())
                .isEqualTo(String.class.getSimpleName());
    }

    @Test
    public void createConfigMap__() {
        Map<String, ModelInterface> configMap = new ModelFactoryFromConfigurations(ProviderConfigMaps.CONFIG_MAPS).createConfigMap();
        Assertions.assertThat( configMap.get("ConfigCall")
                .getField("configFilter")
                .getModelKeys())
                .isEqualTo(String.class.getSimpleName());
    }

}

