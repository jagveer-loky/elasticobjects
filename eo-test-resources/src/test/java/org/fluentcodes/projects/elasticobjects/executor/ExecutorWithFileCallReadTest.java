package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_MAP_PATH;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_PATH;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by Werner on 08.10.2016,
 * Refactored 16.5.2018
 */
public class ExecutorWithFileCallReadTest extends TestHelper {


    @Test
    public void withMapPath()  {
        final EO eoContent = TestCallsProvider.executeExecutorFileRead(FILE_SOURCE_TXT, F_MAP_PATH, SAMPLE_CONTENT);
        Assert.assertEquals(S_STRING, eoContent.get(SAMPLE_CONTENT));
    }

    @Test
    public void withLongPathAndMapPath()  {
        EO eoContentWithPath = TestCallsProvider.executeExecutorFileRead(FILE_SOURCE_TXT, F_PATH, S_PATH2, F_MAP_PATH, SAMPLE_CONTENT);
        Assert.assertEquals(S_STRING, eoContentWithPath.get(toPath(S_PATH2, SAMPLE_CONTENT)));
    }

    @Test
    public void withPathAndMapPath()  {
        final EO adapter = TestCallsProvider.executeExecutorFileRead(FILE_SOURCE_TXT, F_PATH, S_LEVEL0, F_MAP_PATH, SAMPLE_CONTENT);
        Assert.assertEquals(S_STRING, adapter.get(toPath(S_LEVEL0, SAMPLE_CONTENT)));
    }

}
