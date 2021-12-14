package org.fluentcodes.projects.elasticobjects.models;

public class ShapeTypeSerializer implements ShapeTypeSerializerInterface {
    @Override
    public String asString(Object value) {
        String string = value.toString();
        return string;
    }

}
