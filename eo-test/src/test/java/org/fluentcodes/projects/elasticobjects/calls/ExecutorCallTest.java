package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class ExecutorCallTest {
    private static final Logger LOG = LogManager.getLogger(ExecutorCallTest.class);

    @Test
    public void givenModel_whenCreate_thenThrowsException()  {
        final ModelConfig model = ProviderRootTestScope
                .EO_CONFIGS
                .findModel(ExecutorCall.class);
        Assert.assertEquals(ExecutorCall.class, model.getModelClass());
        Assertions
                .assertThatThrownBy(
                        () -> { model.create();})
                .isInstanceOf(EoException.class)
                .hasMessageContaining("Could not create empty instance from model for 'ExecutorCall'");
        Assertions.assertThat(model.getFieldKeys()).isEmpty();
    }


}
