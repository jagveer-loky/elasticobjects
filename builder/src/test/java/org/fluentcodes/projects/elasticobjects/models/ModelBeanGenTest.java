package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

public class ModelBeanGenTest {
    protected static final ModelBeanGen AN_OBJECT_BEAN = new ModelBeanGen(ProviderRootTestScope.findModel(AnObject.class));

    @Test
    public void TEST_AnObject__get_ShapeType__BEAN() {
        Assertions.assertThat(AN_OBJECT_BEAN.getShapeType()).isEqualTo(ShapeTypes.BEAN);
    }

    // TODO empty since value not provided from model config.
    @Test
    public void TEST_AnObject__get_Import__expected() {
        Assertions.assertThat(AN_OBJECT_BEAN.getJavaImport()).isEqualTo("");
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(ModelBeanGen.class);
    }



}
