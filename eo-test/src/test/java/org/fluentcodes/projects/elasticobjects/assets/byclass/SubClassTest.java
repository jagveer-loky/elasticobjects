package org.fluentcodes.projects.elasticobjects.assets.byclass;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class SubClassTest {

    @Test
    public void givenScopeDev_whenFindBasicTest_thenExceptionThrown()  {
        try {
            ModelInterface model = ProviderRootDevScope.EO_CONFIGS.findModel(SubClass.class);
            Assert.fail("Should throw EoException since BasicTest is not in the cache");
        }
        catch(EoException e) {

        }
    }
    @Ignore
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(SubClass.class);
    }

    @Ignore
    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(SubClass.class);
    }

}
