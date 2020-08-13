package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.TestCsvProvider;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestEOProvider;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.M_CSV_CALL;
import static org.fluentcodes.projects.elasticobjects.CEO_STATIC_TEST.CSV_SOURCE_CSV;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 18.04.2017.
 */
public class EOAddCallTest {
    private static String METHOD_READ_SOUCE = ".read(ListSimple.csv)";

    @Test
    public void addFileCsvReadAction()  {
        

        final EO adapter = TestEOProvider.createEmptyMap();
        adapter.add(S_PATH1)
                .set(S_STRING);
        final EO child = adapter.getChild(S_LEVEL0);

        ExecutorCall executorCall = TestCsvProvider.createCallExecutorRead(CSV_SOURCE_CSV);
        child.addCall(executorCall);

        Assert.assertEquals(1, adapter.getCalls().getExecutorList().size());
        ExecutorCall executor = (ExecutorCall) adapter.getCalls().getExecutorList().get(0);
        Assert.assertEquals(M_CSV_CALL + METHOD_READ_SOUCE, executor.getAction());
        Assert.assertEquals(Path.DELIMITER + S_LEVEL0, executor.getPath());

        adapter.executeCalls();
        Assert.assertEquals(S_VALUE21, adapter.get(toPath(S_LEVEL0, S1, S_KEY1)));
        //TODO //AssertEO.compare(adapter);
    }
}
