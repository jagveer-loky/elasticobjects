package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelConfigObject;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_EXPECTED_EXCEPTION;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_EXPECTED_EXCEPTION_FAILS;

/**
 * Created by Werner on 9.7.2017.
 */
public class CallExecutorModelConfigTest {
    private static final Logger LOG = LogManager.getLogger(CallExecutorModelConfigTest.class);

    @Test
    public void createFromModelDoesNotWork()  {
        final ModelConfigObject executorActionModel = (ModelConfigObject) TestProviderRootTest.EO_CONFIGS.findModel(CallExecutorResource.class);
        Assert.assertEquals(CallExecutorResource.class, executorActionModel.getModelClass());
        try {
            executorActionModel.create();
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }


}
