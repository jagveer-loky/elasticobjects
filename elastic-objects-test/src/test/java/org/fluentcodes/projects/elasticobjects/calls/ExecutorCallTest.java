package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class ExecutorCallTest {
    @Test
    public void modelConfig__create__exception()  {
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
