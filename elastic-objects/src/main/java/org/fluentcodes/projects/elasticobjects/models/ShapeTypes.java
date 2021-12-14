package org.fluentcodes.projects.elasticobjects.models;


/**
 * Created by Werner on 18.11.2014.
 */
public enum ShapeTypes {
    MAP(ModelConfigMap.class),
    LIST(ModelConfigList.class),
    INTERFACE(),
    OBJECT(),
    SCALAR_SERIALIZED(ModelConfigScalar.class),
    SCALAR(ModelConfigScalar.class, new ShapeTypeSerializerString()),
    STRING(ModelConfigScalar.class, new ShapeTypeSerializerString()),
    NUMBER(ModelConfigScalar.class, new ShapeTypeSerializerNumber()),
    DATE(ModelConfigScalar.class, new ShapeTypeSerializerDate()),
    BOOLEAN(ModelConfigScalar.class, new ShapeTypeSerializerBoolean()),
    ENUM(ModelConfigScalar.class, new ShapeTypeSerializerEnum()),
    NULL(ModelConfigScalar.class),
    NONE(ModelConfigScalar.class),
    BEAN(),
    CALL_BEAN(),
    MODEL(),
    INSTANCE(),
    CONFIG();
    private Class<? extends ModelConfig> modelConfig;
    private ShapeTypeSerializerInterface serializer;
    ShapeTypes() {
        this(ModelConfigObject.class, new ShapeTypeSerializer());
    }
    ShapeTypes(Class<? extends ModelConfig> modelConfig) {
        this(modelConfig, new ShapeTypeSerializer());
    }
    ShapeTypes(Class<? extends ModelConfig> modelConfig, ShapeTypeSerializerInterface serializer) {
        this.modelConfig = modelConfig;
        this.serializer = serializer;
    }

    public Class getModelConfig() {
        return modelConfig;
    }

    public boolean hasModelConfig() {
        return modelConfig != null;
    }
    public String getModelConfigKey() {
        return modelConfig.getSimpleName();
    }

    public String asString(Object object) {
        return serializer.asString(object);
    }
    public String asJson(Object object) {
        return serializer.asJson(object);
    }
}
