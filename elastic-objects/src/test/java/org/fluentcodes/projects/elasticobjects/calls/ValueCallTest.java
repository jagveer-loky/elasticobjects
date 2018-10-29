package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MAP_PATH;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_VALUE;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 13.4.2017.
 */
public class ValueCallTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ValueCallTest.class);


    @Test
    public void modelConfig() throws Exception {
        final ModelInterface modelConfig = TestObjectProvider.EO_CONFIGS_CACHE.findModel(ValueCall.class);
        Assert.assertEquals(1, modelConfig.getFieldKeys().size());
        modelConfig.resolve();
        Assert.assertEquals(3, modelConfig.getFieldKeys().size());
    }

    @Test
    public void withValueAndMapPath_valueWillNotSet() throws Exception {
        final EO eoIntValue1 = TestCallsProvider.createValueCallEO(VC_INT_VALUE1, F_VALUE, S_STRING, F_MAP_PATH, F_TEST_STRING);
        Assert.assertEquals(new Long(S_INTEGER), eoIntValue1.get(F_TEST_STRING));
    }

    @Test
    public void setNullEO() throws Exception {
        final ValueCall action = TestCallsProvider.createValueCall(VC_INT_VALUE1);
        try {
            action.set(null);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void intValue1_default() throws Exception {
        final EO eo = TestCallsProvider.createValueCallWithEOEmpty(VC_INT_VALUE1);
        Assert.assertEquals(new Long(S_INTEGER), eo.get());
    }


    @Test
    public void emptyWithMapPathAndValue() throws Exception {
        final EO eoEmpty = TestCallsProvider.createValueCallEO(VC_EMPTY, F_VALUE, S_STRING, F_MAP_PATH, F_TEST_STRING);
        Assert.assertEquals(S_STRING, eoEmpty.get(F_TEST_STRING));
    }

    @Test
    public void content_default() throws Exception {
        final EO eo = TestCallsProvider.createValueCallWithEOEmpty(VC_CONTENT);
        ;
        Assert.assertEquals(S_STRING, eo.get(SAMPLE_CONTENT));
    }
}
