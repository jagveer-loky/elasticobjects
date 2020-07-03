package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;

import java.util.HashMap;
import java.util.Map;

public class ExecutorProvider {
    public static EO execute(final Executor callExecutor)  {
        return execute(false, callExecutor, new HashMap());
    }

    public static EO execute(final Executor callExecutor, String... values)  {
        return execute(false, callExecutor, EO_STATIC.toMap(values));
    }

    public static EO execute(final Executor callExecutor, Map values)  {
        return execute(false, callExecutor, values);
    }

    public static EO executeWithCompare(final Executor callExecutor)  {
        return execute(false, callExecutor, new HashMap());
    }

    public static EO executeWithCompare(final Executor callExecutor, String... values)  {
        return execute(false, callExecutor, EO_STATIC.toMap(values));
    }

    public static EO execute(final boolean serialize, final Executor callExecutor, Map values)  {
        EO eo = TestEOProvider.createEmptyMap();
        callExecutor.execute(eo, values);
        if (!serialize) {
            return eo;
        }
        final EO eoFromJsn = TestEOProvider.assertEOSerialized(eo);
        eoFromJsn.executeCalls();
        return eoFromJsn;
    }
}
