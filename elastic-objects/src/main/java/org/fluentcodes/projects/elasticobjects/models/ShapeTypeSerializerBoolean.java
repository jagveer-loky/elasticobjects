package org.fluentcodes.projects.elasticobjects.models;

import org.fluentcodes.projects.elasticobjects.exceptions.EoException;

public class ShapeTypeSerializerBoolean implements ShapeTypeSerializerInterface<Boolean> {
    @Override
    public String asString(final Boolean value) {
        if (value == null) {
            return "false";
        }
        return value.toString();
    }

    @Override
    public String asJson(Boolean value) {
        return asString(value);
    }

    @Override
    public Boolean asObject(Object value) {
        if (value == null) {
            return false;
        }
        if (value instanceof Boolean) {
            return (Boolean) value;
        }
        if (value instanceof Number) {
            if (value.equals(0)) {
                return false;
            }
            return true;
        }
        if (value instanceof String) {
            if (value.equals("true")) {
                return true;
            }
            return false;
        }
        throw new EoException("No defined mapping for " + value.getClass());
    }

    @Override
    public Boolean asObject(String value) {
        if (value == null) {
            return false;
        }
        if (value.equals("1")|| value.equals("true")) {
            return true;
        }
        return false;
    }
}
