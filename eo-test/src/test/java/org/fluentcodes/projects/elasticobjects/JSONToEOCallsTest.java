package org.fluentcodes.projects.elasticobjects;


import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.paths.Path;

import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderMapJson;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 27.10.18.
 */

public class JSONToEOCallsTest {
    private static final Logger LOG = LogManager.getLogger(JSONToEOCallsTest.class);
    private static final String SCS_CALL_SOURCE = "ScsCallSource";
    private static final String SCS_CALL_SOURCE_PATH = "ScsCallSourcePath";
    private static final String SCS_CALL_SOURCE_JOINED = "ScsCallSourceJoined";

    @Test
    public void scsCallSource()  {
        // just to load initial values
        final String scsCallSource = TestProviderMapJson.SCS_CALL_SOURCE.content();
        EO eoScs = TestProviderRootTest.createEo(scsCallSource);
        Assert.assertTrue(eoScs.hasCalls());
        eoScs.execute();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eoScs.getEo(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eoScs.getEo(S0).get(S_KEY1));
        //AssertEO.compare(eoScs);
    }

    @Test
    public void scsCallSourcePath()  {
        final String scsCallSource = TestProviderMapJson.SCS_CALL_SOURCE_PATH.content();;
        EO eoScs = TestProviderRootTest.createEo(scsCallSource);
        Assert.assertTrue(eoScs.hasCalls());
        eoScs.execute();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eoScs.getEo(S_PATH1 + Path.DELIMITER + S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eoScs.getEo(S_PATH1 + Path.DELIMITER + S0).get(S_KEY1));
        //AssertEO.compare(eoScs);
    }

    @Test
    public void scsCallSourceJoined()  {
        // just to load initial values
        final String scsCallSource = TestProviderMapJson.SCS_CALL_SOURCE_JOINED.content();;
        EO eoScs = TestProviderRootTest.createEo(scsCallSource);
        Assert.assertTrue(eoScs.hasCalls());
        eoScs.execute();
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eoScs.getEo(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eoScs.getEo(S0).get(S_KEY1));
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eoScs.getEo(S_PATH1 + Path.DELIMITER + S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eoScs.getEo(S_PATH1 + Path.DELIMITER + S0).get(S_KEY1));
        //AssertEO.compare(eoScs);
    }

}
