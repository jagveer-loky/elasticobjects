package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.config.ShapeTypes;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

public class CallExecutorResourceTest {
    private static final Logger LOG = LogManager.getLogger(CallExecutorResourceTest.class);
    @Test
    public void createFromModel()  {
        final ModelInterface model = TestProviderRootTest.EO_CONFIGS.findModel(CallExecutorResource.class);
        Assert.assertEquals(ShapeTypes.BEAN, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
        Object executor = model.create();
        Assertions.assertThat(executor).isNotNull();
        for (String key: model.getFieldKeys()) {
            if (!model.hasFieldConfig(key)) {
                continue;
            }
            if (!model.hasSetter(key)) {
                continue;
            }
            Assertions.assertThat(model.hasGetter(key)).isTrue();

        }
    }
}
