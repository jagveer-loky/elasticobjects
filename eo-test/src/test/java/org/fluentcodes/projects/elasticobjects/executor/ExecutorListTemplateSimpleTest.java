package org.fluentcodes.projects.elasticobjects.executor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.paths.Path.ofs;

/**
 * Created by Werner on 22.03.2017.
 */
public class ExecutorListTemplateSimpleTest {
    private static final Logger LOG = LogManager.getLogger(ExecutorListTemplateSimpleTest.class);


    @Test
    public void emptyNoCallTpl()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_EMPTY_NO_CALL));
    }

    @Test
    public void emptyWithCallAtStartStopTpl()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_EMPTY_WITH_CALL_AT_START_STOP));
    }


    @Test
    public void emptyWithCallTpl()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_EMPTY_WITH_CALL));
    }

    @Test
    public void emptyWithEmbeddedCallTpl()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_EMPTY_WITH_EMBEDDED_CALL));
    }

    @Test
    public void attributeWithCallTpl()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_WITH_CALL));
    }

    @Test
    public void attributeWithEmbeddedCallTpl()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_WITH_EMBEDDED_CALL));
    }

    @Test
    public void attributeGlobalTpl()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_GLOBAL));
    }

    @Test
    public void attributeGlobalParallelTpl()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_ATTRIBUTE_GLOBAL_PARALLEL));
    }

    @Test
    public void valuesWithCallAndPathTpl()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_VALUES_WITH_CALL_AND_PATH), TemplateTestObjectProvider.SIMPLE_INSERT_WITH_PATH.createEoData());
    }

    @Test
    public void valuesNoCallNotExistingValue()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_VALUES_NO_CALL_NOT_EXISTING_VALUE), TemplateTestObjectProvider.SIMPLE_INSERT_WITH_PATH.createEoData());
    }

    @Test
    public void valuesNoCall()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_VALUES_NO_CALL), TemplateTestObjectProvider.SIMPLE_INSERT_WITH_PATH.createEoData());
    }


    @Test
    public void valuesWithCallDeeperPath()  {
        //TestTemplateProvider.assertExecutorTemplate(ofs(PATH_TEMPLATE_CONTENT, T_VALUES_WITH_CALL_DEEPER_PATH), TemplateTestObjectProvider.DEEP_PATH_VALUE.createEoData());
    }


}
