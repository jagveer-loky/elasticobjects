package org.fluentcodes.projects.elasticobjects.calls;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Ignore;
import org.junit.Test;

/**
 * Created by Werner on 22.03.2017.
 */
public class ExecutorListTemplateModelsTest {
    private static final Logger LOG = LogManager.getLogger(ExecutorListTemplateModelsTest.class);

    @Test
    public void callModelWithLoopPathAll()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_MODEL_WITH_LOOP_PATH));
    }

    @Test
    public void callModelWithPathAndLoopPathAll()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_MODEL_WITH_PATH_AND_LOOP_PATH));
    }

    @Test
    public void callModelWithLoopPathAndEmbeddedIf()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_MODEL_WITH_LOOP_PATH_AND_EMBEDDED_IF));
    }

    @Ignore
    // TODO seems to be a build test...
    @Test
    public void modelWithLoopPathAndEmbeddedIfKeep()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_MODEL_WITH_LOOP_PATH_AND_EMBEDDED_IF_KEEP));
    }
}
