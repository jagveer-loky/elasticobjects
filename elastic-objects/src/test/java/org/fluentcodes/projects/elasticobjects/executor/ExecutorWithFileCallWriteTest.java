package org.fluentcodes.projects.elasticobjects.executor;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 08.10.2016.
 */
public class ExecutorWithFileCallWriteTest extends TestHelper {

    @Test
    public void execute() throws Exception {
        final CallExecutor executor = TestCallsProvider.createExecutorFileWrite(FILE_TARGET_TXT, F_MAP_PATH, F_TEST_STRING);
        executor.execute(MapProviderEO.createString());

        final EO eoRead =  TestCallsProvider.executeExecutorFileRead(FILE_TARGET_TXT, F_MAP_PATH, SAMPLE_CONTENT);
        Assert.assertEquals(S_STRING, eoRead.get(SAMPLE_CONTENT));

    }

}
