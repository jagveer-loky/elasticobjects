package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_BASIC_TEST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.F_TEST_STRING;


/**
 * Created by Werner on 06.09.2018.
 */
public class ModelsScalarTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ModelsScalarTest.class);

    @Test
    public void withString() throws Exception {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, String.class);
        Assert.assertEquals(String.class, models.getModelClass());
        Assert.assertEquals(Object.class, models.getChildModelClass());
        final Models child = models.createChild(F_TEST_STRING);

    }

    @Test
    public void withBTString() throws Exception {
        Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class, String.class);
        Assert.assertEquals(Object.class, models.getChildModelClass());
    }

    @Test
    public void withMapBT() throws Exception {
        final Models models = new Models(TestEOProvider.EO_CONFIGS, Map.class, BasicTest.class);
        Assert.assertEquals(Map.class, models.getModelClass());
        Assert.assertEquals(BasicTest.class, models.getChildModelClass());
        Assert.assertEquals(String.class, models.createChild(F_BASIC_TEST).createChild(F_TEST_STRING).getModelClass());
    }

    @Test
    public void withBT_childTestString() throws Exception {
        Models models = new Models(TestEOProvider.EO_CONFIGS, BasicTest.class);
        Assert.assertEquals(BasicTest.class, models.getModelClass());
        Assert.assertEquals(String.class, models.createChild(F_TEST_STRING).getModelClass());
    }
}
