package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorListTemplate;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.M_CSV_CALL;

public class TestCsvProvider {
    public static CallExecutor createExecutorCsvActionRead(final String key) throws Exception {
        return createExecutorCsvActionRead(new HashMap(), key);
    }

    public static CallExecutor createExecutorCsvActionRead(Map attributes, final String key) throws Exception {
        attributes.put(ExecutorListTemplate.EXECUTE, M_CSV_CALL + ".read(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeCsvActionRead(Map attributes, final String configKey) throws Exception {
        CallExecutor callExecutor = createExecutorCsvActionRead(attributes, configKey);
        EO adapter = TestObjectProvider.createEOFromJson();
        callExecutor.execute(adapter);
        return adapter;
    }

    public static CallExecutor createExecutorCsvActionWrite(final String key) throws Exception {
        return createExecutorCsvActionWrite(new HashMap(), key);
    }

    public static CallExecutor createExecutorCsvActionWrite(Map attributes, final String key) throws Exception {
        attributes.put(ExecutorListTemplate.EXECUTE, M_CSV_CALL + ".write(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeCsvActionWrite(Map attributes, final String configKey) throws Exception {
        CallExecutor callExecutor = createExecutorCsvActionWrite(attributes, configKey);
        EO adapter = TestObjectProvider.createEOFromJson();
        callExecutor.execute(adapter);
        return adapter;
    }
}
