package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoException;

import java.util.*;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigMap extends ModelConfig implements ModelInterface {
    private static final Logger LOG = LogManager.getLogger(ModelConfigMap.class);

    public ModelConfigMap(EOConfigsCache provider, Builder bean) {
        super(provider, bean);
    }

    @Override
    public String getTable() {
        return null;
    }

    @Override
    public ModelInterface getFieldModel(final String fieldName)  {
        return getConfigsCache().findModel(Object.class); //TODO
    }


    @Override
    public FieldConfig getField(final String fieldName) {
        return null; //TODO
    }

    @Override
    public Set<String> keys(Object object)  {
        resolve();
        return ((Map) object).keySet();
    }

    public int size(Object object)  {
        resolve();
        int counter = 0;
        for (Object key : ((Map) object).keySet()) {
            if (((Map) object).get(key) == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    public List<Object> keysAsIs(Object object)  {
        resolve();
        return new ArrayList(((Map) object).keySet());
    }

    public void set(String fieldName, Object object, Object value)  {
        resolve();
        ((Map) object).put(fieldName, value);
    }

    /**
     * Gets the value for fieldName of the object.
     *
     * @param fieldNameAsObject
     * @param object
     * @return
     * @
     */
    public Object getAsIs(Object fieldNameAsObject, Object object)  {
        resolve();
        if (fieldNameAsObject == null) {
            throw new EoException("Getter: null key request for " + this.getModelKey() + "! ");
        }
        return ((Map) object).get(fieldNameAsObject);
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
        resolve();
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

    public boolean exists(final String fieldName, final Object object)  {
        resolve();
        return ((Map) object).containsKey(fieldName);

    }

    @Override
    public boolean hasKey(final String fieldName) {
        return true;
    }

    public void remove(final String fieldName, final Object object)  {
        resolve();
        get(fieldName, object);
        ((Map) object).remove(fieldName);
    }

    public Object create() {
        resolve();
        return new LinkedHashMap<>();
    }

    public boolean hasModel() {
        return true;
    }

    public boolean isScalar() {
        return false;
    }

    public boolean isEnum() {
        return false;
    }

    public boolean isMap() {
        return true;
    }

    public boolean isSet() {
        return false;
    }

    public boolean isList() {
        return false;
    }

    public boolean isListType() {
        return false;
    }

    public boolean isMapType() {
        return true;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isNull() {
        return false;
    }

}
