package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

public class ExecutorCallImplTest {
    private static final Logger LOG = LogManager.getLogger(ExecutorCallImplTest.class);

    @Test
    public void givenModel_whenCreate_thenOkAndFieldsAreEmpty()  {
        final ModelInterface model = ProviderRootTestScope.EO_CONFIGS.findModel(ExecutorCallImpl.class);
        Assert.assertEquals(ShapeTypes.OBJECT, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
        Assertions.assertThat(model.getFieldKeys()).isEmpty();
    }
}
