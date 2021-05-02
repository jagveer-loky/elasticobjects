package org.fluentcodes.projects.elasticobjects.models;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;
import org.fluentcodes.projects.elasticobjects.utils.ScalarConverter;

import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * Created by Werner on 09.10.2016.
 */
public class ModelConfigList extends ModelConfig implements ModelConfigInterfaceMethods {
    private static final Logger LOG = LogManager.getLogger(ConfigConfig.class);
    public static final String CONFIG_MODEL_KEY = "ModelConfigList";

    public ModelConfigList(ConfigBean bean) {
        this((ModelBean) bean);
    }

    public ModelConfigList(ModelBean bean) {
        super(bean);
    }

    @Override
    public ModelConfig getFieldModel(final String fieldName)  {
        return null; //getConfigsCache().findModel(Object.class); //TODO
    }

    @Override
    public FieldConfig getField(final String fieldName) {
        return null; //TODO
    }

    public Set<String> keys(Object object)  {
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
    public boolean set(final String fieldName, final Object object, final Object value)  {
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
        return true;
    }

    @Override
    public Object get(final String fieldName, final Object object)  {
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
        //TODO
        Integer i = Integer.parseInt(fieldName);
        return i <= ((List) object).size() - 1;
    }

    @Override
    public void remove(final String fieldName, final Object object)  {
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
        return new ArrayList();
    }

}
