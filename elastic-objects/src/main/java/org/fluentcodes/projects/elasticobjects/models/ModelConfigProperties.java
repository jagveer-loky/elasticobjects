package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.values.StringLowerCall;
import org.fluentcodes.projects.elasticobjects.calls.values.StringPluralCall;
import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.List;

public interface ModelConfigProperties extends ConfigProperties, Base {
    String DEFAULT_IMPLEMENTATION = "defaultImplementation";
    String SHAPE_TYPE = "shapeType";
    String CREATE = "create";
    String CLASS_PATH = "classPath";
    String ID_KEY = "idKey";
    String NATURAL_KEYS = "naturalKeys";
    String TABLE = "table";

    default boolean hasTable() {
        return getTable()!=null && !getTable().isEmpty();
    }

    default String getTable() {
        return hasProperties() ? (String) getProperties().get(TABLE) : null;
    }

    default boolean hasIdKey() {
        return getIdKey()!=null && !getIdKey().isEmpty();
    }

    default String getIdKey() {
        return hasProperties() ? (String) getProperties().get(ID_KEY) : null;
    }

    default boolean hasNaturalKeys() {
        return getNaturalKeys()!=null && !getNaturalKeys().isEmpty();
    }

    default String getNaturalKeys() {
        return hasProperties() ? (String) getProperties().get(NATURAL_KEYS) : null;
    }

    String getModelKey();
    default boolean hasModelKey() {
        return getModelKey()!=null && !getModelKey().isEmpty();
    }

    String getPackagePath();
    default boolean hasPackagePath() {
        return getPackagePath()!=null && !getPackagePath().isEmpty();
    }

    List<String> getFieldKeys();

    String getSuperKey();
    default boolean hasSuperKey() {
        return getSuperKey()!=null && !getSuperKey().isEmpty();
    }

    String getInterfaces();
    default boolean hasInterfaces() {
        return getInterfaces()!=null && !getInterfaces().isEmpty();
    }


    default boolean hasCreate() {
        return getCreate()!=null;
    }

    default Boolean getCreate() {
        return hasProperties() && getProperties().containsKey(CREATE)? ScalarConverter.toBoolean(getProperties().get(CREATE)) : true;
    }

    default boolean hasShapeType() {
        return getShapeType()!=null;
    }

    default ShapeTypes getShapeType() {
        if (!hasProperties()) {
            return null;
        }
        if (!getProperties().containsKey(SHAPE_TYPE)) {
            return null;
        }
        if (getProperties().get(SHAPE_TYPE) instanceof String) {
            return ShapeTypes.valueOf((String) getProperties().get(SHAPE_TYPE));
        }
        if (getProperties().get(SHAPE_TYPE) instanceof ShapeTypes) {
            return (ShapeTypes)getProperties().get(SHAPE_TYPE);
        }
        throw new EoException("Could not map " + getProperties().get(SHAPE_TYPE) + " " + getProperties().get(SHAPE_TYPE).getClass());
    }

    default boolean hasDefaultImplementation() {
        return getDefaultImplementation()!=null && !getDefaultImplementation().isEmpty();
    }

    default String getDefaultImplementation() {
        return hasProperties() ? (String) getProperties().get(DEFAULT_IMPLEMENTATION) : null;
    }

    default boolean hasClassPath() {
        return getClassPath()!=null && !getClassPath().isEmpty();
    }
    default String getClassPath() {
        return hasProperties()?(String)getProperties().get(CLASS_PATH):null;
    }

    default boolean isList() {
        return (this instanceof ModelConfigList);
    }

    default boolean isMap() {
        return (this instanceof ModelConfigMap);
    }

    default boolean isSet() {
        return (this instanceof ModelConfigSet);
    }

    default boolean isScalar() {
        return (this instanceof ModelConfigScalar);
    }

    default boolean isObject() {
        return (this instanceof ModelConfigObject);
    }

    default boolean isCall() {
        return isObject () && getShapeType() == ShapeTypes.CALL_BEAN;
    }

    default boolean isInterface() {
        return isObject () && getShapeType() == ShapeTypes.INTERFACE;
    }

    default boolean isContainer() {
        return !isScalar();
    }

    default boolean isNumber() {
        return false;
    }

    default boolean hasModel() {
        return true;
    }

    default boolean isCreate() {
        return getCreate();
    }
    default boolean isNull() {
        return false;
    }
    default boolean isEnum() {
        return false;
    }
}
