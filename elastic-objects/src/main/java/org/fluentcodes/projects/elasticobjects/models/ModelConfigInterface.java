package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.Map;
import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.models.Model.ABSTRACT;
import static org.fluentcodes.projects.elasticobjects.models.Model.DB_ANNOTATED;
import static org.fluentcodes.projects.elasticobjects.models.Model.JAVASCRIPT_TYPE;

public interface ModelConfigInterface extends ConfigConfigInterface {
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

    Set<String> getFieldKeys();
    default boolean hasFields() {
        return getFieldKeys().isEmpty();
    }

    String getSuperKey();
    default boolean hasSuperKey() {
        return getSuperKey()!=null && !getSuperKey().isEmpty();
    }

    String getInterfaces();
    default boolean hasInterfaces() {
        return getInterfaces()!=null && !getInterfaces().isEmpty();
    }

    default boolean hasCreate() {
        return getProperties().containsKey(CREATE) && getProperties().get(CREATE) != null;
    }

    default Boolean getCreate() {
        return (Boolean) getProperties().get(CREATE);
    }

    default boolean hasShapeType() {
        return getProperties().get(SHAPE_TYPE)!=null;
    }

    default ShapeTypes getShapeType() {
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
        return getProperties().containsKey(DEFAULT_IMPLEMENTATION);
    }

    default String getDefaultImplementation() {
        return (String) getProperties().get(DEFAULT_IMPLEMENTATION);
    }

    default Boolean getAbstract() {
        return (Boolean)getProperties().get(ABSTRACT);
    }
    default Boolean hasAbstract() {
        return getProperties().containsKey(ABSTRACT);
    }
    default Boolean isAbstract() {
        return (hasAbstract() && getAbstract()) || false;
    }

    default Boolean getDbAnnotated() {
        return (Boolean)getProperties().get(DB_ANNOTATED);
    }
    default Boolean hasDbAnnotated() {
        return getProperties().containsKey(DB_ANNOTATED);
    }
    default Boolean isDbAnnotated() {
        return (hasDbAnnotated() && getDbAnnotated()) || false;
    }

    default String getJavascriptType() {
        return (String)getProperties().get(JAVASCRIPT_TYPE);
    }
    default Boolean hasJavascriptType() {
        return getProperties().containsKey(JAVASCRIPT_TYPE) && getProperties().get(JAVASCRIPT_TYPE) != null && !((String) getProperties().get(JAVASCRIPT_TYPE)).isEmpty() ;
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
        return hasCreate() && getCreate();
    }
    default boolean isNull() {
        return false;
    }
    default boolean isEnum() {
        return false;
    }

    default boolean isJsonIgnore(final String key) {
        return false;
    }
}
