package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_STRING;

/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigScalarTest extends TestHelper {

    @Test
    public void readMainJacksonString()  {
        TestHelper.printStartMethod();
        ModelConfigScalar stringModel = (ModelConfigScalar) TestEOProvider.EO_CONFIGS.findModel(String.class);
        Assert.assertEquals(String.class, stringModel.getModelClass());
    }

    @Test
    public void assertString()  {
        TestHelper.printStartMethod();
        ModelInterface config = TestEOProvider.EO_CONFIGS.findModel(M_STRING);
        Assert.assertEquals(ShapeTypes.SCALAR, config.getShapeType());
        Assert.assertTrue(config.hasModel());
        Assert.assertFalse(config.isObject());
        Assert.assertTrue(config.isScalar());
        Assert.assertEquals(M_STRING, config.getNaturalId());
        Assert.assertEquals(M_STRING, config.getModelKey());
    }

}
