package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 22.03.2017.
 */
public class ExecutorListTemplateModelsTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ExecutorListTemplateModelsTest.class);

    @Test
    public void callModelWithLoopPathAll() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT,T_MODEL_WITH_LOOP_PATH));
    }

    @Test
    public void callModelWithPathAndLoopPathAll() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT,T_MODEL_WITH_PATH_AND_LOOP_PATH));
    }

    @Test
    public void callModelWithLoopPathAndEmbeddedIf() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT,T_MODEL_WITH_LOOP_PATH_AND_EMBEDDED_IF));
    }

    @Ignore
    // TODO seems to be a build test...
    @Test
    public void modelWithLoopPathAndEmbeddedIfKeep() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT,T_MODEL_WITH_LOOP_PATH_AND_EMBEDDED_IF_KEEP));
    }
}
