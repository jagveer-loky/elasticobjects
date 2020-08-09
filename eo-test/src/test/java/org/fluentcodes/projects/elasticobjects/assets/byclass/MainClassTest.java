package org.fluentcodes.projects.elasticobjects.assets.byclass;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDev;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void givenScopeDev_whenFindBasicTest_thenExceptionThrown()  {
        try {
            ModelInterface model = ProviderRootDev.EO_CONFIGS.findModel(MainClass.class);
            Assert.fail("Should throw EoException since BasicTest is not in the cache");
        }
        catch(EoException e) {

        }
    }
    @Ignore
    @Test
    public void givenModel_whenCreate_thenOk()  {
        ConfigChecks.findModelAndCreateInstance(MainClass.class);
    }

    @Test
    public void givenModel_whenCompare_thenEqual()  {
        ConfigChecks.findModelAndCompare(MainClass.class);
    }

}
