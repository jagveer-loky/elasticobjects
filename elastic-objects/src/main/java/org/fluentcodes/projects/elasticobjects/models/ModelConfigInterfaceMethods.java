package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.PathPattern;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public interface ModelConfigInterfaceMethods extends ModelConfigInterface {
    Map<String, FieldConfig> getFieldConfigMap() ;

    default boolean hasFieldConfig(final String fieldName) {
        return getFieldConfigMap().containsKey(fieldName) && getFieldConfigMap().get(fieldName) != null;
    }

    default boolean hasFieldConfigMap() {
        return !getFieldConfigMap().isEmpty();
    }

    default void existFieldConfig(final String fieldName) {
        if (!hasFieldConfig(fieldName)) {
            throw new EoException("No fieldConfig '" + fieldName + "' defined in model '" + this.getModelKey() + "' ! ");
        }
    }

    default FieldConfig getFieldConfig(final String fieldName) {
        existFieldConfig(fieldName);
        return getFieldConfigMap().get(fieldName);
    }

    default Set<String> getFieldKeys() {
        return getFieldConfigMap().keySet();
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
    boolean isEmpty(Object object) ;

    boolean set(String fieldName, Object object, Object value);

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
