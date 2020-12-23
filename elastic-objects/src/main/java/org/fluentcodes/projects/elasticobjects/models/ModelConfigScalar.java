package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigScalar extends ModelConfig {
    public static final String CONFIG_MODEL_KEY = "ModelConfigScalar";

    public ModelConfigScalar(Map map) {
        this(new ModelBean(map));
    }

    public ModelConfigScalar(ConfigBean bean) {
        this((ModelBean) bean);
    }

    public ModelConfigScalar(ModelBean bean) {
        super(bean);
    }

    @Override
    public ModelConfig getFieldModel(final String fieldName)  {
        throw new EoException("Could not get sub field model because no field defined for scalar models: " + fieldName);
    }

    @Override
    public FieldConfig getFieldConfig(final String fieldName)  {
        throw new EoException("Could not get sub field because no field defined for scalar models: " + fieldName);
    }


    @Override
    public Class getFieldClass(String fieldName)  {
        throw new EoException("Could not get sub value because no field defined for scalar models: " + fieldName);
    }

    @Override
    public Set<String> keys(Object object)  {
        throw new EoException("Could not get field names because no sub fields defined for scalar models! " + object.getClass().getSimpleName());
    }

    @Override
    public int size(final Object object)  {
        throw new EoException("Could not get field names size because no sub fields  defined for scalar models!" + object.getClass().getSimpleName());
    }

    @Override
    public boolean isEmpty(final Object object)  {
        return object == null;
    }

    @Override
    public boolean set(final String fieldName, final Object object, final Object value)  {
        throw new EoException("Could not set value because no field defined for scalar models: " + fieldName);
    }

    @Override
    public Object get(final String fieldName, final Object object)  {
        throw new EoException("Could not get field value because no sub fields defined for scalar models: " + fieldName);
    }

    @Override
    public boolean exists(final String fieldName, final Object object)  {
        throw new EoException("No sub fields defined for scalar models: " + fieldName);
    }

    @Override
    public void remove(final String fieldName, final Object object)  {
        throw new EoException("Could not remove sub field because not sub fields defined for scalar models: " + fieldName);
    }

    @Override
    public Object create() {
        return null;
    }

    @Override
    public boolean isNumber() {
        return getModelClass().getSimpleName().matches("(Integer|Float|Double|Long)");
    }

    @Override
    public boolean isEnum() {
        return (getModelClass().isEnum());
    }

}
