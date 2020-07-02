package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorProvider;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MAP_PATH;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_VALUE;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 13.4.2017.
 */
public class ValueCallTest extends TestHelper {
    public static final String VC_INT_VALUE1 = "intValue1";
    public static final String VC_EMPTY = "empty";
    public static final String VC_CONTENT = "content";
    public static final String VC_TEST_ITEM = "testItem";
    private static final Logger LOG = LogManager.getLogger(ValueCallTest.class);


    @Test
    public void modelConfig() throws Exception {
        final ModelInterface modelConfig = TestEOProvider.EO_CONFIGS.findModel(ValueCall.class);
        Assert.assertEquals(1, modelConfig.getFieldKeys().size());
        modelConfig.resolve();
        Assert.assertEquals(3, modelConfig.getFieldKeys().size());
    }

    @Test
    public void intValue1_withoutParameter() throws Exception {
        final EO eo = ExecutorProvider.execute(
                ValueCall.createSetExecutor(VC_INT_VALUE1)
        );
        Assert.assertEquals(new Long(S_INTEGER), eo.get());
    }

    @Test
    public void intValues1_withValue_andMapPath() throws Exception {
        final EO eo = ExecutorProvider.execute(
            ValueCall.createSetExecutor(VC_INT_VALUE1),
                F_VALUE, S_STRING,
                F_MAP_PATH, F_TEST_STRING
        );
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_STRING));
    }

    @Test
    public void intValues1_withValue2_andMapPath() throws Exception {
        final EO eo = ExecutorProvider.execute(
                ValueCall.createSetExecutor(VC_INT_VALUE1,F_VALUE, S_STRING),
                F_MAP_PATH, F_TEST_STRING
        );
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_STRING));
    }

    @Test
    public void setNullEO() throws Exception {
        final ValueCall call = new ValueCall(TestEOProvider.EO_CONFIGS, VC_INT_VALUE1);
        try {
            call.set(null);
            Assert.fail(INFO_EXPECTED_EXCEPTION_FAILS);
        } catch (Exception e) {
            LOG.info(INFO_EXPECTED_EXCEPTION + e.getMessage());
        }
    }

    @Test
    public void empty_withMapPathAndValue() throws Exception {
        final EO eo = ExecutorProvider.execute(
                ValueCall.createSetExecutor(VC_EMPTY,F_VALUE, S_STRING),
                F_MAP_PATH, F_TEST_STRING
        );
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
    }

    @Test
    public void content_default() throws Exception {
        final EO eo = ExecutorProvider.execute(
                ValueCall.createSetExecutor(VC_CONTENT)
        );
        Assert.assertEquals(S_STRING, eo.get(SAMPLE_CONTENT));
    }
}
