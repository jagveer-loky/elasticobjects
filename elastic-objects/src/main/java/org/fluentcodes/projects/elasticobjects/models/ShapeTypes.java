package org.fluentcodes.projects.elasticobjects.models;


/**
 * Created by Werner on 18.11.2014.
 */
public enum ShapeTypes {
    MAP(ModelConfigTypes.MAP),
    LIST(ModelConfigTypes.SET),
    SET(ModelConfigTypes.SCALAR),
    INTERFACE(),
    OBJECT(ModelConfigTypes.NONE),
    SCALAR_SERIALIZED(),
    SCALAR(ModelConfigTypes.SCALAR),
    NUMBER(ModelConfigTypes.SCALAR),
    ENUM(ModelConfigTypes.SCALAR),
    NULL(),
    NONE(),
    BEAN(ModelConfigTypes.OBJECT),
    CALL(ModelConfigTypes.OBJECT),
    CALL_BEAN(ModelConfigTypes.OBJECT),
    MODEL(),
    INSTANCE(),
    CONFIG();

    private ModelConfigTypes type;

    ShapeTypes() {
    }

    ShapeTypes(ModelConfigTypes type) {
        this.type = type;
    }

    public ModelConfigTypes getModelConfigType() {
        return type;
    }
}
