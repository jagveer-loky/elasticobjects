package org.fluentcodes.projects.elasticobjects.assets;

import org.fluentcodes.projects.elasticobjects.ConfigChecks;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDev;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.junit.Assert;
import org.junit.Test;

public class BtTest {

    @Test
    public void givenScopeDev_whenFindBasicTest_thenExceptionThrown()  {
        try {
            ModelInterface model = ProviderRootDev.EO_CONFIGS.findModel(BasicTest.class);
            Assert.fail("Should throw EoException since BasicTest is not in the cache");
        }
        catch(EoException e) {

        }
    }

    @Test
    public void givenModel_whenCreate_thenOk()  {
        ConfigChecks.findModelAndCreateInstance(BasicTest.class);
    }

    @Test
    public void givenModel_whenCompare_thenEqual()  {
        ConfigChecks.findModelAndCompare(BasicTest.class);
    }

}
