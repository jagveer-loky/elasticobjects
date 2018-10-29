package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MODEL_KEY;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_MODEL_INTERFACE;


public class ModelConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelInterface.class);

    private EOBuilder createBuilder() throws Exception {
        return TestObjectProvider.createEOBuilder()
                .setLogLevel(LogLevel.WARN);
    }


    @Test
    public void withModelBasicTestMapNotEmpty() throws Exception {
        TestHelper.printStartMethod();
        ModelConfigObject model = (ModelConfigObject) TestObjectProvider.EO_CONFIGS_CACHE.findModel(ModelInterface.class);
        model.getModelClass();
        EO adapter = createBuilder()
                .setModels(ModelInterface.class)
                .set(model);
        Assert.assertEquals(M_MODEL_INTERFACE, adapter.get(F_MODEL_KEY));
    }


}