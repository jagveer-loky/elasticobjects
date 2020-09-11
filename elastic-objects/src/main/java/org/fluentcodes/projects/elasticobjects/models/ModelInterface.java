package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.Path;
import org.fluentcodes.projects.elasticobjects.PathPattern;

import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public interface ModelInterface extends Config {
    //<call keep="JAVA" templateKey="InterfacesCacheSetter.tpl" }

    String getModelKey();

    EOParams getEoParams();

    ViewParams getViewParams();

    DBParams getDbParams();

    Map getCustomParams();

    List<String> getFieldKeys();
    Set<String> getFieldNames();

    ShapeTypes getShapeType();

    boolean isToSerialize();

    String getPackageGroup();

    String getSuperKey();

    String getInterfaces();

    String getDefaultImplementation();

    String getIdKey();

    boolean isHibernateAnnotations();

    String getTable();

    List<String> getNaturalKeys();

    Map<String, FieldConfig> getFieldCacheMap() ;

    Map<String, ModelInterface> getImportClasses() ;

    Class getModelClass() ;

    String getPackagePath();

    ModelInterface getSuperModel() ;

    Map<String, Object> getNaturalValues(Object object) ;

    Object getId(Object object) ;

    /**
     * Gets the Field with fieldName.
     *
     * @param fieldName
     * @return
     * @
     */
    FieldConfig getFieldConfig(final String fieldName) ;
    boolean hasFieldConfig(final String fieldName);
    ModelInterface getFieldModel(final String fieldName) ;

    Class getFieldClass(final String fieldName) ;

    Set<String> keys(Object object) ;

    Map getKeyValues(Object object, PathPattern pathPattern) ;

    int size(Object object) ;

    boolean isEmpty(Object object) ;

    void set(String fieldName, Object object, Object value);
    boolean hasSetter(final String fieldName);
    boolean hasGetter(final String fieldName);

    /**
     * Gets the value for fieldName of the object.
     *
     * @param fieldNameAsObject
     * @param object
     * @return
     * @
     */
    Object getAsIs(Object fieldNameAsObject, Object object) ;

    /**
     * Gets the value for fieldName of the object.
     *
     * @param fieldName
     * @param object
     * @return
     * @
     */
    Object get(String fieldName, Object object) ;

    boolean exists(final String fieldName, final Object object) ;

    boolean hasKey(final String fieldName) ;
    boolean hasKey(final Path path);
    /**
     * Removes a value depending on the type.
     * <ul>
     * <li>scalar: </li>
     * <li>object: add value to null</li>
     * <li>map: removes value</li>
     * <li>list: removes value</li>
     * </ul>
     *
     * @param fieldName The fieldName
     * @param object    The object
     * @ on scalar model without field structure.
     */
    void remove(final String fieldName, final Object object) ;

    Object create();

    boolean equals(ModelInterface modelCache);

    boolean hasModel();

    boolean isScalar();
    boolean isNumber();
    boolean isMap();
    boolean isMapType();
    boolean isSet();
    boolean isList();
    boolean isCreate();
    boolean isListType();
    boolean isCall();
    boolean isObject();
    boolean isNull();
    default boolean isEnum() {
        return this.getModelClass().isEnum();
    }
    default boolean isContainer() {
        return isMap() || isObject() || isList();
    }

    String toJSON(final JSONSerializationType serializationType, final Object object) ;

}
