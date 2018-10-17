package org.fluentcodes.projects.elasticobjects.executor;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.calls.FileCall;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 18.04.2017.
 */
public class EOWithFileCallTest extends TestHelper {
    private static final String METHOD_SOURCE_TXT = ".read(source.txt)";
    @Test
    public void addFileReadAction() throws Exception {
        final EO root = TestObjectProvider.createEO();
        root.add(toPath(S_LEVEL0, S_LEVEL1))
                .set(S_STRING);
        final EO child = root.getChild(S_LEVEL0);

        final CallExecutor executor = TestCallsProvider.createExecutorFileRead(FILE_SOURCE_TXT, F_MAP_PATH, SAMPLE_CONTENT);
        child.addCall(executor);

        Assert.assertEquals(1, root.getCalls().getExecutorList().size());
        CallExecutor callExecutor = (CallExecutor) root.getCalls().getExecutorList().get(0);
        Assert.assertEquals(FileCall.class.getSimpleName() + METHOD_SOURCE_TXT, callExecutor.getAction());
        Assert.assertEquals(Path.DELIMITER + S_LEVEL0, callExecutor.getPath());

        root.executeActions();
        Assert.assertEquals(S_STRING, root.get(toPath(S_LEVEL0, SAMPLE_CONTENT)));

    }
}
