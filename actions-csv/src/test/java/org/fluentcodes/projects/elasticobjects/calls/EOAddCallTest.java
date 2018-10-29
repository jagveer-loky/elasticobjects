package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.TestCsvProvider;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.M_CSV_CALL;
import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.CSV_SOURCE_CSV;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 18.04.2017.
 */
public class EOAddCallTest extends TestHelper {
    private static String METHOD_READ_SOUCE = ".read(source.csv)";

    @Test
    public void addFileCsvReadAction() throws Exception {
        TestHelper.printStartMethod();

        final EO adapter = TestObjectProvider.createEOFromJson();
        adapter.add(S_PATH1)
                .set(S_STRING);
        final EO child = adapter.getChild(S_LEVEL0);

        CallExecutor callExecutor = TestCsvProvider.createExecutorCsvActionRead(CSV_SOURCE_CSV);
        child.addCall(callExecutor);

        Assert.assertEquals(1, adapter.getCalls().getExecutorList().size());
        CallExecutor executor = (CallExecutor) adapter.getCalls().getExecutorList().get(0);
        Assert.assertEquals(M_CSV_CALL + METHOD_READ_SOUCE, executor.getAction());
        Assert.assertEquals(Path.DELIMITER + S_LEVEL0, executor.getPath());

        adapter.executeCalls();
        Assert.assertEquals(S_VALUE21, adapter.get(toPath(S_LEVEL0, S1, S_KEY1)));
        //TODO AssertEO.compare(adapter);
    }
}
