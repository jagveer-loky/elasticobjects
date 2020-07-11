package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

/**
 * Created by Werner on 9.7.2017.
 */
public class ModelConfigListTest {

    @Test
    public void readMainJackson()  {
        ModelConfigList listModel = (ModelConfigList) TestProviderRootTest.EO_CONFIGS.findModel(List.class);
        Assert.assertEquals(List.class, listModel.getModelClass());
        List list = (List) listModel.create();
    }
}
