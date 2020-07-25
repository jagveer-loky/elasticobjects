package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.files.FileCallRead;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 08.10.2016,
 * Refactored 16.5.2018
 */
public class ExecutorWithFileCallReadTest {


    @Test
    public void withMapPath()  {
        Call call = new FileCallRead(FILE_SOURCE_TXT)
                .setTargetPath(SAMPLE_CONTENT);
        final EO eo = TestProviderRootTest.createEo();
        eo.addCall(call);
        eo.execute();
        Assert.assertEquals(S_STRING, eo.get(SAMPLE_CONTENT));
    }

    @Test
    public void withLongPathAndMapPath()  {
        final String path = Path.ofs(S_PATH2, SAMPLE_CONTENT);
        Call call = new FileCallRead(FILE_SOURCE_TXT).setTargetPath(path);
        final EO eo = TestProviderRootTest.createEo();
        eo.addCall(call);
        eo.execute();
        Assert.assertEquals(S_STRING, eo.get(path));
    }
}
