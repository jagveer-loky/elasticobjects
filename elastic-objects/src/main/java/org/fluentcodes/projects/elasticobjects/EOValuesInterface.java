package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.ConfigMaps;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface EOValuesInterface extends EoLogInterface{
    /**
     * Creates a new object an map it to the object
     *
     * @
     */
    ConfigMaps getConfigsCache();
    String getFieldKey();

    EOValuesInterface getParent();
    default boolean hasParent() {
        return getParent()!=null;
    }
    default boolean isRoot() {
        return hasParent();
    }

    EOValuesInterface createChild(final PathElement fieldKey);
    EOValuesInterface createChild(final PathElement pathElement, final Object value);

    Path getPath();
    String getPathAsString();

    Object get();
    Object get(String... paths) ;
    default boolean isEmpty() {
        return getModels().isEmpty(get());
    }

    EOValuesInterface set(Object value, String... paths) ;
    EOValuesInterface setEmpty(String... paths) ;

    EOValuesInterface mapObject(Object source);

    boolean hasEo(String path);
    int sizeEo();
    int size();
    Set<String> keys();
    Set<String> keysEo() ;
    List<String> keys(String path) ;
    List<String> keys(PathPattern pathPattern) ;
    List<String> filterPaths(String filter) ;

    Map getKeyValues() ;

    EOValuesInterface getEo(String... path) ;
    EOValuesInterface getEo(PathElement path) ;
    EOValuesInterface remove(String... path) ;
    EOValuesInterface getRoot() ;

    Models getModels();
    ModelConfig getModel();
    Class getModelClass();

    default boolean isTransient(final String fieldName) {
        return getModel().hasFieldConfig(fieldName)  ? getModel().getField(fieldName).isTransient(): false;
    }

    boolean isCheckObjectReplication();

    void setCheckObjectReplication(boolean checkObjectReplication);

    boolean isChanged();

    default boolean isContainer() {
        return !isScalar();
    }

    default boolean isList() {
        return getModel().isList();
    }
    default boolean isObject() {
        return getModel().isObject();
    }
    default boolean isScalar() {
        return getModel().isScalar() || getModels().isEnum();
    }
    default boolean isMap() {
        return getModel().isMap();
    }
    default boolean isNull() {
        return getModel().isNull();
    }
    boolean isEoEmpty();

    void setRoles(String... roles);
    List<String> getRoles();
    void setRoles(List<String> roles);

    default boolean hasRoles() {
        return getRoles() != null && !getRoles().isEmpty();
    }

    EOValuesInterface addCall(Call callExecutor) ;
    Set<String> getCallKeys();
    EOValuesInterface getCallEo(String key);

    boolean execute();

    default String getLog() {
        if (!hasEo(PathElement.LOGS))  return "";
        return getLogList().stream().collect(Collectors.joining("\n"));
    }

    default boolean hasLogLevel() {
        return hasEo(PathElement.LOG_LEVEL);
    }

    JSONSerializationType getSerializationType();

    EOValuesInterface setSerializationType(JSONSerializationType serializationType);

    String compare(final EOValuesInterface other);

    String toString(JSONSerializationType serializationType);
}
