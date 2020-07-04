package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 08.09.2018.
 */
public class ModelsMapElementaryTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsMapElementaryTest.class);

    @Test
    public void withMap()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class);
        Assert.assertEquals(Map.class, models.getModelClass());
        Assert.assertTrue(models.hasModel());
        Assert.assertFalse(models.hasChildModel());
        Assert.assertFalse(models.isEmpty());
    }

    @Test
    public void withMapList()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class, List.class);
        Assert.assertEquals(Map.class, models.getModelClass());
        Assert.assertEquals(List.class, models.getChildModelClass());
    }

    @Test
    public void withMapObject()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class, Object.class);
        Assert.assertEquals(Map.class, models.getModelClass());
        Assert.assertEquals(Object.class, models.getChildModelClass());
        Assert.assertEquals(1, models.size());
        Assert.assertTrue(models.hasModel());
        Assert.assertFalse(models.hasChildModel());
        Assert.assertFalse(models.isEmpty());
    }

    @Test
    public void withMapListMapList()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class, List.class, Map.class, List.class);
        Assert.assertEquals(4, models.size());
        Assert.assertTrue(models.hasModel());
        Assert.assertTrue(models.hasChildModel());
        Assert.assertEquals(Map.class, models.get(2).getModelClass());
    }

    @Test
    public void withMapStringValue()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class);
        final Models childModels = models.createChildForSet(F_TEST_STRING, S_STRING);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, childModels);
        Assert.assertEquals(INFO_COMPARE_FAILS + childModels.getModelClass().getSimpleName(), String.class, childModels.getModelClass());
    }

    @Test
    public void childMapStringValueAndStringClass()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class);
        final Models childModels = models.createChildForMap(F_TEST_STRING, S_STRING, new Class[]{String.class});
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, childModels);
        Assert.assertEquals(INFO_COMPARE_FAILS + childModels.getModelClass().getSimpleName(), String.class, childModels.getModelClass());
    }

    @Test
    public void childMapStringValueAndEmptyClass()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class);
        final Models childModels = models.createChildForMap(F_TEST_STRING, S_STRING, new Class[]{});
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, childModels);
        Assert.assertEquals(INFO_COMPARE_FAILS + childModels.getModelClass().getSimpleName(), String.class, childModels.getModelClass());
    }

    @Test
    public void childMapStringValueAndNullClass()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class);
        final Models childModels = models.createChildForMap(F_TEST_STRING, S_STRING);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, childModels);
        Assert.assertEquals(INFO_COMPARE_FAILS + childModels.getModelClass().getSimpleName(), String.class, childModels.getModelClass());
    }

    @Test
    public void childMapStringValueAndMapClass()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class);
        try {
            final Models childModels = models.createChildForMap(F_TEST_STRING, S_STRING, new Class[]{Map.class});
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void childMapBTValueAndMapClass()  {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class);
        final Models childModels = models.createChildForMap(F_UNTYPED_MAP, new BasicTest(), new Class[]{Map.class});
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, childModels);
        Assert.assertEquals(INFO_COMPARE_FAILS + childModels.getModelClass().getSimpleName(), Map.class, childModels.getModelClass());
    }

    @Test
    public void childMapBTValueAndBTClass()  {
        final Models parentModels = new Models(TestEOProvider.EO_CONFIGS, Map.class);
        final Models childModels = parentModels
                .createChildForMap(F_BASIC_TEST, new BasicTest(), new Class[]{BasicTest.class});
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, childModels);
        Assert.assertEquals(INFO_COMPARE_FAILS + childModels.getModelClass().getSimpleName(), BasicTest.class, childModels.getModelClass());
    }
}
