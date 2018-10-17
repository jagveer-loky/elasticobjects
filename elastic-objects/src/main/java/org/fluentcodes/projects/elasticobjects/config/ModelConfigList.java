package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigList extends ModelConfig implements ModelInterface {
    private static final Logger LOG = LogManager.getLogger(ConfigImpl.class);

    public ModelConfigList(EOConfigsCache provider, Builder bean) {
        super(provider, bean);
    }

    @Override
    public String getTable() {
        return null;
    }

    @Override
    public ModelInterface getFieldModel(final String fieldName) throws Exception {
        return getConfigsCache().findModel(Object.class); //TODO
    }

    @Override
    public FieldConfig getField(final String fieldName) {
        return null; //TODO
    }


    @Override
    public Class getFieldClass(String fieldName) {
        return Object.class;
    }


    public Set<String> keys(Object object) throws Exception {
        resolve();
        int size = -1;
        if (object instanceof List) {
            size = ((List) object).size();
        }
        Set<String> fields = new LinkedHashSet<>();
        for (int i = 0; i < size; i++) {
            fields.add(new Integer(i).toString());
        }
        return fields;
    }

    @Override
    public int size(final Object object) throws Exception {
        resolve();
        int counter = 0;
        int size = -1;
        if (object instanceof List) {
            size = ((List) object).size();
        }
        for (int i = 0; i < size; i++) {
            if (((List) object).get(i) == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    public List<Object> keysAsIs(final Object object) throws Exception {
        resolve();
        int size = -1;
        if (object instanceof List) {
            size = ((List) object).size();
        }
        List<Integer> fields = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fields.add(new Integer(i));
        }
        return (List) fields;
    }


    @Override
    public void set(final String fieldName, final Object object, final Object value) throws Exception {
        resolve();
        //TODO
        Integer i = Integer.parseInt(fieldName);
        if (i == ((List) object).size()) {
            ((List) object).add(value);
        } else if (i < ((List) object).size()) {
            ((List) object).set(i, value);
        } else {
            for (int j = ((List) object).size(); j < i; j++) {
                ((List) object).add(null);
            }
            ((List) object).add(value);
        }
    }

    @Override
    public Object getAsIs(final Object fieldNameAsObject, final Object object) throws Exception {
        resolve();
        if (fieldNameAsObject == null) {
            throw new Exception("Getter: null key request for " + this.getModelKey() + "! ");
        }
        //TODO
        String fieldName = ScalarConverter.toString(fieldNameAsObject);
        Integer i = Integer.parseInt(fieldName);
        if (i + 1 > ((List) object).size()) {
            return null;
        }
        return ((List) object).get(i);
    }

    @Override
    public Object get(final String fieldName, final Object object) throws Exception {
        resolve();
        //TODO
        Integer i = ((List) object).size();
        try {
            i = Integer.parseInt(fieldName);
        } catch (Exception e) {

        }
        if (i + 1 > ((List) object).size()) {
            return null;
        }
        return ((List) object).get(i);
    }

    @Override
    public boolean exists(final String fieldName, final Object object) throws Exception {
        resolve();
        //TODO
        Integer i = Integer.parseInt(fieldName);
        return i <= ((List) object).size() - 1;
    }

    @Override
    public boolean hasKey(final String fieldName) {
        if (fieldName.matches("^\\d$")) {
            return true;
        }
        return true; // use size as key for values not matching.
    }

    @Override
    public void remove(final String fieldName, final Object object) throws Exception {
        resolve();
        //TODO
        Integer i = Integer.parseInt(fieldName);
        List list = ((List) object);
        if (((List) object).size() <= i) {
            throw new Exception("List size " + list.size() + " greater than " + i);
        } else if (i + 1 == list.size()) {
            ((List) object).remove(i.intValue());
            return;
        }
        if (((List) object).get(i) == null) {
            throw new Exception("List value for " + i + " is already null.");
        }
        list.set(i.intValue(), null);
        LOG.info("Size of list: " + list.size());

    }

    @Override
    public Object create() throws Exception {
        resolve();
        return new ArrayList();
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
        return false;
    }

    public boolean isSet() {
        return false;
    }

    public boolean isList() {
        return true;
    }

    public boolean isListType() {
        return true;
    }
    public boolean isMapType() {
        return false;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isNull() {
        return false;
    }


}
