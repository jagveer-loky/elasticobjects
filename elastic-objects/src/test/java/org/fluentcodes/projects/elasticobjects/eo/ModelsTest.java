package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 23.05.2016.
 */
public class ModelsTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsTest.class);

    @Test
    public void withModelInterface() throws Exception {
        Models models = new Models(TestObjectProvider.EO_CONFIGS_CACHE, M_MODEL_INTERFACE);
        Assert.assertTrue(models.hasModel());
        Assert.assertFalse(models.hasChildModel());
        Assert.assertFalse(models.isEmpty());
    }


}
