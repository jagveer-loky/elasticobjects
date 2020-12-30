package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigMap extends ModelConfig implements ModelConfigInterfaceMethods {
    public static final String CONFIG_MODEL_KEY = "ModelConfigMap";

    public ModelConfigMap(Map map) {
        this(new ModelBean(map));
    }

    public ModelConfigMap(ConfigBean bean) {
        this((ModelBean) bean);
    }

    public ModelConfigMap(ModelBean bean) {
        super(bean);
    }

    @Override
    public ModelConfig getFieldModel(final String fieldName)  {
        return null; //TODO
    }


    @Override
    public FieldConfig getFieldConfig(final String fieldName) {
        return null; //TODO
    }

    @Override
    public Set<String> keys(Object object)  {
        return ((Map) object).keySet();
    }

    public int size(Object object)  {
        int counter = 0;
        for (Object key : ((Map) object).keySet()) {
            if (((Map) object).get(key) == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    public boolean set(String fieldName, Object object, Object value)  {
        ((Map) object).put(fieldName, value);
        return true;
    }

    /**
     * Gets the value for fieldName of the object.
     *
     * @param fieldName
     * @param object
     * @return
     * @
     */
    public Object get(String fieldName, Object object)  {
        if (((Map) object).containsKey(fieldName)) {
            return ((Map) object).get(fieldName);
        } else if (fieldName.matches("^\\d+$")) {
            Integer i = Integer.parseInt(fieldName);
            if (((Map) object).containsKey(i)) {
                return ((Map) object).get(i);
            }
        }
        throw new EoException("No value add for fieldName=" + fieldName);

    }

    public Set<String> getFieldKeys() {
        return getFieldConfigMap().keySet();
    }

    public boolean exists(final String fieldName, final Object object)  {
        return ((Map) object).containsKey(fieldName);
    }


    public void remove(final String fieldName, final Object object)  {
        get(fieldName, object);
        ((Map) object).remove(fieldName);
    }

    public Object create() {
        return new LinkedHashMap<>();
    }

}
