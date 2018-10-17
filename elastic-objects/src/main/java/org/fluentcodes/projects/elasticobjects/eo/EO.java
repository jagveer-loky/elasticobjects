package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.config.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.config.ModelInterface;
import org.fluentcodes.projects.elasticobjects.executor.CallExecutor;
import org.fluentcodes.projects.elasticobjects.executor.ExecutorList;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface EO {
    /**
     * Creates a new object an map it to the object
     *
     * @throws Exception
     */
    EOConfigsCache getConfigsCache();

    String getParentKey();
    boolean hasParent();

    Path getPath();
    String getPathAsString();

    boolean isContainer();
    boolean isList();
    boolean isObject();
    boolean isScalar();
    boolean isMap();
    boolean hasDefaultMap();
    boolean isChildTyped();
    boolean isNull();
    boolean isEmpty();

    int size() throws Exception;
    int size(String path) throws Exception;

    Set<String> keys() throws Exception;
    List<String> keys(String path) throws Exception;
    List<String> keys(PathPattern pathPattern) throws Exception;
    List<String> filterPaths(String filter) throws Exception;
    Map getKeyValues() throws Exception;

    Object get();
    Object get(String paths) throws Exception;
    EO getChild(String paths) throws Exception;

    EOBuilder add();
    EOBuilder add(String path);

    EO remove() throws Exception;
    boolean remove(String path) throws Exception;

    EO getRoot() throws Exception;
    boolean isRoot();

    boolean isCheckObjectReplication();
    void setCheckObjectReplication(boolean checkObjectReplication);

    Models getModels();
    ModelInterface getModel();
    Class getModelClass();

    List<String> getRoles();

    void addCall(CallExecutor callExecutor) throws Exception;
    ExecutorList getCalls();
    boolean hasActions();
    void executeActions() throws Exception;

    void compare(final StringBuilder builder, final EO other);

    String getLog();
    LogLevel getLogLevel();
    LogLevel getErrorLevel();
    void debug(String message);
    void info(String message);
    void warn(String message);
    void error(String message);
    void warn(String message, Exception e);
    void error(String message, Exception e);
}
