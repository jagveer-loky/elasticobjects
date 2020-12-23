package org.fluentcodes.projects.elasticobjects.domain.test;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterfaceMethods;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Test;

public class FieldExampleClassTest {

    @Test
    public void givenScopeDev_whenFindFieldExampleClass_thenExceptionThrown()  {
        try {
            ModelConfigInterfaceMethods model = ProviderRootDevScope.EO_CONFIGS.findModel(FieldExampleClass.class);
            Assert.fail("Should throw EoException since " + AnObject.class.getSimpleName() + " is not in the cache");
        }
        catch(EoException e) {

        }
    }


    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(FieldExampleClass.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(FieldExampleClass.class);
    }

}
