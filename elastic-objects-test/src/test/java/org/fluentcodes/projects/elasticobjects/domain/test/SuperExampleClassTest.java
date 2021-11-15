package org.fluentcodes.projects.elasticobjects.domain.test;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigMethods;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

public class SuperExampleClassTest {

    @Test
    public void givenScopeDev_whenFindSuperExampleClass_thenExceptionThrown()  {
        try {
            ModelConfigMethods model = ProviderRootDevScope.EO_CONFIGS.findModel(SuperExampleClass.class);
            Assert.fail("Should throw EoException since " + AnObject.class.getSimpleName() + " is not in the cache");
        }
        catch(EoException e) {

        }
    }

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(SuperExampleClass.class);
    }

    @Test
    public void setValue() {
        ModelConfig config = ProviderRootTestScope.EO_CONFIGS.findModel(SuperExampleClass.class);
        Object object = config.create();
        config.set("id", object, 1L);
        Assertions.assertThat(config.get("id", object)).isEqualTo(1L);
        Assertions.assertThat(((SuperExampleClass)object).getId()).isEqualTo(1L);

    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(SuperExampleClass.class);
    }

}
