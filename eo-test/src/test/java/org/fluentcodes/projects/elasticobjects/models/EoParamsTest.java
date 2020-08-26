package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfig;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

public class EoParamsTest {

    @Test
    public void initDbParams() {
        EOParams eoParams = new EOParams(ProviderConfig.createEoParams());
        Assert.assertEquals(EOParams.MODEL_CONFIG_KEY, eoParams.getModelConfigKey());
        Assert.assertEquals(EOParams.DEFAULT_IMPLEMENTATION, eoParams.getDefaultImplementation());
        Assert.assertEquals(ShapeTypes.MAP, eoParams.getShapeType());
        Assert.assertTrue(eoParams.isCreate());
        Assert.assertEquals(EOParams.ATTRIBUTE_LIST, eoParams.getAttributeList().get(0));
        Assert.assertEquals(Scope.ALL, eoParams.getScope().get(0));
        Assert.assertEquals(F_PATH_PATTERN, eoParams.getPathPatternAsString());
    }

}
