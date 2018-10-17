package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigListTest extends TestHelper {

    @Test
    public void readMainJackson() throws Exception {
        ModelConfigList listModel = (ModelConfigList) TestObjectProvider.EO_CONFIGS_CACHE.findModel(List.class);
        Assert.assertEquals(List.class, listModel.getModelClass());
        List list = (List) listModel.create();
    }
}
