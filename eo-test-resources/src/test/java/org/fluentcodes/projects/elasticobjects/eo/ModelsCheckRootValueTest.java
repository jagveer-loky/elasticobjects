package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 23.05.2016.
 */
public class ModelsCheckRootValueTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsCheckRootValueTest.class);

    @Test
    public void withString_withNullAndMap_keepString() throws Exception {
        final Models models = new Models(DevObjectProvider.EO_CONFIGS, String.class);
        models.checkRootValue(null, true);
        Assert.assertEquals(String.class, models.getModelClass());
    }

    @Test
    public void withString_withIntegerAndMap_keepString() throws Exception {
        final Models models = new Models(DevObjectProvider.EO_CONFIGS, String.class);
        models.checkRootValue(S_INTEGER, true);
        Assert.assertEquals(String.class, models.getModelClass());
    }

    @Test
    public void withMap_withIntegerAndMap_fails() throws Exception {
        final Models models = new Models(DevObjectProvider.EO_CONFIGS, Map.class);
        try {
            models.checkRootValue(S_INTEGER, true);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void withMap_withIntegerAndSet_fails() throws Exception {
        final Models models = new Models(DevObjectProvider.EO_CONFIGS, Map.class);
        models.checkRootValue(S_INTEGER, false);
        Assert.assertEquals(Integer.class, models.getModelClass());
    }


}
