package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

public class ModelBeanGenTest implements IModelConfigCreateTests {
    protected static final ModelBeanGen AN_OBJECT_BEAN = new ModelBeanGen(ProviderConfigMaps.findModel(AnObject.class));

    @Override
    public Class<?> getModelConfigClass() {
        return ModelBeanGen.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void TEST_AnObject__get_ShapeType__BEAN() {
        Assertions.assertThat(AN_OBJECT_BEAN.getShapeType()).isEqualTo(ShapeTypes.BEAN);
    }

    // TODO empty since value not provided from model config.
    @Test
    public void TEST_AnObject__get_Import__expected() {
        Assertions.assertThat(AN_OBJECT_BEAN.getJavaImport()).isEqualTo("");
    }
}
