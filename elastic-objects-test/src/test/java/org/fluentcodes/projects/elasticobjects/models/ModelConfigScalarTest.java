package org.fluentcodes.projects.elasticobjects.models;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;
/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigScalarTest {

    @Test
    public void assertString()  {
        ModelConfig model = ProviderRootTestScope.EO_CONFIGS.findModel(String.class);
        Assert.assertEquals(String.class, model.getModelClass());
        Assert.assertEquals(ShapeTypes.SCALAR, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertFalse(model.isObject());
        Assert.assertTrue(model.isScalar());
        Assert.assertEquals(String.class.getSimpleName(), model.getNaturalId());
        Assert.assertEquals(String.class.getSimpleName(), model.getModelKey());
        Assertions.assertThat(model.create()).isNull();
    }

}
