package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

import org.fluentcodes.projects.elasticobjects.models.EOConfigMapFields;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 04.11.2016.
 */
public class EOConfigMapFieldsTest {

    @Test
    public void findFieldConfigInModelCache()  {
        
        final ModelInterface fieldModel = ProviderRootTest.EO_CONFIGS.findModel(FieldConfig.class);
        Assert.assertEquals(FieldConfig.class.getSimpleName(), fieldModel.getModelKey());
        Assert.assertEquals(FieldConfig.class, fieldModel.getModelClass());
    }

    @Test
    public void findFieldConfigInCache()  {
        
        final FieldConfig fieldConfig = ProviderRootTest.EO_CONFIGS.findField(F_SUB_TEST_MAP);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, fieldConfig);
        Assert.assertNull(INFO_NULL_FAILS, fieldConfig.getPathPattern());
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS, fieldConfig.isFilterNothing());
    }

    @Test
    public void readConfigClassPath()  {
        EOConfigMapFields map = new EOConfigMapFields(ProviderRootTest.EO_CONFIGS);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.find(F_MODEL_KEY));
    }

    @Test
    public void readMap()  {
        EOConfigMapFields map = new EOConfigMapFields(ProviderRootTest.EO_CONFIGS);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.find(F_TEST_STRING));
        Assert.assertTrue(map.hasKey(F_TEST_LONG));
    }

}
