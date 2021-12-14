package org.fluentcodes.projects.elasticobjects.models;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ShapeTypeSerializerNumberTest {
    @Test
    public void asStringFloat2() {
        assertEquals("2.0",
                new ShapeTypeSerializerNumber().asString(new Float(2.0)));
    }

    @Test
    public void asStringFloatNative2() {
        assertEquals("2",
                new ShapeTypeSerializerNumber().asString(2));
    }

    @Test
    public void asStringFloat2_1() {
        assertEquals("2.1",
                new ShapeTypeSerializerNumber().asString(new Float(2.1)));
    }

    @Test
    public void stringifyFloat() {
        assertEquals("1.111111111111E9",
                new ShapeTypeSerializerNumber().asString(+1111111111.111));
    }

    @Test
    public void asStringDouble2() {
        assertEquals("2.0",
                new ShapeTypeSerializerNumber().asString(new Double(2.0)));
    }

    @Test
    public void asStringDouble2_1() {
        assertEquals("2.1",
                new ShapeTypeSerializerNumber().asString(new Double(2.1)));
    }

    @Test
    public void asStringLong2() {
        assertEquals("2",
                new ShapeTypeSerializerNumber().asString(new Long(2)));
    }

    @Test
    public void asStringInteger2() {
        assertEquals("2",
                new ShapeTypeSerializerNumber().asString(new Integer(2)));
    }

}
