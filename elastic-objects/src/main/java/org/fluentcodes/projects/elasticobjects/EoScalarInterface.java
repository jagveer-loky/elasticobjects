package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.EOConfigsCache;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.Models;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Offers an adapter for objects to access elements via path.
 */

public interface EoScalarInterface extends EoLogInterface{
    /**
     * Creates a new object an map it to the object
     *
     * @
     */
    EOConfigsCache getConfigsCache();
    String getFieldKey();

    EoScalarInterface getParent();
    default boolean hasParent() {
        return getParent()!=null;
    }
    default boolean isRoot() {
        return hasParent();
    }

    EoScalarInterface createChild(final PathElement fieldKey);
    EoScalarInterface createChild(final PathElement pathElement, final Object value);

    Path getPath();
    String getPathAsString();

    Object get();
    Object get(String... paths) ;
    default boolean isEmpty() {
        return getModels().isEmpty(get());
    }

    EoScalarInterface set(Object value, String... paths) ;
    EoScalarInterface setEmpty(String... paths) ;

    EoScalarInterface mapObject(Object source);

    boolean hasEo(String path);

    Map getKeyValues() ;

    EoScalarInterface getEo(String... path) ;
    EoScalarInterface getEo(PathElement path) ;
    EoScalarInterface remove(String... path) ;
    EoScalarInterface getRoot() ;

    Models getModels();
    ModelConfig getModel();
    Class getModelClass();

    default boolean isTransient(final String fieldName) {
        return getModel().hasFieldConfig(fieldName)  ? getModel().getFieldConfig(fieldName).isTransient(): false;
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

    EoScalarInterface addCall(Call callExecutor) ;
    Set<String> getCallKeys();
    EoScalarInterface getCallEo(String key);

    boolean execute();

    default String getLog() {
        if (!hasEo(PathElement.LOGS))  return "";
        return getLogList().stream().collect(Collectors.joining("\n"));
    }

    default boolean hasLogLevel() {
        return hasEo(PathElement.LOG_LEVEL);
    }

    JSONSerializationType getSerializationType();

    EoScalarInterface setSerializationType(JSONSerializationType serializationType);

    String compare(final EoScalarInterface other);

    String toString(JSONSerializationType serializationType);


}
