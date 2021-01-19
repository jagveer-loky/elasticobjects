package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigSet extends ModelConfig {
    private static final Logger LOG = LogManager.getLogger(ModelConfigSet.class);

    public ModelConfigSet(Map map) {
        this(new ModelBean(map));
    }

    public ModelConfigSet(ConfigBean bean) {
        this((ModelBean) bean);
    }

    public ModelConfigSet(ModelBean bean) {
        super(bean);
    }

    @Override
    public ModelConfig getFieldModel(final String fieldName)  {
        return null;//getConfigsCache().findModel(Object.class); //TODO
    }

    @Override
    public FieldConfig getField(final String fieldName) {
        return null; //TODO
    }


    public Set<String> keys(Object object)  {
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

    @Override
    public boolean isEmpty(final Object object)  {
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
    public boolean set(final String fieldName, final Object object, final Object value)  {
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
        return true;
    }

    @Override
    public Object get(final String fieldName, final Object object)  {
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
        //TODO
        Integer i = Integer.parseInt(fieldName);
        return i <= ((Set) object).size() - 1;
    }

    @Override
    public void remove(final String fieldName, final Object object)  {
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
        return new ArrayList();
    }
    @Override
    public boolean hasModel() {
        return false;
    }


}
