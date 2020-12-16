package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.calls.generate.GenerateModelAbstract;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.ConfigConfigInterface.SCOPE;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfigInterface.OVERRIDE;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfigInterface.GENERATED;
import static org.fluentcodes.projects.elasticobjects.models.FieldConfigInterface.NOT_NULL;

public class FieldBeanGenTest {
    @Test
    public void set_notNull_true__get__true() {
        EO eo = ProviderRootTestScope.createEo();
        FieldBean fieldBean = new FieldBean();
        fieldBean.setNotNull(true);
        Assertions.assertThat(fieldBean.getNotNull()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(NOT_NULL)).isTrue();
        eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(NOT_NULL)).isTrue();
    }

    @Test
    public void set_override_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setOverride(true);
        Assertions.assertThat(fieldBean.getOverride()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(OVERRIDE)).isTrue();
        EO eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(OVERRIDE)).isTrue();
    }

    @Test
    public void set_generated_true__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setGenerated(true);
        Assertions.assertThat(fieldBean.getGenerated()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(GENERATED)).isTrue();
        EO eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(GENERATED)).isTrue();
    }

    @Test
    public void set_generated_true__merge__true() {
        FieldBean fieldBeanOverwritten = new FieldBean();
        FieldBean fieldBean = new FieldBean();
        fieldBean.setGenerated(true);
        fieldBeanOverwritten.merge(fieldBean);
        Assertions.assertThat(fieldBean.getGenerated()).isTrue();
        Assertions.assertThat((Boolean)fieldBean.getProperties().get(GENERATED)).isTrue();
        Assertions.assertThat(fieldBeanOverwritten.getGenerated()).isTrue();
        Assertions.assertThat((Boolean)fieldBeanOverwritten.getProperties().get(GENERATED)).isTrue();
        EO eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((Boolean)eo.get(GENERATED)).isTrue();
    }

    @Test
    public void set_scope_DEV__get__true() {
        FieldBean fieldBean = new FieldBean();
        fieldBean.setScope(Arrays.asList(new Scope[]{Scope.DEV}));
        Assertions.assertThat(fieldBean.getScope()).isNotEmpty();
        EO eo = ProviderRootTestScope.createEo(fieldBean);
        Assertions.assertThat((List)eo.get(SCOPE)).isNotEmpty();
    }

    @Test
    public void getAnObject__persist__withoutGetField() {
        EO eo = GenerateModelAbstract.READ(Scope.TEST);
        ModelBean modelBean = (ModelBean) eo.get(AnObject.class.getSimpleName());
        modelBean.resolve();
        FieldBean fieldBean = modelBean.getFieldBean(NATURAL_ID);
        String getMethod = fieldBean.getJavaGetMethod();

        Assertions.assertThat(getMethod).contains("get");
        String serialized = new EOToJSON().toJSON(eo.getEo(AnObject.class.getSimpleName()));
        String serialized2 = new EOToJSON().toJSON(eo.getConfigsCache(), fieldBean);
    }
}
