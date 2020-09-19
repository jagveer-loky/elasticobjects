package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.*;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigList extends ModelConfig implements ModelInterface {
    private static final Logger LOG = LogManager.getLogger(ConfigImpl.class);
    public static final String CONFIG_MODEL_KEY = "ModelConfigList";

    public ModelConfigList(EOConfigsCache provider, Map map) {
        super(provider, map);
    }

    @Override
    public ModelInterface getFieldModel(final String fieldName)  {
        return getConfigsCache().findModel(Object.class); //TODO
    }

    @Override
    public FieldConfig getFieldConfig(final String fieldName) {
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
            size = ((List) object).size();
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

    @Override
    public void set(final String fieldName, final Object object, final Object value)  {
        resolve();
        if (object == null) {
            throw new EoInternalException("List object for " + fieldName + " is null!");
        }
        Integer i = null;
        if (fieldName == null) {
            i = ((List) object).size();
        }
        else {
            try {
                i = Integer.parseInt(fieldName);
            } catch (NumberFormatException e) {
                throw new EoException("Could not parse for integer " + fieldName + ": ", e);
            }
        }
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
    public Object getAsIs(final Object fieldNameAsObject, final Object object)  {
        resolve();
        if (fieldNameAsObject == null) {
            throw new EoException("Getter: null key request for " + this.getModelKey() + "! ");
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
    public Object get(final String fieldName, final Object object)  {
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
    public boolean exists(final String fieldName, final Object object)  {
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
    public void remove(final String fieldName, final Object object)  {
        resolve();
        //TODO
        Integer i = Integer.parseInt(fieldName);
        List list = ((List) object);
        if (((List) object).size() <= i) {
            throw new EoException("List size " + list.size() + " greater than " + i);
        } else if (i + 1 == list.size()) {
            ((List) object).remove(i.intValue());
            return;
        }
        if (((List) object).get(i) == null) {
            throw new EoException("List value for " + i + " is already null.");
        }
        list.set(i.intValue(), null);
        LOG.info("Size of list: " + list.size());

    }

    @Override
    public Object create()  {
        resolve();
        return new ArrayList();
    }
    @Override
    public boolean hasModel() {
        return true;
    }
    @Override
    public boolean isCreate() {
        return true;
    }
    @Override
    public boolean hasSetter(final String fieldName) {
        return true;
    }
    @Override
    public boolean hasGetter(final String fieldName) {
        return true;
    }
    @Override
    public boolean isList() {
        return true;
    }
    @Override
    public boolean isListType() {
        return true;
    }



}
