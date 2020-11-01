package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

public interface ModelConfigProperties extends ModelConfigInterface {
    String DEFAULT_IMPLEMENTATION = "defaultImplementation";
    String SHAPE_TYPE = "shapeType";
    String CREATE = "create";
    String CLASS_PATH = "classPath";

    default boolean hasCreate() {
        return getCreate()!=null;
    }

    default Boolean getCreate() {
        return hasProperties()? (Boolean) ScalarConverter.toBoolean(getProperties().get(CREATE)) : null;
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


}
