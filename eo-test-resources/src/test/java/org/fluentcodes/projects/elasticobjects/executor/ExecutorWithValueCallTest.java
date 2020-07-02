package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.calls.ValueCallTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.BTProvider;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 13.02.2017.
 */
public class ExecutorWithValueCallTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ExecutorWithValueCallTest.class);
    private static final String METHOD_SET_EMPTY = ".set(empty)";

    @Test
    public void intValue1_With_Path() throws Exception {
        final EO adapter = TestCallsProvider.executeExecutorValueCall(ValueCallTest.VC_INT_VALUE1, F_PATH, F_KEY);
        Assert.assertEquals(new Long(S_INTEGER), adapter.get(Path.DELIMITER + F_KEY));
    }

    @Test
    public void intValue1WithPathMapPathAndValue() throws Exception {
        final EO adapter = TestCallsProvider.executeExecutorValueCall(ValueCallTest.VC_INT_VALUE1, F_PATH, S_LEVEL0, F_MAP_PATH, S_LEVEL1, F_VALUE, S_STRING);
        Assert.assertEquals(new Long(S_INTEGER), adapter.get(toPath(S_LEVEL0, S_LEVEL1)));
    }

    @Test
    public void keyNotExists_createLogEntry() throws Exception {
        EO eoUnknown = TestCallsProvider.executeExecutorValueCall(SAMPLE_KEY_UNKNOW);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, eoUnknown.getLog().isEmpty());
    }

    @Test
    public void intValue1WithMapPathAndValue() throws Exception {
        final EO eoInt1 = TestCallsProvider.executeExecutorValueCall(ValueCallTest.VC_INT_VALUE1, F_MAP_PATH, S_LEVEL0, F_VALUE, S_STRING);
        Assert.assertEquals(new Long(S_INTEGER), eoInt1.get(S_LEVEL0));
    }

    @Test
    public void testItemWithPathPathAndValue_ignoresValue() throws Exception {
        final EO adapter = TestCallsProvider.executeExecutorValueCall(ValueCallTest.VC_TEST_ITEM, F_PATH, F_TEST_STRING, F_VALUE, S_STRING_OTHER);
        Assert.assertEquals(S_STRING, adapter.get(F_TEST_STRING));
        Assert.assertEquals(Map.class, adapter.getModelClass());
    }

    @Test
    public void testItemWithPathPath() throws Exception {
        final EO adapter = TestCallsProvider.executeExecutorValueCall(ValueCallTest.VC_TEST_ITEM, F_PATH, F_TEST_STRING);
        Assert.assertEquals(S_STRING, adapter.get(F_TEST_STRING));
    }


    @Test
    public void emptyWithBT() throws Exception {
        EO eoBT = TestCallsProvider
                .executeExecutorValueCall(ValueCallTest.VC_EMPTY, F_MAP_PATH, F_BASIC_TEST, F_VALUE, BTProvider.createString());
        Assert.assertEquals(Map.class, eoBT.getModelClass());
        Assert.assertEquals(BasicTest.class, eoBT.getChild(F_BASIC_TEST).getModelClass());
        Assert.assertEquals(S_STRING, ((BasicTest) eoBT.get(F_BASIC_TEST)).getTestString());
        Assert.assertEquals(S_STRING, eoBT.get(toPath(F_BASIC_TEST, F_TEST_STRING)));
    }
}
