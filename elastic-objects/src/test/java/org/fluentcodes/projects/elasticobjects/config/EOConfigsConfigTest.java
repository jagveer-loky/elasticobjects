package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;

import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

/**
 * Created by werner.diwischek on 09.12.17.
 */
public class EOConfigsConfigTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOConfigsConfigTest.class);

    @Test
    public void checkConfigsCache() throws Exception {
        ModelInterface cache = TestObjectProvider.EO_CONFIGS_CACHE.findModel(ModelConfigObject.class);
        cache.resolve();
        AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, cache);
    }

    @Test
    public void checkConfigsCacheWithST() throws Exception {
        ModelInterface cache = TestObjectProvider.EO_CONFIGS_CACHE.findModel(SubTest.class);
        cache.resolve();
        AssertEO.compare(TestObjectProvider.EO_CONFIGS_CACHE, cache);
    }

    // Just a first check for the dev scope working.
    @Test
    public void checkConfigsCacheDev() throws Exception {
        EOConfigsCache configsCache = new EOConfigsCache(Scope.DEV);
        ModelInterface model = configsCache.findModel(Map.class);
        model.resolve();
        EOBuilder builder = new EOBuilder(configsCache);
        EO adapter = builder.set(S_STRING);
        Assert.assertEquals(S_STRING, adapter.get());
        Assert.assertEquals(M_STRING, adapter.getModelClass().getSimpleName());
    }


}
