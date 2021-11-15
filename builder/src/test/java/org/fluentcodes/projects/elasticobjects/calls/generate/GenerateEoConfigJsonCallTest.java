package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.domain.BaseInterface;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateEoConfigJsonCallTest {

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(GenerateEoConfigJsonCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(GenerateEoConfigJsonCall.class);
    }

    /**
     * Was for configuration debugging purposes
     */
    @Test
    public void modelConfig__set_NaturalId_test__ok()  {
        ModelConfig model = ProviderRootTestScope.EO_CONFIGS.findModel(GenerateEoConfigJsonCall.class.getSimpleName());
        Object object = model.create();
        model.set(BaseInterface.NATURAL_ID,object, "test");
        Assertions.assertThat(model.get(BaseInterface.NATURAL_ID, object)).isEqualTo("test");
    }
}
