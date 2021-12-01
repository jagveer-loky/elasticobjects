package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IConfigurationTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EoTestStatic.SAMPLE_KEY_UNKNOW;

/**
 * Created by Werner on 04.11.2016.
 */
public class ModelConfigTest implements IConfigurationTests {
    private static final Logger LOG = LogManager.getLogger(ModelConfigTest.class);

    @Override
    public Class<?> getModelConfigClass() {
        return ModelConfig.class;
    }

    @Override
    @Test
    public void create_throwsEoException() {
        assertCreateThrowingException();
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

    @Override
    @Test
    public void compareConfigurations() {
        assertLoadedConfigurationsEqualsPersisted();
    }


    @Test
    public void scopeTest__findModel_Unknown__exception() {
        Assertions.assertThatThrownBy(() -> {
            ProviderConfigMaps.CONFIG_MAPS.findModel(SAMPLE_KEY_UNKNOW);
        })
                .isInstanceOf(EoException.class);
    }

    @Test
    public void checkDependentModels() {
        // Check if basic Models are available
        ModelConfig model = ProviderConfigMaps.CONFIG_MAPS.findModel(AnObject.class.getSimpleName());
        Assert.assertEquals(AnObject.class.getSimpleName(), model.getModelKey());
        model = ProviderConfigMaps.CONFIG_MAPS.findModel(ASubObject.class);
        Assert.assertEquals(ASubObject.class.getSimpleName(), model.getModelKey());
    }
}
