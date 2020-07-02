package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 28.10.2018.
 */
public class ScsCallTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ScsCallTest.class);

    @Test
    public void readSourceCsvGuest_ok() throws Exception {
        final ScsCall call = TestCallsProvider.createScsCall(CS_SOURCE_CSV);
        EO eo = TestEOProvider.create();
        eo.setRoles(R_GUEST);
        call.read(eo);
        TestObjectProvider.checkLogEmpty(eo);
        Assert.assertNotNull(INFO_NOT_NULL_FAILS, eo.getChild(S0));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_VALUE11, eo.getChild(S0).get(S_KEY1));
    }

    @Test
    public void readSourceCsvAnonym_hasLog() throws Exception {
        final ScsCall call = TestCallsProvider.createScsCall(CS_SOURCE_CSV);
        EO eo = TestEOProvider.create();
        eo.setRoles(R_ANONYM);
        call.read(eo);
        TestObjectProvider.checkLogNotEmpty(eo);
    }
}
