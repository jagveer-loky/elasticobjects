package org.fluentcodes.projects.elasticobjects.models;

public abstract class ShapeTypeSerializerNumber<T extends Number> implements ShapeTypeSerializerInterface<T> {

    @Override
    public String asJson(T value) {
        return asString(value);
    }
}
