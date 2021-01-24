package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface.MODULE_SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;

public class ModelBeanMapTest {
    public static final ModelBeanMap MODEL_BEAN_MAP = new ModelBeanMap(Scope.TEST);

    @Test
    public void DEV_modelBeanMap__find_Map__notNull() {
        ModelBean bean = new ModelBeanMap().find(Map.class.getSimpleName());
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void TEST_modelBeanMap__find_Map__notNull() {
        ModelBean bean = MODEL_BEAN_MAP.find(Map.class.getSimpleName());
        Assertions.assertThat(bean).isNotNull();
    }

    @Test
    public void TEST_modelBeanMapResolved__find_AnObject__notNull() {
        ModelBean bean = MODEL_BEAN_MAP.find(ModelBean.class.getSimpleName());
        FieldBeanInterface packagePathBean = bean.getFieldBean(PACKAGE_PATH);
        Assertions.assertThat(packagePathBean).isNotNull();
        FieldBeanInterface moduleScopeBean = bean.getFieldBean(MODULE_SCOPE);
        Assertions.assertThat(moduleScopeBean).isNotNull();
    }



}
