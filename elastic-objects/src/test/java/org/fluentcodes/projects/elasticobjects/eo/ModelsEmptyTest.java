package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 08.09.2018.
 */
public class ModelsEmptyTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsEmptyTest.class);

    @Test
    public void basic() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE);
        Assert.assertEquals(Map.class, models.getModelClass());
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS, models.hasModel());
    }

    @Test
    public void childString() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE);
        final Models childModels = models.createChildForSet(SAMPLE_KEY_UNKNOW, S_STRING);
        Assert.assertEquals(String.class, childModels.getModelClass());
        Assert.assertTrue(INFO_CONDITION_FALSE_FAILS, childModels.hasModel());
    }

    @Test
    public void childNull() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE);
        final Models childModels = models.createChildForSet(SAMPLE_KEY_UNKNOW, null);
        Assert.assertEquals(Map.class, childModels.getModelClass());
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS, childModels.hasModel());
    }

    @Test
    public void childNullWithNameNull() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE);
        try {
            final Models childModels = models.createChildForSet(null, null);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        }
        catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION);
        }
    }

    @Test
    public void childArrayList() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE);
        final Models childModels = models.createChildForSet(SAMPLE_KEY_UNKNOW, new ArrayList());
        Assert.assertEquals(ArrayList.class, childModels.getModelClass());
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS, childModels.hasModel());
    }

}
