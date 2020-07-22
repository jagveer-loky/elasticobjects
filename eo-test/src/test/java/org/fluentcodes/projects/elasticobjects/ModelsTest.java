package org.fluentcodes.projects.elasticobjects;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootDev;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.Map;

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
    @Test
    public void createChildModelsWithList() {
        EO eo = TestProviderRootDev.createEo();
        Models models = new Models(eo.getConfigsCache(), Map.class);
        Models childModels = models.createChildModels(eo, new Path("(ArrayList)list"), null);
        Assertions.assertThat(childModels.getModelClass()).isEqualTo(ArrayList.class);
    }

    @Test
    public void createArrayList() {
        EO eo = TestProviderRootDev.createEo();
        Models models = new Models(eo.getConfigsCache(), ArrayList.class);
        Assertions.assertThat(models.isCreate()).isTrue();
        Assertions.assertThat(models.isScalar()).isFalse();
    }


}
