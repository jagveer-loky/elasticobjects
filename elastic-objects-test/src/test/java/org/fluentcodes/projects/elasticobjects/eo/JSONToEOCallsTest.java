package org.fluentcodes.projects.elasticobjects.eo;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.JSONInputReader;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.18.
 */

public class JSONToEOCallsTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(JSONToEOCallsTest.class);
    private static final String SCS_CALL_SOURCE = "ScsCallSource";
    private static final String SCS_CALL_SOURCE_PATH = "ScsCallSourcePath";
    private static final String SCS_CALL_SOURCE_JOINED = "ScsCallSourceJoined";

    @Test
    public void scsCallSource()  {
        TestObjectProvider.createEOBoolean(); // just to load initial values
        final String scsCallSource = JSONInputReader.readTestInputJSN(SCS_CALL_SOURCE);
        EO eoScs = TestObjectProvider.createEOFromJson(scsCallSource);
        Assert.assertTrue(eoScs.hasCalls());
        eoScs.executeCalls();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eoScs.getChild(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eoScs.getChild(S0).get(S_KEY1));
        AssertEO.compare(eoScs);
    }

    @Test
    public void scsCallSourcePath()  {
        TestObjectProvider.createEOBoolean(); // just to load initial values
        final String scsCallSource = JSONInputReader.readTestInputJSN(SCS_CALL_SOURCE_PATH);
        EO eoScs = TestObjectProvider.createEOFromJson(scsCallSource);
        Assert.assertTrue(eoScs.hasCalls());
        eoScs.executeCalls();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eoScs.getChild(S_PATH1 + Path.DELIMITER + S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eoScs.getChild(S_PATH1 + Path.DELIMITER + S0).get(S_KEY1));
        AssertEO.compare(eoScs);
    }

    @Test
    public void scsCallSourceJoined()  {
        TestObjectProvider.createEOBoolean(); // just to load initial values
        final String scsCallSource = JSONInputReader.readTestInputJSN(SCS_CALL_SOURCE_JOINED);
        EO eoScs = TestObjectProvider.createEOFromJson(scsCallSource);
        Assert.assertTrue(eoScs.hasCalls());
        eoScs.executeCalls();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eoScs.getChild(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eoScs.getChild(S0).get(S_KEY1));
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eoScs.getChild(S_PATH1 + Path.DELIMITER + S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eoScs.getChild(S_PATH1 + Path.DELIMITER + S0).get(S_KEY1));
        AssertEO.compare(eoScs);
    }

}
