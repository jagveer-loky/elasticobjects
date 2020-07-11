package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.Models;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.M_MODEL_INTERFACE;

/**
 * Created by Werner on 23.05.2016.
 */
public class ModelsTest {
    private static final Logger LOG = LogManager.getLogger(ModelsTest.class);

    @Test
    public void withModelInterface()  {
        Models models = new Models(TestProviderRootTest.EO_CONFIGS, M_MODEL_INTERFACE);
        Assert.assertTrue(models.hasModel());
        Assert.assertFalse(models.hasChildModel());
        Assert.assertFalse(models.isEmpty());
    }


}
