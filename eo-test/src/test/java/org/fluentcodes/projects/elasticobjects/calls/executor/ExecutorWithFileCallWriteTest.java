package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.files.FileCallRead;
import org.fluentcodes.projects.elasticobjects.calls.files.FileCallWrite;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.fileprovider.TestProviderMapJson;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 08.10.2016.
 */
public class ExecutorWithFileCallWriteTest {

    @Test
    public void execute()  {
        Call callWrite = new FileCallWrite(FILE_TARGET_TXT)
                .setSourcePath(F_TEST_STRING);
        EO eo = TestProviderMapJson.STRING.createMapEo();
        //FILE_TARGET_TXT, F_MAP_PATH, F_TEST_STRING);
        eo.addCall(callWrite);
        eo.execute();

        Call callRead = new FileCallRead(FILE_TARGET_TXT)
                .setTargetPath(SAMPLE_CONTENT);
        //new CallExecutorResource().execute(eo, callRead);
        Assert.assertEquals(S_STRING, eo.get(SAMPLE_CONTENT));

    }

}
