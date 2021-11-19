package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateEoConfigJsonCallTest extends IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return GenerateEoConfigJsonCall.class;
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

    /**
     * Was for configuration debugging purposes
     */
    @Test
    public void modelConfig__set_NaturalId_test__ok()  {
        ModelConfig model = ProviderConfigMaps.CONFIG_MAPS.findModel(GenerateEoConfigJsonCall.class.getSimpleName());
        Object object = model.create();
        model.set(BaseInterface.NATURAL_ID,object, "test");
        Assertions.assertThat(model.get(BaseInterface.NATURAL_ID, object)).isEqualTo("test");
    }
}
