package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.MapProviderEO;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
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
    public void emptyNoCallTpl() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_EMPTY_NO_CALL));
    }

    @Test
    public void emptyWithCallAtStartStopTpl() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_EMPTY_WITH_CALL_AT_START_STOP));
    }


    @Test
    public void emptyWithCallTpl() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_EMPTY_WITH_CALL));
    }

    @Test
    public void emptyWithEmbeddedCallTpl() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_EMPTY_WITH_EMBEDDED_CALL));
    }

    @Test
    public void attributeWithCallTpl() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_WITH_CALL));
    }

    @Test
    public void attributeWithEmbeddedCallTpl() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_WITH_EMBEDDED_CALL));
    }

    @Test
    public void attributeGlobalTpl() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_GLOBAL));
    }

    @Test
    public void attributeGlobalParallelTpl() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_GLOBAL_PARALLEL));
    }

    @Test
    public void valuesWithCallAndPathTpl() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_VALUES_WITH_CALL_AND_PATH), MapProviderEO.createSimpleInsertWithPath());
    }

    @Test
    public void valuesNoCallNotExistingValue() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_VALUES_NO_CALL_NOT_EXISTING_VALUE), MapProviderEO.createSimpleInsertWithPath());
    }

    @Test
    public void valuesNoCall() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_VALUES_NO_CALL), MapProviderEO.createSimpleInsertWithPath());
    }


    @Test
    public void valuesWithCallDeeperPath() throws Exception {
        TestCallsProvider.assertExecutorTemplate(toPath(PATH_TEMPLATE_CONTENT, T_VALUES_WITH_CALL_DEEPER_PATH), MapProviderEO.createDeepPathValueAdapter());
    }


}
