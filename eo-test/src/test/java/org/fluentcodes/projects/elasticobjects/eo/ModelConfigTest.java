package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.config.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_MODEL_INTERFACE;


public class ModelConfigTest {
    private static final Logger LOG = LogManager.getLogger(ModelInterface.class);


    @Test
    public void withModelBasicTestMapNotEmpty()  {
        
        ModelConfigObject model = (ModelConfigObject) TestProviderRootTest.EO_CONFIGS.findModel(ModelInterface.class);
        model.getModelClass();
        EO adapter = TestProviderRootTest.createEo(ModelInterface.class);
        adapter.mapObject(model);
        Assert.assertEquals(M_MODEL_INTERFACE, adapter.get(F_MODEL_KEY));
    }


}