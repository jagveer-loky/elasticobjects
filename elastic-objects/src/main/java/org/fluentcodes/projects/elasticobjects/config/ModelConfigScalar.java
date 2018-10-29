package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigScalar extends ModelConfig implements ModelInterface {
    private static final Logger LOG = LogManager.getLogger(ConfigImpl.class);

    public ModelConfigScalar(EOConfigsCache provider, Builder bean) {
        super(provider, bean);
    }

    @Override
    public String getTable() {
        return null;
    }

    @Override
    public ModelInterface getFieldModel(final String fieldName) throws Exception {
        throw new Exception("No field defined for scalar models: " + fieldName);
    }

    @Override
    public FieldConfig getField(final String fieldName) throws Exception {
        throw new Exception("No field defined for scalar models: " + fieldName);
    }


    @Override
    public Class getFieldClass(String fieldName) throws Exception {
        throw new Exception("No field defined for scalar models: " + fieldName);
    }

    @Override
    public Set<String> keys(Object object) throws Exception {
        throw new Exception("No field defined for scalar models! " + object.getClass().getSimpleName());
    }

    @Override
    public int size(final Object object) throws Exception {
        throw new Exception("No field defined for scalar models!" + object.getClass().getSimpleName());
    }

    @Override
    public boolean isEmpty(final Object object) throws Exception {
        return object == null;
    }

    @Override
    public void set(final String fieldName, final Object object, final Object value) throws Exception {
        throw new Exception("No field defined for scalar models: " + fieldName);
    }

    @Override
    public Object getAsIs(final Object fieldNameAsObject, final Object object) throws Exception {
        throw new Exception("No field defined for scalar models!" + object.getClass().getSimpleName());
    }

    @Override
    public Object get(final String fieldName, final Object object) throws Exception {
        throw new Exception("No field defined for scalar models: " + fieldName);
    }

    @Override
    public boolean exists(final String fieldName, final Object object) throws Exception {
        throw new Exception("No field defined for scalar models: " + fieldName);
    }

    @Override
    public boolean hasKey(final String fieldName) {
        return false;
    }

    @Override
    public void remove(final String fieldName, final Object object) throws Exception {
        throw new Exception("No field defined for scalar models: " + fieldName);
    }

    @Override
    public Object create() {
        return null;
    }

    public boolean hasModel() {
        return true;
    }

    public boolean isScalar() {
        try {
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isEnum() {
        try {
            return this.getModelClass().isEnum();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public boolean isMap() {
        return false;
    }

    public boolean isSet() {
        return false;
    }

    public boolean isList() {
        return false;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isListType() {
        return false;
    }

    public boolean isMapType() {
        return false;
    }

    public boolean isNull() {
        return false;
    }


}
