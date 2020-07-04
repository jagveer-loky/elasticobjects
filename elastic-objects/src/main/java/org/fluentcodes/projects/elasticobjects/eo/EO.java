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
     * @
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

    int size() ;

    int size(String path) ;

    Set<String> keys() ;

    List<String> keys(String path) ;

    List<String> keys(PathPattern pathPattern) ;

    List<String> filterPaths(String filter) ;

    Map getKeyValues() ;

    Object get();

    Object get(String paths) ;

    EO getChild(String path) ;

    EOBuilder add();

    EOBuilder add(String path);

    EO remove() ;

    boolean remove(String path) ;

    EO getRoot() ;

    boolean isRoot();

    boolean isCheckObjectReplication();

    void setCheckObjectReplication(boolean checkObjectReplication);

    Models getModels();

    ModelInterface getModel();

    Class getModelClass();

    void setRoles(String... roles);

    List<String> getRoles();

    void setRoles(List<String> roles);

    boolean hasRoles();

    void addCall(CallExecutor callExecutor) ;

    ExecutorList getCalls();

    boolean hasCalls();

    void executeCalls() ;

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

    boolean hasErrors();
}
