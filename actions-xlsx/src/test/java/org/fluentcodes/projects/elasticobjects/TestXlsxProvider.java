package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.XlsxCall;
import org.fluentcodes.projects.elasticobjects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.ExecutorCall;
import org.fluentcodes.projects.elasticobjects.calls.executor.Executor;
import org.fluentcodes.projects.elasticobjects.testitemprovider.TestEOProvider;

import java.util.HashMap;
import java.util.Map;

public class TestXlsxProvider {
    public static ExecutorCall createExecutorXlsxActionRead(final String key)  {
        return createExecutorXlsxActionRead(new HashMap(), key);
    }

    public static ExecutorCall createExecutorXlsxActionRead(Map attributes, final String key)  {
        attributes.put(Executor.EXECUTE, XlsxCall.class.getSimpleName() + ".read(" + key + ")");
        return new ExecutorCall(attributes);
    }

    public static EO executeXlsxActionRead(Map attributes, final String configKey)  {
        ExecutorCall executorCall = createExecutorXlsxActionRead(attributes, configKey);
        EO adapter = TestEOProvider.createEmptyMap();
        executorCall.execute(adapter);
        return adapter;
    }

    public static ExecutorCall createExecutorXlsxActionWrite(final String key)  {
        return createExecutorXlsxActionWrite(new HashMap(), key);
    }

    public static ExecutorCall createExecutorXlsxActionWrite(Map attributes, final String key)  {
        attributes.put(Executor.EXECUTE, XlsxCall.class.getSimpleName() + ".write(" + key + ")");
        return new ExecutorCall(attributes);
    }

    public static EO executeXlsxActionWrite(Map attributes, final String configKey)  {
        ExecutorCall executorCall = createExecutorXlsxActionWrite(attributes, configKey);
        EO adapter = TestEOProvider.createEmptyMap();
        executorCall.execute(adapter);
        return adapter;
    }
}
