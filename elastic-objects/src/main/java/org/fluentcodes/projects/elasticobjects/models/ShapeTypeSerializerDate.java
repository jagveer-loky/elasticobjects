package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

import java.util.Date;

public class ShapeTypeSerializerDate implements ShapeTypeSerializerInterface {
    @Override
    public String asString(Object value) {
        if (value instanceof Date) {
            return Long.toString(((Date)value).getTime());
        }
        throw new EoException("Not instance of Date but " + value.getClass());
    }

    @Override
    public String asJson(final Object object) {
        return asString(object);
    }
}
