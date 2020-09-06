package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_CONFIG_IMPL;

/**
 * Created by Werner on 13.4.2017.
 */
public class ConfigImplTest {
    private static final Logger LOG = LogManager.getLogger(ConfigImplTest.class);

    @Test
    public void assertModelCache()  {
        
        ModelInterface model = ProviderRootTestScope.EO_CONFIGS.findModel(M_CONFIG_IMPL);
        model.resolve();
        Assert.assertEquals(ShapeTypes.CONFIG, model.getShapeType());
        Assert.assertTrue(model.isObject());
        Assert.assertFalse(model.isScalar());
    }

}
