package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfig;
import org.junit.Assert;
import org.junit.Test;

public class EoParamsTest {

    @Test
    public void initDbParams() {
        EOParams eoParams = new EOParams(ProviderConfig.createEoParams());
        Assert.assertEquals(ModelConfigProperties.DEFAULT_IMPLEMENTATION, eoParams.getDefaultImplementation());
        Assert.assertEquals(ShapeTypes.MAP, eoParams.getShapeType());
        Assert.assertTrue(eoParams.isCreate());
    }

}
