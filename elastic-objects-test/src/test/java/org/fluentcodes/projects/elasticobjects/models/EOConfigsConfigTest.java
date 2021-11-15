package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.domain.test.ASubObject;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 09.12.17.
 */
public class EOConfigsConfigTest {
    private static final Logger LOG = LogManager.getLogger(EOConfigsConfigTest.class);

    @Test
    public void checkConfigsCache()  {
        ModelConfigMethods model = ProviderRootTestScope.EO_CONFIGS.findModel(ModelConfigObject.class);
        //new XpectEo<>(TRootTestProvider.EO_CONFIGS).compareAsString(model);
    }

    @Test
    public void testModelNotExisting_Exception()  {
        try {
            ModelConfigMethods model = ProviderRootTestScope.EO_CONFIGS.findModel("Nonsense");
            Assert.fail("Should throw EoException since Nonsense is not in the cache");
        }
        catch(EoException e) {
            LOG.info(e.getMessage());
        }
    }


    @Test
    public void checkConfigsCacheWithST()  {
        ModelConfigMethods model = ProviderRootTestScope.EO_CONFIGS.findModel(ASubObject.class);
        //new XpectEo<>(TRootTestProvider.EO_CONFIGS).compareAsString(model);
    }
}
