package org.fluentcodes.projects.elasticobjects.calls;

import org.fluentcodes.projects.elasticobjects.EO;

/**
 * Interface for Executors
 *
 * @author Werner Diwischek
 * @version 0.2.0
 */
public interface ExecutorCall {
    /**
     * Execute something. Exception will be documented in the adapter log.
     */
    Object execute(EO adapter, Call call);
    Object transform(Object result);
}
