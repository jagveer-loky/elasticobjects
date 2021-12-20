package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;
import org.fluentcodes.projects.elasticobjects.exceptions.EoInternalException;

public class ShapeTypeSerializerInteger extends ShapeTypeSerializerNumber<Integer> {
    @Override
    public Integer asObject(String object) {
        if (object == null) {
            return null;
        }
        try {
            return Integer.parseInt(object);
        }
        catch (NumberFormatException e) {
            throw new EoException(e);
        }
    }

    @Override
    public Integer asObject(Object object) {
        if (object == null) {
            return null;
        }
        if (object instanceof Integer) {
            return (Integer) object;
        }
        return asObject(object.toString());
    }
}
