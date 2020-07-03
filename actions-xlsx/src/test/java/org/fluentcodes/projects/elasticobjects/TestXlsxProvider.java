package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.XlsxCall;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.Executor;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;

import java.util.HashMap;
import java.util.Map;

public class TestXlsxProvider {
    public static CallExecutor createExecutorXlsxActionRead(final String key)  {
        return createExecutorXlsxActionRead(new HashMap(), key);
    }

    public static CallExecutor createExecutorXlsxActionRead(Map attributes, final String key)  {
        attributes.put(Executor.EXECUTE, XlsxCall.class.getSimpleName() + ".read(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeXlsxActionRead(Map attributes, final String configKey)  {
        CallExecutor callExecutor = createExecutorXlsxActionRead(attributes, configKey);
        EO adapter = TestEOProvider.createEmptyMap();
        callExecutor.execute(adapter);
        return adapter;
    }

    public static CallExecutor createExecutorXlsxActionWrite(final String key)  {
        return createExecutorXlsxActionWrite(new HashMap(), key);
    }

    public static CallExecutor createExecutorXlsxActionWrite(Map attributes, final String key)  {
        attributes.put(Executor.EXECUTE, XlsxCall.class.getSimpleName() + ".write(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeXlsxActionWrite(Map attributes, final String configKey)  {
        CallExecutor callExecutor = createExecutorXlsxActionWrite(attributes, configKey);
        EO adapter = TestEOProvider.createEmptyMap();
        callExecutor.execute(adapter);
        return adapter;
    }
}
