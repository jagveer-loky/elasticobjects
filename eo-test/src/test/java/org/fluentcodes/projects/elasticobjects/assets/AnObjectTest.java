package org.fluentcodes.projects.elasticobjects.assets;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.models.ModelConfigInterface;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.junit.Assert;
import org.junit.Test;

public class AnObjectTest {

    @Test
    public void DEV__findAnObject_exception()  {
        try {
            ModelConfigInterface model = ProviderRootDevScope.EO_CONFIGS.findModel(AnObject.class);
            Assert.fail("Should throw EoException since " + AnObject.class.getSimpleName() + " is not in the cache");
        }
        catch(EoException e) {

        }
    }

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(AnObject.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(AnObject.class);
    }

}
