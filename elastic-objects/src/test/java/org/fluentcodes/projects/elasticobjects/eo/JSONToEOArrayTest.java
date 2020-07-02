package org.fluentcodes.projects.elasticobjects.eo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.JSONInputReader;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.2018.
 */

public class JSONToEOArrayTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(JSONToEOArrayTest.class);

    @Test
    public void test() throws Exception {
        EO eoList = DevObjectProvider
                .createEOBuilder()
                .map("[]");
        Assert.assertEquals(List.class, eoList.getModelClass());
    }

    @Test
    public void testFromFile() throws Exception {
        EO eoList = DevObjectProvider.createEOFromJsonListKey(JSONInputReader.EMPTY);
        Assert.assertEquals(List.class, eoList.getModelClass());
    }

    @Test
    public void testDouble() throws Exception {
        EO eoList = DevObjectProvider.createEOFromJsonListKey(JSONInputReader.DOUBLE);
        Assert.assertEquals(INFO_COMPARE_FAILS, List.class, eoList.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, SAMPLE_DOUBLE, eoList.get(S0));
    }

    @Test
    public void testSmall() throws Exception {
        EO eoList = DevObjectProvider.createEOFromJsonListKey(JSONInputReader.SMALL);
        Assert.assertEquals(INFO_COMPARE_FAILS, List.class, eoList.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoList.get(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.longValue(), eoList.get(S1));
    }

    @Test
    public void testAll() throws Exception {
        EO eoList = DevObjectProvider.createEOFromJsonListKey(JSONInputReader.ALL);
        Assert.assertEquals(INFO_COMPARE_FAILS, List.class, eoList.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoList.get(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.longValue(), eoList.get(S1));
        AssertEO.compareJSON(eoList);
    }

    @Test
    public void checkListModel_Calls() throws Exception {
        final String toParse = "{\n" +
                "\"(List)_calls\":\n" +
                "  {\n" +
                "    \"0\": {\"execute\": \"testCall()\"}\n" +
                "  }\n" +
                "}";
        EO eo = new EOBuilder(TestEOProvider.EO_CONFIGS)
                .map(toParse);
        String log = eo.getLog();
        Assert.assertFalse(eo.getLog().isEmpty());
    }

    @Test
    public void checkListModelNoCalls() throws Exception {
        final String toParse = "{\n" +
                "\"(List)noCalls\":\n" +
                "  {\n" +
                "    \"0\": \"test\",\n" +
                "    \"1\": 1\n" +
                "  }\n" +
                "}";
        EO eo = new EOBuilder(TestEOProvider.EO_CONFIGS)
                .map(toParse);
        Assert.assertEquals(ArrayList.class, eo.get("noCalls").getClass());
        Assert.assertEquals("test",eo.get("noCalls/0"));
    }
}
