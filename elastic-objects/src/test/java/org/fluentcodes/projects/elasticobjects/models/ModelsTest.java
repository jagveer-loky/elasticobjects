package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.config.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by werner.diwischek on 06.01.18.
 */
public class ModelsTest {
    @Test
    public void checkModel() throws Exception {
        ModelInterface modelConfig = TestObjectProvider.EO_CONFIGS_CACHE.findModel(Model.class);
        Assert.assertEquals(ModelConfigObject.class, modelConfig.getClass());
        Assert.assertEquals(Model.class, modelConfig.getModelClass());
        Assert.assertEquals(5, modelConfig.getFieldCacheMap().size());
        Assert.assertEquals(5, modelConfig.getFieldKeys().size());
        Model model = (Model) modelConfig.create();
        Assert.assertNotNull(model);
    }

}
