package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.ListProvider;
import org.fluentcodes.projects.elasticobjects.test.MapProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 10.09.2018.
 */
public class ModelsBTSTTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsBTSTTest.class);

    @Test
    public void setEmpty_ok()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        final Models child = models.createChildForSet(F_SUB_TEST, null, null);
        Assert.assertEquals(SubTest.class, child.getModelClass());
    }

    @Test
    public void mapEmpty_ok()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        final Models child = models.createChildForMap(F_SUB_TEST, null);
        Assert.assertEquals(SubTest.class, child.getModelClass());
    }

    @Test
    public void setJsonToEO_fails()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        final JSONToEO empty = new JSONToEO(SAMPLE_STRING_JSON_MAP_EMPTY, TestEOProvider.EO_CONFIGS);
        try {
            final Models childModels = models.createChildForSet(F_SUB_TEST, empty, null);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info("Expected Exception getting child with set and JSONToEO");
        }
    }

    @Test
    public void mapJsonToEO_okWithModelST_IsTypeOfTheField()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        final JSONToEO empty = new JSONToEO(SAMPLE_STRING_JSON_MAP_EMPTY, TestEOProvider.EO_CONFIGS);
        final Models child = models.createChildForMap(F_SUB_TEST, empty);
        Assert.assertEquals(SubTest.class, child.getModelClass());
    }

    @Test
    public void mapString_fails()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        try {
            models.createChildForMap(F_SUB_TEST, S_STRING);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setString_fails()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        try {
            models.createChildForSet(F_SUB_TEST, S_STRING);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setMap_fails()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        try {
            models.createChildForSet(F_SUB_TEST, MapProvider.createEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapMap_ok()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        final Models child = models.createChildForMap(F_SUB_TEST, MapProvider.createEmpty());
        Assert.assertEquals(SubTest.class, child.getModelClass());
    }

    @Test
    public void setList_fails()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        try {
            models.createChildForSet(F_SUB_TEST, ListProvider.createEmpty(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapList_fails()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        try {
            models.createChildForMap(F_SUB_TEST, ListProvider.createEmpty());
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void setSubTest_ok()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        Models child = models.createChildForSet(F_SUB_TEST, new SubTest(), null);
        Assert.assertEquals(SubTest.class, child.getModelClass());
    }

    @Test
    public void setBasicTest_fails()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        try {
            models.createChildForSet(F_SUB_TEST, new BasicTest(), null);
            Assert.fail(INFO_EXPECTED_NO_EXCEPTION);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void mapBasicTest_ok()  {
        TestHelper.printStartMethod();
        final Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        final Models child = models.createChildForMap(F_SUB_TEST, new BasicTest());
        Assert.assertEquals(SubTest.class, child.getModelClass());
    }
}
