package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMapsDev;
import org.fluentcodes.tools.io.IOClasspathStringList;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

public class ModelFactoryFromConfigurationsTest {

    @Test
    public void readModelConfig_EntriesExists() {
        final List<String> jsonList = new IOClasspathStringList("ModelConfig.json").read();
        for (final String json: jsonList) {
            EoRoot root = ProviderConfigMapsDev.createEo(json);
            Assert.assertTrue(root.size() > 0);
        }
    }

    @Test
    public void readFieldConfig_EntriesExists() {
        final List<String> jsonList = new IOClasspathStringList("FieldConfig.json").read();
        for (final String json: jsonList) {
            EoRoot root = ProviderConfigMapsDev.createEo(json);
            Assert.assertTrue(root.size() > 0);
        }
    }

    @Test
    public void createBeanMap__get_ConfigCall_get_configFilter_getModelKeys__String() {
        Map<String, ModelBean> beanMap = new ModelFactoryFromConfigurations(ProviderConfigMaps.CONFIG_MAPS).createBeanMap();
        ModelBean modelBean = beanMap.get(ConfigInterface.class.getSimpleName());
        Assertions.assertThat( modelBean
                .getFieldBean("moduleScope").getModelKeys())
                .isEqualTo(String.class.getSimpleName());
    }
}

