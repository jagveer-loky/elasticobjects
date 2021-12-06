package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

import java.util.Map;
import java.util.Set;

import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_FINAL;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_OVERRIDE;
import static org.fluentcodes.projects.elasticobjects.models.FieldBeanInterface.F_PROPERTY;

public interface ModelInterface extends ConfigInterface {
    String DEFAULT_IMPLEMENTATION = "defaultImplementation";
    String SHAPE_TYPE = "shapeType";
    String CREATE = "create";
    String CLASS_PATH = "classPath";
    String ID_KEY = "idKey";
    String NATURAL_KEYS = "naturalKeys";
    String TABLE = "table";
    String BEAN = "bean";
    String JAVASCRIPT_TYPE = "javascriptType";
    String DB_ANNOTATED = "dbAnnotated";
    String ABSTRACT = "abstract";

    Map<String, FieldConfig> getFieldMap() ;

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
    default String getKey() {
        if (hasModelKey()) return getModelKey();
        if (hasNaturalId()) return getNaturalId();
        return "";
    }
    default boolean hasKey() {
        return !getModelKey().isEmpty();
    }

    String getPackagePath();
    default boolean hasPackagePath() {
        return getPackagePath()!=null && !getPackagePath().isEmpty();
    }

    Set<String> getFieldKeys();
    default boolean hasFields() {
        return getFieldKeys().isEmpty();
    }


    default FieldBeanInterface getField(final String key) {
        return getFieldMap().get(key);
    }

    default boolean hasField(final String key) {
        return getFieldMap().containsKey(key);
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

    default boolean hasProperty () {
        return getProperties().containsKey(F_PROPERTY) && getProperties().get(F_PROPERTY)!=null;
    }
    default boolean isProperty() {
        return hasProperty() && getProperty();
    }
    default Boolean getProperty() {
        return (Boolean) getProperties().get(F_PROPERTY);
    }

    default boolean hasShapeType() {
        return getProperties().containsKey(SHAPE_TYPE) && getProperties().get(SHAPE_TYPE) != null;
    }

    default ShapeTypes getShapeType() {
        if (!getProperties().containsKey(SHAPE_TYPE)) {
            return ShapeTypes.BEAN;
        }
        try {
            if (getProperties().get(SHAPE_TYPE) instanceof String) {
                return ShapeTypes.valueOf((String) getProperties().get(SHAPE_TYPE));
            }
        }
        catch (IllegalArgumentException e) {
            throw new EoInternalException(e);
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
    default boolean hasAbstract() {
        return getProperties().containsKey(ABSTRACT) && getProperties().get(ABSTRACT) !=null;
    }
    default Boolean isAbstract() {
        return hasAbstract() && getAbstract();
    }

    default Boolean getFinal() {
        return (Boolean)getProperties().get(F_FINAL);
    }
    default boolean hasFinal() {
        return getProperties().containsKey(F_FINAL) && getProperties().get(F_FINAL) !=null;
    }
    default boolean isFinal() {
        return hasFinal() && getFinal();
    }

    default Boolean getOverride() {
        return (Boolean)getProperties().get(F_OVERRIDE);
    }
    default boolean hasOverride() {
        return getProperties().containsKey(F_OVERRIDE) && getProperties().get(F_OVERRIDE) !=null;
    }
    default boolean isOverride() {
        return hasOverride() && getOverride();
    }


    default String getBean() {
        return (String)getProperties().get(BEAN);
    }
    default boolean hasBean() {
        return getProperties().containsKey(BEAN) && getProperties().get(BEAN) !=null  && !((String)getProperties().get(BEAN)).isEmpty();
    }

    default Boolean getDbAnnotated() {
        return (Boolean)getProperties().get(DB_ANNOTATED);
    }
    default boolean hasDbAnnotated() {
        return getProperties().containsKey(DB_ANNOTATED);
    }
    default Boolean isDbAnnotated() {
        return (hasDbAnnotated() && getDbAnnotated()) || false;
    }

    default boolean isList() {
        return (this instanceof ModelConfigList);
    }

    default boolean isMap() {
        return (this instanceof ModelConfigMap);
    }

    default boolean isScalar() {
        return (this instanceof ModelConfigScalar);
    }

    default boolean isObject() {
        return (this instanceof ModelConfigObject);
    }

    default boolean isCall() {
        return getModelKey().endsWith("Call");
    }

    default boolean isInterface() {
        return getShapeType() == ShapeTypes.INTERFACE;
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
        return hasField(key) && getField(key).isJsonIgnore()    ;
    }

    default boolean isProperty(final String key) {
        return hasField(key) && getField(key).isProperty();
    }
}
