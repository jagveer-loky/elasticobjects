package org.fluentcodes.projects.elasticobjects.config;

import org.fluentcodes.projects.elasticobjects.eo.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.paths.Path;
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
    FieldConfig getField(final String fieldName) ;

    ModelInterface getFieldModel(final String fieldName) ;

    Class getFieldClass(final String fieldName) ;

    Set<String> keys(Object object) ;

    Map getKeyValues(Object object, PathPattern pathPattern) ;

    int size(Object object) ;

    boolean isEmpty(Object object) ;

    void set(String fieldName, Object object, Object value) ;

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

    boolean isMap();

    boolean isMapType();

    boolean isSet();

    boolean isList();

    boolean isListType();

    boolean isObject();

    boolean isNull();

    boolean isEnum();

    boolean isContainer();

    String toJSON(final JSONSerializationType serializationType, final Object object) ;

}
