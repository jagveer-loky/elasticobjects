package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.ConfigConfig;
import org.fluentcodes.projects.elasticobjects.models.FieldInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.models.ModelBean;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.MY_STRING;
import static org.fluentcodes.projects.elasticobjects.domain.test.AnObject.NATURAL_ID;

public class AnObjectTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return AnObject.class;
    }

    @Override
    @Test
    public void create_noEoException()  {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig()  {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig()  {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void TEST__get_ShapeType__BEAN() {
        ModelBean modelBean = ProviderConfigMaps.findModelBean(AnObject.class);
        Assertions.assertThat(modelBean.getShapeType()).isEqualTo(ShapeTypes.BEAN);
    }

    @Test
    public void TEST__toString__BEAN_AnObject() {
        ModelBean modelBean = ProviderConfigMaps.findModelBean(AnObject.class);
        Assertions.assertThat(modelBean.toString()).isEqualTo("(BEAN)AnObject");
    }

    @Test
    public void myString__toString__equalsPersisted() {
        FieldInterface field = ProviderConfigMaps.findModel(AnObject.class).getField("myString");
        Assertions.assertThat(field.toString())
                .isEqualTo(XpectEo.load((ConfigConfig)field));
    }

    @Test
    public void TEST__setNaturalIdTest__getNaturalIdTest()  {
        ModelConfig config = ProviderConfigMaps.CONFIG_MAPS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(NATURAL_ID, object, "test");
        Assertions.assertThat(((AnObject)object).getNaturalId()).isEqualTo("test");
        Assertions.assertThat(config.get(NATURAL_ID, object)).isEqualTo("test");
    }

    @Test
    public void TEST__setMyStringTest__getMyStringTest()  {
        ModelConfig config = ProviderConfigMaps.CONFIG_MAPS
                .findModel(AnObject.class);
        Object object = config.create();
        Assertions.assertThat(object).isNotNull();
        config.set(MY_STRING, object, "test");
        Assertions.assertThat(((AnObject)object).getMyString()).isEqualTo("test");
        Assertions.assertThat(config.get(MY_STRING, object)).isEqualTo("test");
    }
}
