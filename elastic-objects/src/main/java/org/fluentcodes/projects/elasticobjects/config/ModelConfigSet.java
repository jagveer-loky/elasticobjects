package org.fluentcodes.projects.elasticobjects.config;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigSet extends ModelConfig implements ModelInterface {
    private static final Logger LOG = LogManager.getLogger(ConfigImpl.class);

    public ModelConfigSet(EOConfigsCache provider, Builder bean) {
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
    public Class getFieldClass(String fieldName) {
        return Object.class;
    }


    public Set<String> keys(Object object)  {
        resolve();
        int size = -1;
        if (object instanceof List) {
            size = ((Set) object).size();
        }
        Set<String> fields = new LinkedHashSet<>();
        for (int i = 0; i < size; i++) {
            fields.add(new Integer(i).toString());
        }
        return fields;
    }

    @Override
    public int size(final Object object)  {
        resolve();
        int counter = 0;
        int size = -1;
        if (object instanceof List) {
            size = ((Set) object).size();
        }
        for (Object entry : (Set) object) {
            if (entry == null) {
                continue;
            }
            counter++;
        }
        return counter;
    }

    public List<Object> keysAsIs(final Object object)  {
        resolve();
        int size = -1;
        if (object instanceof List) {
            size = ((Set) object).size();
        }
        List<Integer> fields = new ArrayList<>();
        for (int i = 0; i < size; i++) {
            fields.add(new Integer(i));
        }
        return null;//(Set) fields;
    }

    @Override
    public boolean isEmpty(final Object object)  {
        resolve();
        if (object == null) {
            return true;
        }
        int size = -1;
        if (((Set) object).size() == 0) {
            return true;
        }
        for (Object value : (Set) object) {
            if (value != null) {
                return false;
            }
        }
        return false;
    }

    @Override
    public void set(final String fieldName, final Object object, final Object value)  {
        resolve();
        //TODO
        Integer i = Integer.parseInt(fieldName);
        if (i == ((Set) object).size()) {
            ((Set) object).add(value);
        } /*else if (i < ((Set) object).size()) {
      ((Set) object).set(i, value);
    }*/ else {
            for (int j = ((Set) object).size(); j < i; j++) {
                ((Set) object).add(null);
            }
            ((Set) object).add(value);
        }
    }

    @Override
    public Object getAsIs(final Object fieldNameAsObject, final Object object)  {
        resolve();
        if (fieldNameAsObject == null) {
            throw new EoException("Getter: null key request for " + this.getModelKey() + "! ");
        }
        //TODO
        String fieldName = ScalarConverter.toString(fieldNameAsObject);
        Integer i = Integer.parseInt(fieldName);
        if (i + 1 > ((Set) object).size()) {
            return null;
        }
        return null;//((Set) object).find(i);
    }

    @Override
    public Object get(final String fieldName, final Object object)  {
        resolve();
        //TODO
        Integer i = ((Set) object).size();
        try {
            i = Integer.parseInt(fieldName);
        } catch (Exception e) {

        }
        if (i + 1 > ((Set) object).size()) {
            return null;
        }
        return null;//((Set) object).find(i);
    }

    @Override
    public boolean exists(final String fieldName, final Object object)  {
        resolve();
        //TODO
        Integer i = Integer.parseInt(fieldName);
        return i <= ((Set) object).size() - 1;
    }

    @Override
    public boolean hasKey(final String fieldName) {
        if (fieldName.matches("^\\d$")) {
            return true;
        }
        return true; // use size as key for values not matching.
    }

    @Override
    public void remove(final String fieldName, final Object object)  {
        resolve();
        //TODO
        Integer i = Integer.parseInt(fieldName);
        Set list = ((Set) object);
        if (((Set) object).size() <= i) {
            throw new EoException("List size " + list.size() + " greater than " + i);
        } else if (i + 1 == list.size()) {
            ((Set) object).remove(i.intValue());
            return;
        }
        LOG.info("Size of list: " + list.size());

    }

    @Override
    public Object create()  {
        resolve();
        return new ArrayList();
    }

    public boolean hasModel() {
        return false;
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
        return true;
    }

    public boolean isList() {
        return true;
    }

    public boolean isObject() {
        return false;
    }

    public boolean isListType() {
        return true;
    }

    public boolean isMapType() {
        return false;
    }

    public boolean isNull() {
        return false;
    }


}
