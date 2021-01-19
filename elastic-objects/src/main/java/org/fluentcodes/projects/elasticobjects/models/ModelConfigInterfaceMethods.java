package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public interface ModelConfigInterfaceMethods extends ModelConfigInterface {

    default boolean hasFieldConfig(final String fieldName) {
        return getFieldMap().containsKey(fieldName) && getFieldMap().get(fieldName) != null;
    }

    default boolean hasFieldConfigMap() {
        return !getFieldMap().isEmpty();
    }

    default void existFieldConfig(final String fieldName) {
        if (!hasFieldConfig(fieldName)) {
            throw new EoException("No fieldConfig '" + fieldName + "' defined in model '" + this.getModelKey() + "' ! ");
        }
    }

    default FieldConfigInterface getField(final String fieldName) {
        existFieldConfig(fieldName);
        return getFieldMap().get(fieldName);
    }

    default Set<String> getFieldKeys() {
        return getFieldMap().keySet();
    }

    default boolean isNotEmpty(Object object) {
        for (String key: keys(object)) {
            if (exists(key, object)) {
                return false;
            }
        }
        return true;
    }

    Class getModelClass() ;

    /**
     * Gets the Field with fieldName.
     *
     * @param fieldName
     * @return
     * @
     */
    ModelConfig getFieldModel(final String fieldName) ;
    Set<String> keys(Object object) ;
    Map getKeyValues(Object object, PathPattern pathPattern) ;
    int size(Object object) ;

    default boolean isEmpty(final Object object) {
        if (object == null) {
            return true;
        }
        for (final String key: keys(object)) {
            if (exists(key, object)) {
                return false;
            }
        }
        return true;
    }

    default boolean set(final String fieldName, final Object parent, final Object value)  {
        ((FieldConfig)getField(fieldName)).set(parent, value);
        return true;
    }

    default Object get(final String fieldName, final Object parent)  {
        return ((FieldConfig)getField(fieldName)).get(parent);
    }

    /**
     * Gets the value for fieldName of the object.
     *
     * @param fieldName
     * @param object
     * @return
     * @
     */

    boolean exists(final String fieldName, final Object object) ;

    default boolean hasValue(final String fieldName, final Object object) {
        return get(fieldName, object) != null;
    }
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

    ModelConfigInterfaceMethods getDefaultImplementationModel();

    default Object create()  {
        if (!isCreate()) {
            throw new EoException("Could not create empty instance from model for '" + getModelKey() + "'");
        }
        if (getShapeType() == ShapeTypes.CONFIG) {
            throw new EoException("A config has no empty constructor and can't initialized by eo " + getModelKey());
        }
        if (!hasDefaultImplementation()) {
            try {
                return getModelClass().newInstance();
            } catch (Exception e) {
                throw new EoException(e);
            }
        } else {
            ModelConfigInterfaceMethods implementation = getDefaultImplementationModel();
            try {
                return implementation.getModelClass().newInstance();
            } catch (Exception e) {
                throw new EoException("Problem create " + this.getModelKey(), e);
            }
        }
    }

}
