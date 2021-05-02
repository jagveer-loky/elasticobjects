package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface.MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

public class ModelFactoryAllTest {
    public static final ModelFactory MODEL_BEAN_MAP = new ModelFactoryAll();

    @Test
    public void createBeanMap__get_Map__notNull() {
        ModelBean bean = new ModelFactoryAll().createBeanMap((ConfigMaps) null)
                .get(Map.class.getSimpleName());
        Assertions.assertThat(bean).isNotNull();
    }


    @Test
    public void TEST_modelBeanMapResolved__find_AnObject__notNull() {
        ModelBean bean = new ModelFactoryAll().createBeanMap((ConfigMaps) null)
                .get(ModelBean.class.getSimpleName());
        FieldBeanInterface packagePathBean = bean.getFieldBean(PACKAGE_PATH);
        Assertions.assertThat(packagePathBean).isNotNull();
        FieldBeanInterface moduleScopeBean = bean.getFieldBean(MODULE_SCOPE);
        Assertions.assertThat(moduleScopeBean).isNotNull();
    }



}
