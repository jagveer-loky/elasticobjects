package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 10..7.2017
 */
public class ModelConfigNoneTest extends TestHelper {

    @Test
    public void assertObject()  {
        TestHelper.printStartMethod();
        ModelInterface model = TestEOProvider.EO_CONFIGS.findModel(Object.class.getSimpleName());
        Assert.assertEquals(ShapeTypes.NONE, model.getShapeType());
        Assert.assertFalse(model.hasModel());
        Assert.assertFalse(model.isMap());
        Assert.assertFalse(model.isSet());
        Assert.assertFalse(model.isList());
        Assert.assertFalse(model.isScalar());
        Assert.assertFalse(model.isObject());
        String stringified = model.toString();
    }
}
