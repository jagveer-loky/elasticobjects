package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 10..7.2017
 */
public class ModelConfigNoneTest {

    @Test
    public void assertObject()  {
        ModelConfig model = ProviderConfigMaps.CONFIG_MAPS.findModel(Object.class.getSimpleName());
        Assert.assertEquals(ShapeTypes.NONE, model.getShapeType());
        //Assert.assertFalse(model.hasModel());
        Assert.assertFalse(model.isMap());
        Assert.assertFalse(model.isSet());
        Assert.assertFalse(model.isList());
        //Assert.assertTrue(model.isScalar());
        //Assert.assertFalse(model.isObject());
    }
}
