package org.fluentcodes.projects.elasticobjects.models;


/**
 * Created by Werner on 18.11.2014.
 */
public enum ShapeTypes {
    MAP(ModelConfigMap.class),
    LIST(ModelConfigList.class),
    SET(ModelConfigSet.class),
    INTERFACE(),
    OBJECT(),
    SCALAR_SERIALIZED(ModelConfigScalar.class),
    SCALAR(ModelConfigScalar.class),
    NUMBER(ModelConfigScalar.class),
    ENUM(ModelConfigScalar.class),
    NULL(ModelConfigScalar.class),
    NONE(ModelConfigScalar.class),
    BEAN(),
    CALL_BEAN(),
    MODEL(),
    INSTANCE(),
    CONFIG();
    private Class modelConfig;
    ShapeTypes() {
        this.modelConfig = ModelConfigObject.class;
    }
    ShapeTypes(Class modelConfig) {
        this.modelConfig = modelConfig;
    }

    public Class getModelConfig() {
        return modelConfig;
    }
    public String getModelConfigKey() {
        return modelConfig.getSimpleName();
    }
}
