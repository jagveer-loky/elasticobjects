package org.fluentcodes.projects.elasticobjects.models;

public class ShapeTypeSerializerBoolean implements ShapeTypeSerializerInterface {
    @Override
    public String asString(Object value) {
        if (value == null) {
            return "false";
        }
        if (value instanceof Boolean) {
            return value.toString();
        }
        if (value instanceof String) {
            return value.equals("1")||((String)value).equals("true") ? "true":"false";
        }
        return "true";

    }
    @Override
    public String asJson(Object value) {
        return asString(value);
    }
}
