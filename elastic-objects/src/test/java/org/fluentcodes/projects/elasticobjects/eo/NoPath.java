package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Werner on 6.10.2018.
 */
public class NoPath extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(NoPath.class);

    @Test
    public void empty() throws Exception {
        TestHelper.printStartMethod();
        EOConfigsCache configsCache = TestEOProvider.EO_CONFIGS;
        EO adapter = TestEOProvider.createEmptyMap();
        Assert.assertEquals(Map.class, adapter.getModelClass());
        Assert.assertEquals(LinkedHashMap.class, adapter.get().getClass());
        Assert.assertTrue(adapter.keys().size() == 0);
    }

}
