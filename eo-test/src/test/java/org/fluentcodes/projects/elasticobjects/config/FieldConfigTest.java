package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathElement;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 27.8.2018.
 */
public class FieldConfigTest {

    @Test
    public void findFieldConfigInModelCache()  {
        
        final ModelInterface fieldModel = TestProviderRootTest.EO_CONFIGS.findModel(FieldConfig.class.getSimpleName());
        Assert.assertEquals(FieldConfig.class.getSimpleName(), fieldModel.getModelKey());
        Assert.assertEquals(FieldConfig.class, fieldModel.getModelClass());
    }

    @Test
    public void testFieldFromClassPath_Found()  {
        EOConfigsCache cache = TestProviderRootTest.EO_CONFIGS;
        FieldConfig field = cache.findField("ClassTest.id");
        Assert.assertNotNull(field);
        Assert.assertEquals("ClassTest.id", field.getNaturalId());
        Assert.assertEquals("id", field.getKey());
    }

    @Test
    public void testConfigMap()  {
        EOConfigMapFields map = new EOConfigMapFields(TestProviderRootTest.EO_CONFIGS);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, map.isEmpty());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, map.get(F_ID_KEY));
        Assert.assertTrue(map.hasKey(F_MODEL_KEYS));
    }


    @Test
    public void getId()  {
        
        final FieldConfig idConfig = TestProviderRootTest.EO_CONFIGS.findField(F_ID);
        Assert.assertEquals(Long.class, idConfig.getModelConfig().getModelClass());
        Assert.assertNotNull(idConfig.getEoFieldParams());
        Assert.assertFalse(idConfig.getEoFieldParams().isDeliverAction());
        Assert.assertEquals(Path.DELIMITER + PathElement.MATCHER, idConfig.getEoFieldParams().getPathPatternAsString());
    }



    @Test
    public void assertId()  {
        FieldConfig field = TestProviderRootTest.EO_CONFIGS.findField(F_ID);
        Assert.assertEquals(F_ID, field.getFieldKey());
        Assert.assertEquals(F_ID, field.getFieldName());
        Assert.assertEquals(S_BOOLEAN, field.isUnique());
        Assert.assertEquals(S_BOOLEAN, field.isNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Long.class, field.getModelClass());
    }

    /**
     * Will test the alias thing to id.
     *
     * @
     */
    @Test
    public void assertID()  {
        FieldConfig field = TestProviderRootTest.EO_CONFIGS.findField(F_UPPER_ID_KEY);
        Assert.assertEquals(F_UPPER_ID, field.getFieldKey());
        Assert.assertEquals(F_UPPER_ID, field.getFieldName());
        Assert.assertEquals(S_BOOLEAN, field.isUnique());
        Assert.assertEquals(S_BOOLEAN, field.isNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Long.class, field.getModelClass());
    }

    /**
     * Will test the alias thing to id.
     *
     * @
     */
    @Test
    public void assertTestObject()  {
        FieldConfig field = TestProviderRootTest.EO_CONFIGS.findField(F_TEST_OBJECT);
        Assert.assertEquals(F_TEST_OBJECT, field.getFieldKey());
        Assert.assertEquals(F_TEST_OBJECT, field.getFieldName());
        Assert.assertEquals(false, field.isUnique());
        Assert.assertEquals(false, field.isNotNull());
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, field.getDescription());
        Assert.assertEquals(Object.class, field.getModelClass());
    }

}
