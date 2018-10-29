package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;


/**
 * Created by Werner on 06.09.2018.
 */
public class ModelsBTTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsBTTest.class);

    @Test
    public void withBT() throws Exception {
        Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE, BasicTest.class);
        Assert.assertEquals(BasicTest.class, models.getModelClass());
        Assert.assertEquals(Object.class, models.getChildModelClass());
    }

    @Test
    public void withBTString() throws Exception {
        Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE, BasicTest.class, String.class);
        Assert.assertEquals(Object.class, models.getChildModelClass());
    }

    @Test
    public void withMapBT() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE, Map.class, BasicTest.class);
        Assert.assertEquals(Map.class, models.getModelClass());
        Assert.assertEquals(BasicTest.class, models.getChildModelClass());
        Assert.assertEquals(String.class, models.createChild(F_BASIC_TEST).createChild(F_TEST_STRING).getModelClass());
    }

    @Test
    public void withBT_createChildTestString() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE, BasicTest.class);
        Assert.assertEquals(BasicTest.class, models.getModelClass());
        final Models child = models.createChild(F_TEST_STRING);
        Assert.assertEquals(String.class, child.getModelClass());
    }

    @Test
    public void withBT_createChildNotExisting_ThrowException() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE, BasicTest.class);
        Assert.assertEquals(BasicTest.class, models.getModelClass());
        try {
            final Models child = models.createChild(SAMPLE_KEY_UNKNOW);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION, e);
        }
    }

    @Test
    public void withBT_createChildForScalarAndValueNonScalar_ThrowException() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE, BasicTest.class);
        Assert.assertEquals(BasicTest.class, models.getModelClass());
        try {
            final Models child = models.createChildForSet(F_TEST_STRING, new HashMap());
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION, e);
        }
    }

    @Test
    public void withBT_createChildForNonScalarAndValueScalar_ThrowException() throws Exception {
        final Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE, BasicTest.class);
        Assert.assertEquals(BasicTest.class, models.getModelClass());
        try {
            final Models child = models.createChildForSet(F_UNTYPED_MAP, "");
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION, e);
        }
    }
}
