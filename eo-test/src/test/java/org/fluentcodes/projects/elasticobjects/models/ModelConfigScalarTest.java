package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_STRING;

/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigScalarTest {

    @Test
    public void assertString()  {
        ModelInterface model = ProviderRootTest.EO_CONFIGS.findModel(String.class);
        Assert.assertEquals(String.class, model.getModelClass());
        Assert.assertEquals(ShapeTypes.SCALAR, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertFalse(model.isObject());
        Assert.assertTrue(model.isScalar());
        Assert.assertEquals(M_STRING, model.getNaturalId());
        Assert.assertEquals(M_STRING, model.getModelKey());
        Assertions.assertThat(model.create()).isNull();
    }

}
