package org.fluentcodes.projects.elasticobjects.models;


import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Map;

/**
 * Created by Werner on 4.8.2020
 */
public enum ModelTypes {
    MAP(ModelConfigMap.class),
    LIST(ModelConfigList.class),
    SET(ModelConfigSet.class),
    OBJECT(ModelConfigObject.class),
    SCALAR(ModelConfigScalar.class),
    NONE(ModelConfigNone.class);
    private Class<? extends ModelConfig> modelClass;
    private Constructor modelConstructor;
    ModelTypes(Class<? extends ModelConfig> modelClass) {
        this.modelClass = modelClass;
        try {
            this.modelConstructor = modelClass.getConstructor(EOConfigsCache.class, ModelConfig.Builder.class);
        }
        catch (Exception e) {
            throw new EoException(e);
        }
    }

    public ModelConfig createConfig(EOConfigsCache cache, Map values) {
        ModelConfig.Builder builder = new ModelConfig.Builder();
        builder.prepare(cache, values);
        try {
            return (ModelConfig)this.modelConstructor.newInstance(cache, builder);
        } catch (InstantiationException e) {
            throw new EoException(e);
        } catch (IllegalAccessException e) {
            throw new EoException(e);
        } catch (InvocationTargetException e) {
            throw new EoException(e);
        }
    }
}
