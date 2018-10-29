package org.fluentcodes.projects.elasticobjects.eo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.JSONInputReader;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class JSONToEOMapTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(JSONToEOMapTest.class);

    @Test
    public void test() throws Exception {
        EO eoMap = DevObjectProvider
                .createEOBuilder()
                .map("{}");
        Assert.assertEquals(Map.class, eoMap.getModelClass());
    }

    @Test
    public void testFromFile() throws Exception {
        EO eoMap = DevObjectProvider.createEOFromJsonMapKey(JSONInputReader.EMPTY);
        Assert.assertEquals(Map.class, eoMap.getModelClass());
    }

    @Test
    public void testDouble() throws Exception {
        EO eoMap = DevObjectProvider.createEOFromJsonMapKey(JSONInputReader.DOUBLE);
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eoMap.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, SAMPLE_DOUBLE, eoMap.get(F_TEST_DOUBLE));
    }

    @Test
    public void testSmall() throws Exception {
        EO eoMap = DevObjectProvider.createEOFromJsonMapKey(JSONInputReader.SMALL);
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eoMap.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMap.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.longValue(), eoMap.get(F_TEST_INTEGER));
    }

    @Test
    public void testAll() throws Exception {
        EO eoMap = DevObjectProvider.createEOFromJsonMapKey(JSONInputReader.ALL);
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eoMap.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMap.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.longValue(), eoMap.get(F_TEST_INTEGER));
        AssertEO.compareJSON(eoMap);
    }
}
