package org.fluentcodes.projects.elasticobjects.calls.executor;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;

/**
 * Interface for Executors
 *
 * @author Werner Diwischek
 * @version 0.01
 */
public interface CallExecutor {
    String EXECUTE = "execute";
    /**
     * Execute something. Exception will be documented in the adapter log.
     */
    Object execute(EO adapter, Call call);
    Object transform(Object result);
}
