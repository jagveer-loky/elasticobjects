package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.calls.FileCallRead;
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
        final CallExecutorResource executor = new CallExecutorResource(new FileCallRead(FILE_SOURCE_TXT));
        executor.setTargetPath(SAMPLE_CONTENT);
        final EO eo = TestProviderRootTest.createEo();
        executor.execute(eo);
        Assert.assertEquals(S_STRING, eo.get(SAMPLE_CONTENT));
    }

    @Test
    public void withLongPathAndMapPath()  {
        final String path = Path.ofs(S_PATH2, SAMPLE_CONTENT);
        final CallExecutorResource executor = new CallExecutorResource(new FileCallRead(FILE_SOURCE_TXT));
        executor.setTargetPath(path);
        final EO eo = TestProviderRootTest.createEo();
        executor.execute(eo);
        Assert.assertEquals(S_STRING, eo.get(path));
    }
}
