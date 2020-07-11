package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by Werner on 6.10.2018.
 */
public class NoPath {
    private static final Logger LOG = LogManager.getLogger(NoPath.class);

    @Test
    public void empty()  {
        
        EOConfigsCache configsCache = TestProviderRootTest.EO_CONFIGS;
        EO adapter = TestProviderRootTest.createEo();
        Assert.assertEquals(Map.class, adapter.getModelClass());
        Assert.assertEquals(LinkedHashMap.class, adapter.get().getClass());
        Assert.assertTrue(adapter.keys().size() == 0);
    }

}
