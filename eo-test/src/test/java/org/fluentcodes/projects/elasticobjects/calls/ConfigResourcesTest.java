package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 3.8.2017.
 */
public class ConfigResourcesTest {
    @Test
    public void givenModel_whenCreate_thenThrowsException()  {
        final ModelConfig model = ProviderRootTestScope
                .EO_CONFIGS
                .findModel(ConfigResources.class);
        Assert.assertEquals(ConfigResources.class, model.getModelClass());
        Assertions
                .assertThatThrownBy(
                        () -> { model.create();})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not create empty instance from model for 'ConfigResources'");
        Assertions.assertThat(model.getFieldKeys()).isNotEmpty();
    }
}
