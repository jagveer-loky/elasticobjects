package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.calls.FileCallRead;
import org.fluentcodes.projects.elasticobjects.calls.FileCallWrite;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.test.TestProviderMapJson;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 08.10.2016.
 */
public class ExecutorWithFileCallWriteTest {

    @Test
    public void execute()  {
        final CallExecutorResource executor = new CallExecutorResource(new FileCallWrite(FILE_TARGET_TXT));
        executor.setSourcePath(F_TEST_STRING);
        EO eo = TestProviderMapJson.STRING.createMapEo();
        //FILE_TARGET_TXT, F_MAP_PATH, F_TEST_STRING);
        executor.execute(eo);

        final CallExecutorResource readExecutor = new CallExecutorResource(new FileCallRead(FILE_TARGET_TXT));
        executor.setTargetPath(SAMPLE_CONTENT);
        readExecutor.execute(eo);
        Assert.assertEquals(S_STRING, eo.get(SAMPLE_CONTENT));

    }

}
