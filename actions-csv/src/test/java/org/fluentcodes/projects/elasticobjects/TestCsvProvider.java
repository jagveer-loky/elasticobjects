package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.Executor;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;

import java.util.HashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.CEO_STATIC.M_CSV_CALL;

public class TestCsvProvider {
    public static CallExecutor createCallExecutorRead(final String key) throws Exception {
        return createCallExecutorRead(new HashMap(), key);
    }

    public static CallExecutor createCallExecutorRead(Map attributes, final String key) throws Exception {
        attributes.put(Executor.EXECUTE, M_CSV_CALL + ".read(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeRead(Map attributes, final String configKey) throws Exception {
        CallExecutor callExecutor = createCallExecutorRead(attributes, configKey);
        EO adapter = TestEOProvider.createEmptyMap();
        callExecutor.execute(adapter);
        return adapter;
    }

    public static CallExecutor createCallExecutorWrite(final String key) throws Exception {
        return createCallExecutorWrite(new HashMap(), key);
    }

    public static CallExecutor createCallExecutorWrite(Map attributes, final String key) throws Exception {
        attributes.put(Executor.EXECUTE, M_CSV_CALL + ".write(" + key + ")");
        return new CallExecutor(attributes);
    }

    public static EO executeWrite(Map attributes, final String configKey) throws Exception {
        CallExecutor callExecutor = createCallExecutorWrite(attributes, configKey);
        EO adapter = TestEOProvider.createEmptyMap();
        callExecutor.execute(adapter);
        return adapter;
    }
}
