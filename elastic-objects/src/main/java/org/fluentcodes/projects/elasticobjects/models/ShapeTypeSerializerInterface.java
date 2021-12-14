package org.fluentcodes.projects.elasticobjects.models;

public interface ShapeTypeSerializerInterface {
    default String asString(final Object value) {
        return value.toString();
    }

    default String asJson(Object value) {
        return "\"" + asString(value) + "\"";
    }
}
