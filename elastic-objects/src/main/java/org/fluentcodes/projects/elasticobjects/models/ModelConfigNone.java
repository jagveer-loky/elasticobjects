package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.List;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigNone extends ModelConfig {
    public static final String CONFIG_MODEL_KEY = "ModelConfigNone";
    public static final String NO_FIELD_DEFINED_FOR_SCALAR_MODELS = "No field defined for scalar models: ";

    public ModelConfigNone(ConfigBean bean, final ConfigMaps configMaps) {
        this((ModelBean) bean, configMaps);
    }

    public ModelConfigNone(ModelBean bean, final ConfigMaps configMaps) {
        super(bean, configMaps);
    }

    @Override
    public ModelConfig getFieldModel(final String fieldName)  {
        throw new EoException(NO_FIELD_DEFINED_FOR_SCALAR_MODELS + fieldName);
    }

    @Override
    public FieldConfig getField(final String fieldName)  {
        throw new EoException(NO_FIELD_DEFINED_FOR_SCALAR_MODELS + fieldName);
    }

    public Set<String> keys(Object object)  {
        throw new EoException(NO_FIELD_DEFINED_FOR_SCALAR_MODELS + object.getClass().getSimpleName());
    }

    public List<Object> keys(final Object object, final String filter)  {
        throw new EoException("No field defined for scalar models!" + object.getClass().getSimpleName());
    }

    @Override
    public int size(final Object object)  {
        throw new EoException(NO_FIELD_DEFINED_FOR_SCALAR_MODELS + object.getClass().getSimpleName());
    }


    @Override
    public boolean isEmpty(final Object object)  {
        throw new EoException(NO_FIELD_DEFINED_FOR_SCALAR_MODELS + object.getClass().getSimpleName());
    }

    @Override
    public boolean set(final String fieldName, final Object object, final Object value)  {
        throw new EoException(NO_FIELD_DEFINED_FOR_SCALAR_MODELS + fieldName);
    }

    @Override
    public Object get(final String fieldName, final Object object)  {
        throw new EoException(NO_FIELD_DEFINED_FOR_SCALAR_MODELS + fieldName);
    }

    @Override
    public boolean exists(final String fieldName, final Object object)  {
        throw new EoException(NO_FIELD_DEFINED_FOR_SCALAR_MODELS + fieldName);
    }

    @Override
    public void remove(final String fieldName, final Object object)  {
        throw new EoException(NO_FIELD_DEFINED_FOR_SCALAR_MODELS + fieldName);
    }

    @Override
    public Object create() {
        return null;
    }

    @Override
    public boolean hasModel() {
        return false;
    }

    @Override
    public boolean isNull() {
        return true;
    }
}
