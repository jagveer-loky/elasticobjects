package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2017.
 */
public class ConfigResourcesImplTest {
    @Test
    public void givenModel_whenCreate_thenThrowsException()  {
        final ModelConfig model = ProviderRootTest
                .EO_CONFIGS
                .findModel(ConfigResourcesImpl.class);
        Assert.assertEquals(ConfigResourcesImpl.class, model.getModelClass());
        Assertions
                .assertThatThrownBy(
                        () -> { model.create();})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not create empty instance from model for 'ConfigResourcesImpl'");
        Assertions.assertThat(model.getFieldKeys()).isNotEmpty();
    }
}
