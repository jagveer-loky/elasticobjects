package org.fluentcodes.projects.elasticobjects.assets;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
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
    public void givenModelClass_whenCreate_thenNoException()  {
        ConfigModelChecks.create(BasicTest.class);
    }

    @Test
    public void givenModel_whenCompare_thenEqual()  {
        ConfigModelChecks.compare(BasicTest.class);
    }

}
