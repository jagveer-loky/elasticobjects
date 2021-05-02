package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface.MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

public class ModelFactoryBasicTest {

    @Test
    public void createBeanMap__get_Map__notNull() {
        ModelBean bean = new ModelFactoryBasic().createBeanMap()
                .get(Map.class.getSimpleName());
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void createConfigMap__get_Map__notNull() {
        ModelConfigInterface config = new ModelFactoryBasic().createConfigMap(null)
                .get(Map.class.getSimpleName());
        Assertions.assertThat(config).isNotNull();
    }

}

