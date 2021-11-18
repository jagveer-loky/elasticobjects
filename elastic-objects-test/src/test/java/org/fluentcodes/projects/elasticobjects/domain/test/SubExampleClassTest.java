package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigMethods;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Test;

public class SubExampleClassTest {

    @Test
    public void givenScopeDev_whenFindSubExampleClass_thenExceptionThrown()  {
        try {
            ModelConfigMethods model = ProviderRootDevScope.CONFIG_MAPS_DEV.findModel(SubExampleClass.class);
            Assert.fail("Should throw EoException since " + AnObject.class.getSimpleName() + " is not in the cache");
        }
        catch(EoException e) {

        }
    }

    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(SubExampleClass.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(SubExampleClass.class);
    }

}
