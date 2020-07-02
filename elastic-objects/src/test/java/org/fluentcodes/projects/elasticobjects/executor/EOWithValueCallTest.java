package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.calls.ValueCall;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MAP_PATH;
import static org.fluentcodes.projects.elasticobjects.calls.ValueCallTest.VC_INT_VALUE1;
import static org.fluentcodes.projects.elasticobjects.calls.ValueCallTest.VC_TEST_ITEM;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 13.02.2017.
 */
public class EOWithValueCallTest extends TestHelper {

    @Test
    public void testItem() throws Exception {
        TestHelper.printStartMethod();
        final EO root = TestEOProvider.createEmptyMap();
        final CallExecutor executor = ValueCall.createSetExecutor(VC_TEST_ITEM, F_MAP_PATH, F_TEST_STRING);
        root.addCall(executor);
        root.executeCalls();
        Assert.assertEquals(S_STRING, root.get(F_TEST_STRING));
        String stringified = executor.toString();
    }

    @Test
    public void testItemAddedToChild() throws Exception {
        final EO root = TestEOProvider.createEmptyMap();
        final EO child = root
                .add(toPath(S_LEVEL0, S_LEVEL1))
                .build();
        child.addCall(ValueCall.createSetExecutor(VC_TEST_ITEM));
        child.executeCalls();
        Assert.assertEquals(S_STRING, child.get());
        Assert.assertEquals(S_STRING, child.getRoot().get(toPath(S_LEVEL0, S_LEVEL1)));
    }

    @Test
    public void add2ActionsToAdapter() throws Exception {
        EO root = TestEOProvider.createEmptyMap();
        EO child = root.add(toPath(S_LEVEL0, S_LEVEL1)).build();

        final CallExecutor callExecutor1 = ValueCall.createSetExecutor(VC_TEST_ITEM, F_MAP_PATH, F_TEST_STRING);
        final CallExecutor callExecutor2 = ValueCall.createSetExecutor(VC_INT_VALUE1, F_MAP_PATH, F_TEST_INTEGER);

        child.addCall(callExecutor1);
        root
                .getChild(S_LEVEL0)
                .addCall(callExecutor2);
        child.executeCalls();
        Assert.assertEquals(S_STRING, root.get(toPath(S_LEVEL0, S_LEVEL1, F_TEST_STRING)));
        Assert.assertEquals(new Long(1), root.get(toPath(S_LEVEL0, F_TEST_INTEGER)));
    }

}
