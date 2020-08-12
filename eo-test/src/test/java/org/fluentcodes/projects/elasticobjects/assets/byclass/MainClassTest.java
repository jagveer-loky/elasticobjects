package org.fluentcodes.projects.elasticobjects.assets.byclass;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

public class MainClassTest {

    @Test
    public void givenScopeDev_whenFindBasicTest_thenExceptionThrown()  {
        try {
            ModelInterface model = ProviderRootDevScope.EO_CONFIGS.findModel(MainClass.class);
            Assert.fail("Should throw EoException since BasicTest is not in the cache");
        }
        catch(EoException e) {

        }
    }
    @Ignore
    @Test
    public void givenModelClass_whenCreate_thenNoException()  {
        ConfigModelChecks.create(MainClass.class);
    }

    @Ignore
    @Test
    public void givenModel_whenCompare_thenEqual()  {
        ConfigModelChecks.compare(MainClass.class);
    }

}
