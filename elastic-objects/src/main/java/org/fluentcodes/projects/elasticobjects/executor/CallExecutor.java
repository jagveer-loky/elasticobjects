package org.fluentcodes.projects.elasticobjects.executor;

import org.fluentcodes.projects.elasticobjects.EO;

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
    Object execute(EO adapter);
    String getSourcePath();
    CallExecutor setSourcePath(String path);
    String getTargetPath();
    CallExecutor setTargetPath(String path);
    Object transform(Object result);
}
