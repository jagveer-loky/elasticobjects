package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.eo.EO;

import java.util.Map;

/**
 * Interface for Executors
 *
 * @author Werner Diwischek
 * @version 0.01
 */
public interface Executor {
    String EXECUTE = "execute";

    /**
     * Execute something. Exception will be documented in the adapter log.
     */
    Object execute(EO adapter);

    Object execute(EO adapter, Map<String, String> attributes);

    Object getAttribute(String key);

    Map getAttributes();
}
