package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigScalarTest extends TestHelper {

    @Test
    public void readMainJacksonString() throws Exception {
        TestHelper.printStartMethod();
        ModelConfigScalar stringModel = (ModelConfigScalar) TestObjectProvider.EO_CONFIGS_CACHE.findModel(String.class);
        Assert.assertEquals(String.class, stringModel.getModelClass());
    }

    @Test
    public void assertString() throws Exception {
        TestHelper.printStartMethod();
        ModelInterface config = TestObjectProvider.EO_CONFIGS_CACHE.findModel(M_STRING);
        Assert.assertEquals(ShapeTypes.SCALAR, config.getShapeType());
        Assert.assertTrue(config.hasModel());
        Assert.assertFalse(config.isObject());
        Assert.assertTrue(config.isScalar());
        Assert.assertEquals(M_STRING, config.getNaturalId());
        Assert.assertEquals(M_STRING, config.getModelKey());
    }

}
