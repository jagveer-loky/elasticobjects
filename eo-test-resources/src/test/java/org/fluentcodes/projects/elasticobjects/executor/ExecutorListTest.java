package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.calls.ValueCall;
import org.fluentcodes.projects.elasticobjects.calls.ValueCallTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EORoot;
import org.fluentcodes.projects.elasticobjects.eo.EOToJSON;
import org.fluentcodes.projects.elasticobjects.eo.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.test.AssertString;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.test.TestTemplateProvider;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_CONTENT;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_PATH;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class ExecutorListTest {
    public static ExecutorList createSampleExecutorList() throws Exception {
        ExecutorList executorList = new ExecutorList();
        executorList.add(ValueCall.createSetExecutor(ValueCallTest.VC_CONTENT, F_PATH, F_TEST_STRING));
        executorList.add(ValueCall.createSetExecutor(ValueCallTest.VC_EMPTY, F_PATH, F_TEST_DATE, F_CONTENT, SAMPLE_DATE));
        executorList.add(ValueCall.createSetExecutor(ValueCallTest.VC_INT_VALUE1, F_PATH, F_TEST_INTEGER));
        executorList.add(TestTemplateProvider.createExecutorTemplate(T_CONTENT_EXAMPLE));
        return executorList;
    }

    @Test
    public void callToString() throws Exception {
        ExecutorList executorList = createSampleExecutorList();
        String stringified = executorList.toString();
    }

    @Test
    public void toJSON() throws Exception {
        ExecutorList executorList = createSampleExecutorList();
        String json = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(TestEOProvider.EO_CONFIGS, executorList.getListMap());
        AssertString.compare(json);
    }

    @Test
    public void EOtoJSON() throws Exception {
        ExecutorList executorList = createSampleExecutorList();
        EO root = TestEOProvider.createEmptyMap();
        ((EORoot) root).setCalls(executorList);
        String jsn = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(root);
        AssertString.compare(jsn);
        EO fromJsn = TestEOProvider.createEOBuilder()
                .mapFile("src/test/resources/output/ExecutorListTest/EOtoJSON.string");
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS, fromJsn.hasCalls());
    }
}
