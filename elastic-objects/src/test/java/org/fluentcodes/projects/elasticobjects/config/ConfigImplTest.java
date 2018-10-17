package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 13.4.2017.
 */
public class ConfigImplTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ConfigImplTest.class);

    @Test
    public void assertModelCache() throws Exception {
        TestHelper.printStartMethod();
        ModelInterface model = TestObjectProvider.EO_CONFIGS_CACHE.findModel(M_CONFIG_IMPL);
        Assert.assertEquals(ShapeTypes.CONFIG, model.getShapeType());
        Assert.assertTrue(model.hasModel());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
    }

}
