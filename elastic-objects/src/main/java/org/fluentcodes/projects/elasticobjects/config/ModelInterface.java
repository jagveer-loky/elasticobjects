package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.eo.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.paths.PathPattern;

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

    String getModelConfigKey();

    List<String> getFieldKeys();

    ShapeTypes getShapeType();

    boolean isToSerialize();

    String getPackageGroup();

    String getAuthor();

    String getSuperKey();

    String getInterfaces();

//</call>

    String getDefaultImplementation();

    PathPattern getPathPattern();

    String getIdKey();

    boolean isHibernateAnnotations();

    String getTable();

    List<String> getNaturalKeys();

    Map<String, FieldConfig> getFieldCacheMap() throws Exception;

    Map<String, ModelInterface> getImportClasses() throws Exception;

    Class getModelClass() throws Exception;

    String getPackagePath();

    ModelInterface getSuperModel() throws Exception;

    Map<String, Object> getNaturalValues(Object object) throws Exception;

    Object getId(Object object) throws Exception;

    /**
     * Gets the Field with fieldName.
     *
     * @param fieldName
     * @return
     * @throws Exception
     */
    FieldConfig getField(final String fieldName) throws Exception;

    ModelInterface getFieldModel(final String fieldName) throws Exception;

    Class getFieldClass(final String fieldName) throws Exception;

    Set<String> keys(Object object) throws Exception;

    Map getKeyValues(Object object, PathPattern pathPattern) throws Exception;

    int size(Object object) throws Exception;

    boolean isEmpty(Object object) throws Exception;

    void set(String fieldName, Object object, Object value) throws Exception;

    /**
     * Gets the value for fieldName of the object.
     *
     * @param fieldNameAsObject
     * @param object
     * @return
     * @throws Exception
     */
    Object getAsIs(Object fieldNameAsObject, Object object) throws Exception;

    /**
     * Gets the value for fieldName of the object.
     *
     * @param fieldName
     * @param object
     * @return
     * @throws Exception
     */
    Object get(String fieldName, Object object) throws Exception;

    boolean exists(final String fieldName, final Object object) throws Exception;

    boolean hasKey(final String fieldName) throws Exception;

    /**
     * Removes a value depending on the type.
     * <ul>
     * <li>scalar: throws Exception</li>
     * <li>object: add value to null</li>
     * <li>map: removes value</li>
     * <li>list: removes value</li>
     * </ul>
     *
     * @param fieldName The fieldName
     * @param object    The object
     * @throws Exception on scalar model without field structure.
     */
    void remove(final String fieldName, final Object object) throws Exception;

    Object create() throws Exception;

    boolean equals(ModelInterface modelCache);

    boolean hasModel();

    boolean isScalar();

    boolean isMap();

    boolean isMapType();

    boolean isSet();

    boolean isList();

    boolean isListType();

    boolean isObject();

    boolean isNull();

    boolean isEnum();

    boolean isContainer();

    String toJSON(final JSONSerializationType serializationType, final Object object) throws Exception;

}
