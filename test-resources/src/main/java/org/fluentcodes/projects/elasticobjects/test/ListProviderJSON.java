package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;

import java.util.Date;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

public class ListProviderJSON {
    public static EOBuilder builder() {
        return TestObjectProvider.createEOBuilder();
    }

    public static String readEmpty() throws Exception {
        return JSONInputReader.readInputJSON(TYPE.LIST, EMPTY);
    }

    public static EO createEmpty() throws Exception {
        final EO eo = builder().map(readEmpty());
        ListProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO compareEmpty() throws Exception {
        EO eoEmpty = JSONInputReader.compareInputJSON(TYPE.LIST, EMPTY, ListProviderEO.createEmpty());
        ListProviderEO.assertEmpty(eoEmpty);
        return eoEmpty;
    }


    public static String readString() throws Exception {
        return JSONInputReader.readInputJSON(TYPE.LIST, STRING);
    }

    public static EO createString() throws Exception {
        final EO eo = builder().map(readString());
        ListProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString() throws Exception {
        final EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, STRING, ListProviderEO.createString());
        ListProviderEO.assertString(eo);
        return eo;
    }


    public static String readInteger() throws Exception {
        return JSONInputReader.readInputJSON(TYPE.LIST, INT);
    }

    public static EO createInteger() throws Exception {
        final EO eo = builder().map(readInteger());
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S0));
        return eo;
    }

    public static EO compareInteger() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, INT, ListProviderEO.createInteger());
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S0));
        return eo;
    }


    public static String readLong() throws Exception {
        return JSONInputReader.readInputJSON(TYPE.LIST, LONG);
    }

    public static EO createLong() throws Exception {
        final EO eo = builder().map(readLong());
        ListProviderEO.assertLong(eo);
        return eo;
    }

    public static EO compareLong() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, LONG, ListProviderEO.createLong());
        ListProviderEO.assertLong(eo);
        return eo;
    }


    public static String readFloat() throws Exception {
        return JSONInputReader.readInputJSON(TYPE.LIST, FLOAT);
    }

    public static EO createFloat() throws Exception {
        final EO eo = builder().map(readFloat());
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(S0));
        return eo;
    }

    public static EO compareFloat() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, FLOAT, ListProviderEO.createFloat());
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(S0));
        return eo;
    }


    public static String readDouble() throws Exception {
        return JSONInputReader.readInputJSON(TYPE.LIST, DOUBLE);
    }

    public static EO createDouble() throws Exception {
        final EO eo = builder().map(readDouble());
        ListProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO compareDouble() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, DOUBLE, ListProviderEO.createDouble());
        ListProviderEO.assertDouble(eo);
        return eo;
    }


    public static String readDate() throws Exception {
        return JSONInputReader.readInputJSON(TYPE.LIST, DATE);
    }

    public static EO createDate() throws Exception {
        final EO eo = builder().map(readDate());
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(S0));
        return eo;
    }

    public static EO compareDate() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, DATE, ListProviderEO.createDate());
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(S0));
        return eo;
    }


    public static String readBoolean() throws Exception {
        return JSONInputReader.readInputJSON(TYPE.LIST, BOOLEAN);
    }

    public static EO createBoolean() throws Exception {
        final EO eo = builder().map(readBoolean());
        ListProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO compareBoolean() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, BOOLEAN, ListProviderEO.createBoolean());
        ListProviderEO.assertBoolean(eo);
        return eo;
    }

    public static String readMap() throws Exception {
        return readInputJSON(TYPE.LIST, MAP);
    }

    public static EO createMap() throws Exception {
        final EO eo = builder().map(readMap());
        Assert.assertEquals(S_STRING, eo.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S0 + Path.DELIMITER + F_TEST_INTEGER));
        return eo;
    }

    public static EO compareMap() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, MAP, ListProviderEO.createMap());
        Assert.assertEquals(S_STRING, eo.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S0 + Path.DELIMITER + F_TEST_INTEGER));
        return eo;
    }

    public static String readList() throws Exception {
        return readInputJSON(TYPE.LIST, LIST);
    }

    public static EO createList() throws Exception {
        final EO eo = builder().map(readList());
        Assert.assertEquals(S_STRING, eo.get(S0 + Path.DELIMITER + S0));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S0 + Path.DELIMITER + S1));
        return eo;
    }

    public static EO compareList() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, LIST, ListProviderEO.createList());
        Assert.assertEquals(S_STRING, eo.get(S0 + Path.DELIMITER + S0));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S0 + Path.DELIMITER + S1));
        return eo;
    }

    public static String readST() throws Exception {
        return readInputJSON(TYPE.LIST, SUB_TEST);
    }

    public static EO createST() throws Exception {
        final EO eo = builder().map(readST());
        ListProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, SUB_TEST, ListProviderEO.createST());
        ListProviderEO.assertST(eo);
        return eo;
    }

    public static String readBT() throws Exception {
        return readInputJSON(TYPE.LIST, BASIC_TEST);
    }

    public static EO createBT() throws Exception {
        final EO eo = builder().map(readBT());
        Assert.assertEquals(S_STRING, eo.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S0 + Path.DELIMITER + F_TEST_INTEGER));
        return eo;
    }

    public static EO compareBT() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, BASIC_TEST, ListProviderEO.createBT());
        Assert.assertEquals(S_STRING, eo.get(S0 + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S0 + Path.DELIMITER + F_TEST_INTEGER));
        return eo;
    }


    public static String readSmall() throws Exception {
        return readInputJSON(TYPE.LIST, SMALL);
    }

    public static String getSmall() throws Exception {
        return JSONInputReader.readInputJSON(TYPE.LIST, SMALL);
    }

    public static EO createSmall() throws Exception {
        final EO eo = builder().map(readSmall());
        Assert.assertEquals(S_STRING, eo.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S1));
        return eo;
    }

    public static EO compareSmall() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, SMALL, ListProviderEO.createSmall());
        Assert.assertEquals(S_STRING, eo.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S1));
        return eo;
    }


    public static String readSimple() throws Exception {
        return readInputJSON(TYPE.LIST, SIMPLE);
    }

    public static EO createSimple() throws Exception {
        final EO eo = builder().map(readSimple());
        assertSimple(eo);
        return eo;
    }

    public static EO compareSimple() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, SIMPLE, ListProviderEO.createSimple());
        assertSimple(eo);
        return eo;
    }

    public static void assertSimple(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S1));
        Assert.assertEquals(SAMPLE_LONG, eo.get(S2));
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(S3));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(S4));
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(S5));
        Assert.assertEquals(S_BOOLEAN, eo.get(S6));
    }


    public static String readAll() throws Exception {
        return readInputJSON(TYPE.LIST, ALL);
    }

    public static EO create() throws Exception {
        final EO eo = builder().map(readAll());
        asserts(eo);
        return eo;
    }

    public static EO compare() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(TYPE.LIST, ALL, ListProviderEO.create());
        asserts(eo);
        return eo;
    }

    public static void asserts(final EO eo) throws Exception {
        Assert.assertEquals(S_STRING, eo.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(S1));
        Assert.assertEquals(SAMPLE_LONG, eo.get(S2));
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(S3));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(S4));
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(S5));
        Assert.assertEquals(S_BOOLEAN, eo.get(S6));
    }

    public static final String toJSONList(Object... keyValues) {
        StringBuilder builder = new StringBuilder("[");
        if (keyValues == null || keyValues.length == 0) {
            return builder.append("]").toString();
        }
        for (int i = 0; i < keyValues.length; i++) {
            Object object = keyValues[i];
            if (object instanceof String) {
                String value = (String) keyValues[i];
                if (value.startsWith("[") || value.startsWith("{")) {
                    builder.append(keyValues[i]);
                } else {
                    builder.append("\"");
                    builder.append(keyValues[i]);
                    builder.append("\"");
                }
            } else if (object instanceof List) {
                builder.append("[]");
            } else if (object instanceof Map) {
                builder.append("{}");
            } else if (object instanceof Date) {
                builder.append(((Date) object).getTime());
            } else {
                builder.append(keyValues[i]);
            }
            if (i != keyValues.length - 1) {
                builder.append(",");
            }
        }
        return builder.append("]").toString();
    }

    public static final String createRow() {
        return toJSONList(MapProviderJSON.toJSONMap(S_KEY1, S_VALUE11, S_KEY2, S_VALUE12));
    }

    public static final String createJsonArray() {
        return toJSONList(toJSONList(S_KEY1, S_KEY2), toJSONList(S_VALUE11, S_VALUE12));
    }
}
