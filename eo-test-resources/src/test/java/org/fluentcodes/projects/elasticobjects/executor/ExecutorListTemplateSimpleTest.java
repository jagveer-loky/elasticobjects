package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.fluentcodes.projects.elasticobjects.test.TestTemplateProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.toPath;

/**
 * Created by Werner on 22.03.2017.
 */
public class ExecutorListTemplateSimpleTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(ExecutorListTemplateSimpleTest.class);


    @Test
    public void emptyNoCallTpl()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_EMPTY_NO_CALL));
    }

    @Test
    public void emptyWithCallAtStartStopTpl()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_EMPTY_WITH_CALL_AT_START_STOP));
    }


    @Test
    public void emptyWithCallTpl()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_EMPTY_WITH_CALL));
    }

    @Test
    public void emptyWithEmbeddedCallTpl()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_EMPTY_WITH_EMBEDDED_CALL));
    }

    @Test
    public void attributeWithCallTpl()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_WITH_CALL));
    }

    @Test
    public void attributeWithEmbeddedCallTpl()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_WITH_EMBEDDED_CALL));
    }

    @Test
    public void attributeGlobalTpl()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_GLOBAL));
    }

    @Test
    public void attributeGlobalParallelTpl()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_GLOBAL_PARALLEL));
    }

    @Test
    public void valuesWithCallAndPathTpl()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_VALUES_WITH_CALL_AND_PATH), MapProviderEO.createSimpleInsertWithPath());
    }

    @Test
    public void valuesNoCallNotExistingValue()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_VALUES_NO_CALL_NOT_EXISTING_VALUE), MapProviderEO.createSimpleInsertWithPath());
    }

    @Test
    public void valuesNoCall()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_VALUES_NO_CALL), MapProviderEO.createSimpleInsertWithPath());
    }


    @Test
    public void valuesWithCallDeeperPath()  {
        TestTemplateProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_VALUES_WITH_CALL_DEEPER_PATH), MapProviderEO.createDeepPathValueAdapter());
    }


}
