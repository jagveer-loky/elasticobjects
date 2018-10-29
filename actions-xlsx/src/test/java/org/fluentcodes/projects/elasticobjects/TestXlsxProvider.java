package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.XlsxCall;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorListTemplate;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;

import java.util.HashMap;
import java.util.Map;

public class TestXlsxProvider {
    public static CallExecutor createExecutorXlsxActionRead(final String key) throws Exception {
        return createExecutorXlsxActionRead(new HashMap(), key);
    }

    public static CallExecutor createExecutorXlsxActionRead(Map attributes, final String key) throws Exception {
        attributes.put(ExecutorListTemplate.EXECUTE, XlsxCall.class.getSimpleName() + ".read(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeXlsxActionRead(Map attributes, final String configKey) throws Exception {
        CallExecutor callExecutor = createExecutorXlsxActionRead(attributes, configKey);
        EO adapter = TestObjectProvider.createEOFromJson();
        callExecutor.execute(adapter);
        return adapter;
    }

    public static CallExecutor createExecutorXlsxActionWrite(final String key) throws Exception {
        return createExecutorXlsxActionWrite(new HashMap(), key);
    }

    public static CallExecutor createExecutorXlsxActionWrite(Map attributes, final String key) throws Exception {
        attributes.put(ExecutorListTemplate.EXECUTE, XlsxCall.class.getSimpleName() + ".write(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeXlsxActionWrite(Map attributes, final String configKey) throws Exception {
        CallExecutor callExecutor = createExecutorXlsxActionWrite(attributes, configKey);
        EO adapter = TestObjectProvider.createEOFromJson();
        callExecutor.execute(adapter);
        return adapter;
    }
}
