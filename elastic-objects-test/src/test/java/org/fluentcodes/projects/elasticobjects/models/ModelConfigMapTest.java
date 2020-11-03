package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootDevScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;


/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigMapTest {

    @Test
    public void readMainJackson()  {
        final ModelConfigMap mapModel = (ModelConfigMap) ProviderRootDevScope.EO_CONFIGS.findModel(Map.class);
        Assert.assertEquals(Map.class, mapModel.getModelClass());
        final Map map = (Map) mapModel.create();
        Assert.assertEquals(LinkedHashMap.class, map.getClass());
    }

    @Test
    public void assertMap()  {
        ModelConfig model = ProviderRootTestScope.EO_CONFIGS.findModel(Map.class.getSimpleName());
        Assert.assertEquals(ShapeTypes.MAP, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isMap());
        Assert.assertFalse(model.isSet());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertFalse(model.isObject());
    }


}
