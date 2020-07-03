package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.TestXlsxProvider;
import org.fluentcodes.projects.elasticobjects.calls.XlsxCall;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.XEO_STATIC_TEST.X_SOURCE_XLSX_TEST;

/**
 * Created by Werner on 18.04.2017.
 */
public class XlsxCallWithinEOTest extends TestHelper {


    @Test
    public void addFileXlsxReadAction()  {
        EO adapter = TestEOProvider.createEmptyMap();
        adapter
                .add(S_PATH1)
                .set(S_STRING);
        //TODO should work ;-) child = .createChildForMap("../");
        EO child = adapter.getChild(S_LEVEL0);

        CallExecutor executor = TestXlsxProvider.createExecutorXlsxActionRead(X_SOURCE_XLSX_TEST);
        child.addCall(executor);

        Assert.assertEquals(1, adapter.getCalls().getExecutorList().size());

        CallExecutor call = (CallExecutor) adapter.getCalls().getExecutorList().get(0);
        Assert.assertEquals(XlsxCall.class.getSimpleName() + ".read(source.xlsx:test)", call.getAction());
        Assert.assertEquals(Path.DELIMITER + S_LEVEL0, call.getPath());

        adapter.executeCalls();
        Assert.assertEquals(S_VALUE21, adapter.get(toPath(S_LEVEL0, S1, S_KEY1)));

        //TODO Serialization does not work properly
        // AssertEO.compare(adapter);
    }
}
