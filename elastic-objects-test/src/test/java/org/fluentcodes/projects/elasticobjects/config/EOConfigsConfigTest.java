package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.ClassTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;

import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_STRING;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S_STRING;

/**
 * Created by werner.diwischek on 09.12.17.
 */
public class EOConfigsConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOConfigsConfigTest.class);

    @Test
    public void checkConfigsCache()  {
        ModelInterface model = TestEOProvider.EO_CONFIGS.findModel(ModelConfigObject.class);
        model.resolve();
        AssertEO.compare(TestEOProvider.EO_CONFIGS, model);
    }

    @Test
    public void testModelNotExisting_Exception()  {
        try {
            ModelInterface model = TestEOProvider.EO_CONFIGS.findModel("Nonsense");
            Assert.fail("Should throw EoException since Nonsense is not in the cache");
        }
        catch(EoException e) {
            LOG.info(e.getMessage());
        }
    }

    @Test
    public void testDevBT_Exception()  {
        try {
            ModelInterface model = DevObjectProvider.EO_CONFIGS.findModel(BasicTest.class);
            Assert.fail("Should throw EoException since BasicTest is not in the cache");
        }
        catch(EoException e) {
            LOG.info(e.getMessage());
        }
    }

    @Test
    public void checkConfigsCacheWithST()  {
        ModelInterface model = TestEOProvider.EO_CONFIGS.findModel(SubTest.class);
        model.resolve();
        AssertEO.compare(TestEOProvider.EO_CONFIGS, model);
    }

    // Just a first check for the dev scope working.
    @Test
    public void checkConfigsCacheDev()  {
        EOConfigsCache configsCache = new EOConfigsCache(Scope.DEV);
        ModelInterface model = configsCache.findModel(Map.class);
        model.resolve();
        EO adapter = TestEOProvider.create(S_STRING);
        Assert.assertEquals(S_STRING, adapter.get());
        Assert.assertEquals(M_STRING, adapter.getModelClass().getSimpleName());
    }


}
