package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.domain.BaseImpl;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class BaseImplTest {
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(BaseImpl.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(BaseImpl.class);
    }

    @Test
    public void TEST__findModel__fieldKeys_size_5()  {
        ModelConfigInterface modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(BaseImpl.class);
        Assert.assertEquals(ModelConfigObject.class, modelConfig.getClass());
        Assert.assertEquals(BaseImpl.class, modelConfig.getModelClass());
        Assert.assertEquals(5, modelConfig.getFieldCacheMap().size());
        Assert.assertEquals(5, modelConfig.getFieldKeys().size());
        BaseImpl modelImpl = (BaseImpl) modelConfig.create();
        Assert.assertNotNull(modelImpl);
    }

}
