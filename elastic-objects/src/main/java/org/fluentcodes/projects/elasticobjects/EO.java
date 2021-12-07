package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;
import org.fluentcodes.projects.elasticobjects.models.Scope;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface EO extends EOModel{
    /**
     * Creates a new object an map it to the object
     *
     * @
     */

    String getFieldKey();

    EO getParent();
    default boolean hasParent() {
        return getParent()!=null;
    }
    default boolean isRoot() {
        return !hasParent();
    }

    EO createChild(final PathElement fieldKey);
    EO createChild(final PathElement pathElement, final Object value);

    Path getPath();
    String getPathAsString();

    Object get();
    Object get(String... paths) ;
    default boolean isEmpty() {
        return getModels().isEmpty(get());
    }

    EO set(Object value, String... paths) ;
    EO setEmpty(String... paths) ;

    EO mapObject(Object source);

    boolean hasEo(String path);
    int sizeEo();
    int size();
    Set<String> keys();
    Set<String> keysEo() ;
    List<String> keys(String path) ;
    List<String> keys(PathPattern pathPattern) ;
    List<String> filterPaths(String filter) ;

    Map<String, Object> getKeyValues();

    EO getEo(String... path) ;
    EO getEo(PathElement path) ;
    EO remove(String... path) ;
    EO getRoot() ;

    boolean isCheckObjectReplication();

    void setCheckObjectReplication(boolean checkObjectReplication);

    boolean isChanged();

    boolean isEoEmpty();

    String compare(final EO other);

    String toString(JSONSerializationType serializationType);


    void setRoles(String... roles);
    List<String> getRoles();
    void setRoles(List<String> roles);

    default boolean hasRoles() {
        return getRoles() != null && !getRoles().isEmpty();
    }

    EO addCall(Call callExecutor);
    Set<String> getCallKeys();
    EO getCallEo(String key);

    boolean execute();

    default String getLog() {
        if (!hasEo(PathElement.LOGS))  return "";
        return getLogList().stream().collect(Collectors.joining("\n"));
    }

    default boolean hasLogLevel() {
        return hasEo(PathElement.LOG_LEVEL);
    }

    default boolean hasSerializationType() {
        return getRoot().hasEo(PathElement.SERIALIZATION_TYPE);
    }
    JSONSerializationType getSerializationType();

    EO setSerializationType(JSONSerializationType serializationType);

}
