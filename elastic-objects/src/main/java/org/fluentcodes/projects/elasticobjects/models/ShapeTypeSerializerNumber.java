package org.fluentcodes.projects.elasticobjects.models;

public class ShapeTypeSerializerNumber implements ShapeTypeSerializerInterface {
    @Override
    public String asJson(Object value) {
        return asString(value);
    }
}
