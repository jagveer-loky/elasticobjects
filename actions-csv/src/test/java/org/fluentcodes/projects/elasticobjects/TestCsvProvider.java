package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.calls.executor.Executor;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestEOProvider;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.M_CSV_CALL;

public class TestCsvProvider {
    public static ExecutorCall createCallExecutorRead(final String key)  {
        return createCallExecutorRead(new HashMap(), key);
    }

    public static ExecutorCall createCallExecutorRead(Map attributes, final String key)  {
        attributes.put(Executor.EXECUTE, M_CSV_CALL + ".read(" + key + ")");
        return new ExecutorCall(attributes);
    }

    public static EO executeRead(Map attributes, final String configKey)  {
        ExecutorCall executorCall = createCallExecutorRead(attributes, configKey);
        EO adapter = TestEOProvider.createEmptyMap();
        executorCall.execute(adapter);
        return adapter;
    }

    public static ExecutorCall createCallExecutorWrite(final String key)  {
        return createCallExecutorWrite(new HashMap(), key);
    }

    public static ExecutorCall createCallExecutorWrite(Map attributes, final String key)  {
        attributes.put(Executor.EXECUTE, M_CSV_CALL + ".write(" + key + ")");
        return new ExecutorCall(attributes);
    }

    public static EO executeWrite(Map attributes, final String configKey)  {
        ExecutorCall executorCall = createCallExecutorWrite(attributes, configKey);
        EO adapter = TestEOProvider.createEmptyMap();
        executorCall.execute(adapter);
        return adapter;
    }
}
