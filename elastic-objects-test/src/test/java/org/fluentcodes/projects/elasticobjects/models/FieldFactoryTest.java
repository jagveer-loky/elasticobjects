package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

public class FieldFactoryTest {
    @Test
    public void TEST_fieldBeanMap__find_id__notNull() {
        FieldBean bean = new FieldFactory(ProviderConfigMaps.CONFIG_MAPS).createBeanMap()
                .get("id");
        Assertions.assertThat(bean).isNotNull();
        Assertions.assertThat(bean.getModelKeys()).isNotNull();
    }

    @Test
    public void TEST_fieldBeanMap__find_configType__notNull() {
        FieldBean bean = new FieldFactory(ProviderConfigMaps.CONFIG_MAPS).createBeanMap()
                .get("configType");
        Assertions.assertThat(bean).isNotNull();
        Assertions.assertThat(bean.getModelKeys()).isNotNull();
    }
}
