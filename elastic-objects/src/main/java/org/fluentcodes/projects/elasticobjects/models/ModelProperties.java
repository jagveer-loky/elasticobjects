package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import static org.fluentcodes.projects.elasticobjects.models.FieldProperties.TABLE;

public interface ModelProperties extends ModuleProperties, ModelConfigInterface {
    String DEFAULT_IMPLEMENTATION = "defaultImplementation";
    String SHAPE_TYPE = "shapeType";
    String CREATE = "create";
    String HIBERNATE_ANNOTATIONS = "hibernateAnnotations";
    String ID_KEY = "idKey";
    String NATURAL_KEYS = "naturalKeys";

    default boolean hasCreate() {
        return getCreate()!=null;
    }

    default Boolean getCreate() {
        return hasProperties()? (Boolean) ScalarConverter.toBoolean(getProperties().get(CREATE)) : null;
    }

    default void setCreate(final String value) {
        if (hasProperties()) {
            getProperties().put(ModelConfig.CREATE, value);
        }
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

    default void setShapeType(final String packagePath) {
        if (hasProperties()) {
            getProperties().put(ModelConfig.PACKAGE_PATH, packagePath);
        }
    }

    default boolean hasDefaultImplementation() {
        return getDefaultImplementation()!=null && !getDefaultImplementation().isEmpty();
    }

    default String getDefaultImplementation() {
        return hasProperties() ? (String) getProperties().get(DEFAULT_IMPLEMENTATION) : null;
    }

    default void setDefaultImplementation(final String value) {
        if (hasProperties()) {
            getProperties().put(ModelConfig.DEFAULT_IMPLEMENTATION, value);
        }
    }

    default boolean hasTable() {
        return getTable()!=null && !getTable().isEmpty();
    }

    default String getTable() {
        return hasProperties() ? (String) getProperties().get(TABLE) : null;
    }

    default void setTable(final String value) {
        if (hasProperties()) {
            getProperties().put(TABLE, value);
        }
    }


}
