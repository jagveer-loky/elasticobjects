package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelInterface;
import org.fluentcodes.projects.elasticobjects.models.Models;

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

    EO getParent();

    Path getPath();
    String getPathAsString();

    Object get();
    Object get(String... paths) ;

    EO set(Object value, String... paths) ;
    EO setEmpty(String... paths) ;

    EO mapObject(Object source);

    int sizeEo();
    int size();
    Set<String> keys();
    Set<String> keysEo() ;
    List<String> keys(String path) ;
    List<String> keys(PathPattern pathPattern) ;
    List<String> filterPaths(String filter) ;

    Map getKeyValues() ;

    EO getEo(String... path) ;
    EO getEo(PathElement path) ;
    EO remove(String... path) ;
    EO getRoot() ;
    boolean isRoot();

    boolean isCheckObjectReplication();

    void setCheckObjectReplication(boolean checkObjectReplication);

    Models getModels();
    ModelInterface getModel();
    Class getModelClass();
    boolean isChanged();
    boolean isContainer();
    boolean isList();
    boolean isObject();
    boolean isScalar();
    boolean isMap();
    boolean hasDefaultMap();
    boolean isChildTyped();
    boolean isNull();
    boolean isEmpty();

    void setRoles(String... roles);
    List<String> getRoles();
    void setRoles(List<String> roles);
    boolean hasRoles();

    EO addCall(Call callExecutor) ;
    Set<String> getCallKeys();
    EO getCallEo(String key);

    boolean execute();

    String getLog();
    LogLevel getLogLevel();
    EO setLogLevel(LogLevel logLevel);
    LogLevel getErrorLevel();
    boolean hasErrors();
    EO debug(String message);
    EO info(String message);
    EO warn(String message);
    EO error(String message);
    EO warn(String message, Exception e);
    EO error(String message, Exception e);

    JSONSerializationType getSerializationType();

    EO setSerializationType(JSONSerializationType serializationType);

    void compare(final StringBuilder builder, final EO other);


}
