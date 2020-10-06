package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.domain.BaseImpl;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ModelsImplTest {
    @Test
    public void check()  {
        ModelConfigInterface modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(BaseImpl.class);
        Assert.assertEquals(ModelConfigObject.class, modelConfig.getClass());
        Assert.assertEquals(BaseImpl.class, modelConfig.getModelClass());
        Assert.assertEquals(4, modelConfig.getFieldCacheMap().size());
        Assert.assertEquals(5, modelConfig.getFieldKeys().size());
        BaseImpl modelImpl = (BaseImpl) modelConfig.create();
        Assert.assertNotNull(modelImpl);
    }

}
