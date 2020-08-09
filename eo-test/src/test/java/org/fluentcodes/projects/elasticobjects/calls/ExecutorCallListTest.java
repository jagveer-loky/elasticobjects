package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.ShapeTypes;
import org.junit.Assert;
import org.junit.Test;

public class ExecutorCallListTest {
    private static final Logger LOG = LogManager.getLogger(ExecutorCallListTest.class);

    @Test
    public void givenModel_whenCreate_thenOkAndFieldsAreEmpty()  {
        final ModelInterface model = ProviderRootTest.EO_CONFIGS.findModel(ExecutorCallList.class);
        Assert.assertEquals(ShapeTypes.OBJECT, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
        Object instance = model.create();
        Assertions.assertThat(instance).isNotNull();
        Assertions.assertThat(model.getFieldKeys()).isEmpty();
    }
}
