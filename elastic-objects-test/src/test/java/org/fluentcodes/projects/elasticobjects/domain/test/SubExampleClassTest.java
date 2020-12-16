package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterfaceMethods;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Test;

public class SubExampleClassTest {

    @Test
    public void givenScopeDev_whenFindSubExampleClass_thenExceptionThrown()  {
        try {
            ModelConfigInterfaceMethods model = ProviderRootDevScope.EO_CONFIGS.findModel(SubExampleClass.class);
            Assert.fail("Should throw EoException since " + AnObject.class.getSimpleName() + " is not in the cache");
        }
        catch(EoException e) {

        }
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(SubExampleClass.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(SubExampleClass.class);
    }

}
