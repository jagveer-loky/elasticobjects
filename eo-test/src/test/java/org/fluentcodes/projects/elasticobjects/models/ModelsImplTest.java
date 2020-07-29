package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.config.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ModelsImplTest {
    @Test
    public void check()  {
        ModelInterface modelConfig = TestProviderRootTest.EO_CONFIGS.findModel(ModelImpl.class);
        Assert.assertEquals(ModelConfigObject.class, modelConfig.getClass());
        Assert.assertEquals(ModelImpl.class, modelConfig.getModelClass());
        Assert.assertEquals(5, modelConfig.getFieldCacheMap().size());
        Assert.assertEquals(5, modelConfig.getFieldKeys().size());
        ModelImpl modelImpl = (ModelImpl) modelConfig.create();
        Assert.assertNotNull(modelImpl);
    }

}
