package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.calls.values.StringUpperFirstCharCall;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigObject extends ModelConfig {

    public ModelConfigObject(Map map) {
        this(new ModelBean(map));
    }

    public ModelConfigObject(ConfigBean bean) {
        this((ModelBean) bean);
    }

    public ModelConfigObject(ModelBean bean) {
        super(bean);
    }

    public static String getter(final String field) {
        return "get" + StringUpperFirstCharCall.upper(field);
    }

    @Override
    public ModelConfig getFieldModel(final String fieldName)  {
        return getFieldConfig(fieldName).getModelConfig();
    }

    public Models getFieldModels(final String fieldName)  {
        return getFieldConfig(fieldName).getModels();
    }

    public ModelConfigInterfaceMethods getFieldChild(final String fieldName)  {
        return getFieldConfig(fieldName).getChildModel();
    }

    @Override
    public Class getFieldClass(final String fieldName)  {
        return getFieldConfig(fieldName).getModelClass();
    }

    @Override
    public Set<String> keys(final Object object)  {
        return this.getFieldKeys();
    }

    @Override
    public int size(final Object object)  {
        int counter = 0;
        for (String key : this.getFieldKeys()) {
            if (get(key, object) == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    @Override
    public boolean set(final String fieldName, final Object parent, final Object value)  {
        getFieldConfig(fieldName).set(parent, value);
        return true;
    }

    @Override
    public Object get(final String fieldName, final Object parent)  {
        return getFieldConfig(fieldName).get(parent);
    }

    @Override
    public boolean exists(final String fieldName, final Object parent)  {
        return get(fieldName, parent)!=null;
    }

    @Override
    public void remove(final String fieldName, final Object object)  {
        set(fieldName, object, null);
    }

    @Override
    public Object create()  {
        if (!isCreate()) {
            throw new EoException("ModelConfig has no create flag -> no empty instance will created for '" + getModelKey() + "'");
        }
        if (getShapeType() == ShapeTypes.CONFIG) {
            throw new EoException("A config has no empty constructor and can't initialized by eo " + getModelKey());
        }
        if (!hasDefaultImplementation()) {
             try {
                return getModelClass().newInstance();
            } catch (Exception e) {
                throw new EoException(e);
            }
        } else {
            ModelConfigInterfaceMethods implementation = getDefaultImplementationModel();
            try {
                return implementation.getModelClass().newInstance();
            } catch (Exception e) {
                throw new EoException("Problem create " + this.getModelKey(), e);
            }
        }
    }

    public boolean equals(ModelConfigObject modelCache) {
        if (getModelKey() == null) {
            return false;
        }
        if (modelCache == null) {
            return false;
        }
        return getModelKey().equals(modelCache.getModelKey());
    }

    public boolean isJsonIgnore(final String key) {
        return getFieldConfig(key).isJsonIgnore();
    }
}
