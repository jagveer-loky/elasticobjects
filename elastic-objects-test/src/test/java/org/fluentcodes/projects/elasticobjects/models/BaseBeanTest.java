package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.domain.BaseBean;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class BaseBeanTest {
    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(BaseBean.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(BaseBean.class);
    }

    @Test
    public void TEST__findModel__fieldKeys_size_5()  {
        ModelConfig modelConfig = ProviderRootTestScope.EO_CONFIGS.findModel(BaseBean.class);
        Assert.assertEquals(ModelConfigObject.class, modelConfig.getClass());
        Assert.assertEquals(BaseBean.class, modelConfig.getModelClass());
        Assert.assertEquals(5, modelConfig.getFieldConfigMap().size());
        Assert.assertEquals(5, modelConfig.getFieldKeys().size());
        BaseBean modelImpl = (BaseBean) modelConfig.create();
        Assert.assertNotNull(modelImpl);
    }

}
