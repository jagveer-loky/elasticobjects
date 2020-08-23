package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import java.util.List;

public class ConfigTypesCallTest {

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(ConfigTypesCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(ConfigTypesCall.class);
    }

    @Test
    public void givenCall_whenExecute_thenResultIsOrderedList() {
        ConfigTypesCall call = new ConfigTypesCall();
        EO eo = ProviderRootTestScope.createEo();
        List<String> result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
    }
}
