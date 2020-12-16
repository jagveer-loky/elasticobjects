package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.models.ModelConfig.PACKAGE_PATH;
import static org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface.CLASS_PATH;

public class ModelBeanTest {
    @Test
    public void set_ClassPath_test__get__test() {
        ModelBean modelBean = new ModelBean();
        modelBean.setClassPath("test");
        Assertions.assertThat(modelBean.getClassPath()).isEqualTo("test");
     }

    @Test
    public void set_ClassPath_test__properties_get__test() {
        ModelBean modelBean = new ModelBean();
        modelBean.setClassPath("test");
        Assertions.assertThat((String)modelBean.getProperties().get(CLASS_PATH)).isEqualTo("test");
    }

    @Test
    public void eo_set_ClassPath_test__get__test() {
        EO eo = ProviderRootTestScope.createEo(new ModelBean());
        eo.set("test", CLASS_PATH);
        Assertions.assertThat((String)eo.get(CLASS_PATH)).isEqualTo("test");
    }

    @Test
    public void set_PackagePath_test__get__test() {
        ModelBean modelBean = new ModelBean();
        modelBean.setPackagePath("test");
        Assertions.assertThat(modelBean.getPackagePath()).isEqualTo("test");
    }

    @Test
    public void eo_set_PackagePath_test__get__test() {
        EO eo = ProviderRootTestScope.createEo(new ModelBean());
        eo.set("test", PACKAGE_PATH);
        Assertions.assertThat((String)eo.get(PACKAGE_PATH)).isEqualTo("test");
    }
}
