package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EORoot;
import org.fluentcodes.projects.elasticobjects.eo.EOToJSON;
import org.fluentcodes.projects.elasticobjects.eo.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.test.AssertEO;
import org.fluentcodes.projects.elasticobjects.test.AssertString;
import org.fluentcodes.projects.elasticobjects.test.TestCallsProvider;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.*;
import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

public class ExecutorListTest {
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
                .toJSON(TestObjectProvider.EO_CONFIGS_CACHE, executorList.getListMap());
        AssertString.compare(json);
    }

    @Test
    public void EOtoJSON() throws Exception {
        ExecutorList executorList = createSampleExecutorList();
        EO root = TestObjectProvider.createEO();
        ((EORoot) root).setActions(executorList);
        String jsn = new EOToJSON()
                .setStartIndent(1)
                .setSerializationType(JSONSerializationType.EO)
                .toJSON(root);
        AssertString.compare(jsn);
        EO fromJsn = TestObjectProvider.createEOBuilder()
            .mapFile("src/test/resources/output/ExecutorListTest/EOtoJSON.string");
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS, fromJsn.hasActions());
    }

    public static ExecutorList createSampleExecutorList() throws Exception {
        ExecutorList executorList = new ExecutorList();
        executorList.add(TestCallsProvider.createExecutorValueCall(VC_CONTENT,F_PATH, F_TEST_STRING));
        executorList.add(TestCallsProvider.createExecutorValueCall(VC_EMPTY,F_PATH, F_TEST_DATE, F_CONTENT, SAMPLE_DATE));
        executorList.add(TestCallsProvider.createExecutorValueCall(VC_INT_VALUE1, F_PATH, F_TEST_INTEGER));
        executorList.add(TestCallsProvider.createExecutorTemplate(T_CONTENT_EXAMPLE));
        return executorList;
    }
}
