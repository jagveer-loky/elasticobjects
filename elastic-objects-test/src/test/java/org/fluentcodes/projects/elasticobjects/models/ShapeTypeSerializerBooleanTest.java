package org.fluentcodes.projects.elasticobjects.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShapeTypeSerializerBooleanTest {
    @Test
    public void asStringTrue() {
        assertEquals("true",
                new ShapeTypeSerializerBoolean().asString(true));
    }

    @Test
    public void asStringFalse() {
        assertEquals("false",
                new ShapeTypeSerializerBoolean().asString(false));
    }

    @Test
    public void asStringNullFalse() {
        assertEquals("false",
                new ShapeTypeSerializerBoolean().asString(null));
    }

    @Test
    public void asString1True() {
        assertEquals("true",
                new ShapeTypeSerializerBoolean().asString(1));
    }

    @Test
    public void asStringStringFalse() {
        assertEquals("false",
                new ShapeTypeSerializerBoolean().asJson("false"));
    }


}
