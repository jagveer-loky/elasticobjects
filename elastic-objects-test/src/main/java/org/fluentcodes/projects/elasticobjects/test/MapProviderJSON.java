package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;

import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

public class MapProviderJSON {
    
    public static String readEmpty()  {
        return JSONInputReader.readInputJSON(EMPTY);
    }

    public static String getJSONEmpty()  {
        String eo = JSONInputReader.readInputJSON(EMPTY);
        return eo;
    }

    public static EO createEmpty()  {
        final EO eo = TestEOProvider.create(readEmpty());
        BTProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO compareEmpty()  {
        EO eo = JSONInputReader.compareInputJSON(EMPTY, MapProviderEO.createEmpty());
        Assert.assertEquals(Map.class, eo.getModelClass());
        return eo;
    }

    public static String getJSONEmptyValues()  {
        String eo = JSONInputReader.readInputJSON(EMPTY_VALUES);
        return eo;
    }

    public static String readString()  {
        return readInputJSON(STRING);
    }

    public static EO createString()  {
        final EO eo = TestEOProvider.create(readString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString()  {
        EO eo = JSONInputReader.compareInputJSON(STRING, MapProviderEO.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static String readInteger()  {
        return readInputJSON(INT);
    }

    public static EO createInteger()  {
        final EO eo = TestEOProvider.create(readInteger());
        assertInteger(eo);
        return eo;
    }

    public static EO compareInteger()  {
        EO eo = JSONInputReader.compareInputJSON(INT, MapProviderEO.createInteger());
        assertInteger(eo);
        return eo;
    }

    public static void assertInteger(final EO eo)  {
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_INTEGER));
    }

    public static String readLong()  {
        return readInputJSON(LONG);
    }

    public static EO createLong()  {
        final EO eo = TestEOProvider.create(readLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO compareLong()  {
        EO eo = JSONInputReader.compareInputJSON(LONG, MapProviderEO.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static String readFloat()  {
        return readInputJSON(FLOAT);
    }

    public static EO createFloat()  {
        final EO eo = TestEOProvider.create(readFloat());
        assertFloat(eo);
        return eo;
    }

    public static EO compareFloat()  {
        EO eo = JSONInputReader.compareInputJSON(FLOAT, MapProviderEO.createFloat());
        assertFloat(eo);
        return eo;
    }

    public static void assertFloat(final EO eo)  {
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(F_TEST_FLOAT));
    }


    public static String readDouble()  {
        return readInputJSON(DOUBLE);
    }

    public static EO createDouble()  {
        final EO eo = TestEOProvider.create(readDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO compareDouble()  {
        EO eo = JSONInputReader.compareInputJSON(DOUBLE, MapProviderEO.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }


    public static String readDate()  {
        return readInputJSON(DATE);
    }

    public static EO createDate()  {
        final EO eo = TestEOProvider.create(readDate());
        assertDate(eo);
        return eo;
    }

    public static EO compareDate()  {
        EO eo = JSONInputReader.compareInputJSON(DATE, MapProviderEO.createDate());
        assertDate(eo);
        return eo;
    }

    public static void assertDate(final EO eo)  {
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(F_TEST_DATE));
    }


    public static String readBoolean()  {
        return readInputJSON(BOOLEAN);
    }

    public static EO createBoolean()  {
        final EO eo = TestEOProvider.create(readBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO compareBoolean()  {
        EO eo = JSONInputReader.compareInputJSON(BOOLEAN, MapProviderEO.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }


    public static String readMap()  {
        return readInputJSON(MAP);
    }

    public static EO createMap()  {
        final EO eo = TestEOProvider.create(readMap());
        assertMap(eo);
        return eo;
    }

    public static EO compareMap()  {
        EO eo = JSONInputReader.compareInputJSON(MAP, MapProviderEO.createMap());
        assertMap(eo);
        return eo;
    }

    public static void assertMap(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_UNTYPED_MAP + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_UNTYPED_MAP + Path.DELIMITER + F_TEST_INTEGER));
    }


    public static String readList()  {
        return readInputJSON(LIST);
    }

    public static EO createList()  {
        final EO eo = TestEOProvider.create(readList());
        assertList(eo);
        return eo;
    }

    public static EO compareList()  {
        EO eo = JSONInputReader.compareInputJSON(LIST, MapProviderEO.createList());
        assertList(eo);
        return eo;
    }

    public static void assertList(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_UNTYPED_LIST + Path.DELIMITER + S0));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_UNTYPED_LIST + Path.DELIMITER + S1));
    }

    public static String readBT()  {
        return readInputJSON(BASIC_TEST);
    }

    public static EO createBT()  {
        final EO eo = TestEOProvider.create(readBT());
        assertBT(eo);
        return eo;
    }

    public static EO compareBT()  {
        EO eo = JSONInputReader.compareInputJSON(BASIC_TEST, MapProviderEO.createBT());
        assertBT(eo);
        return eo;
    }

    public static void assertBT(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_BASIC_TEST + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_BASIC_TEST + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static String readST()  {
        return readInputJSON(SUB_TEST);
    }

    public static EO createST()  {
        final EO eo = TestEOProvider.create(readST());
        BTProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST()  {
        EO eo = JSONInputReader.compareInputJSON(SUB_TEST, MapProviderEO.createST());
        BTProviderEO.assertST(eo);
        return eo;
    }


    public static String readMapST()  {
        return readInputJSON(SUB_TEST_MAP);
    }

    public static EO createMapST()  {
        final EO eo = TestEOProvider.create(readMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }

    public static EO compareMapST()  {
        EO eo = JSONInputReader.compareInputJSON(SUB_TEST_MAP, MapProviderEO.createMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }


    public static String readListST()  {
        return readInputJSON(SUB_TEST_LIST);
    }

    public static EO createListST()  {
        final EO eo = TestEOProvider.create(readListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static EO compareListST()  {
        EO eo = JSONInputReader.compareInputJSON(SUB_TEST_LIST, MapProviderEO.createListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static String readSmall()  {
        return JSONInputReader.readInputJSON(SMALL);
    }

    public static EO createSmall()  {
        final EO eo = TestEOProvider.create(readSmall());
        assertSmall(eo);
        return eo;
    }

    public static EO compareSmall()  {
        EO eo = JSONInputReader.compareInputJSON(SMALL, MapProviderEO.createSmall());
        assertSmall(eo);
        return eo;
    }

    public static void assertSmall(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_INTEGER));
    }


    public static String readSimple()  {
        return JSONInputReader.readInputJSON(SIMPLE);
    }

    public static EO createSimple()  {
        final EO eo = TestEOProvider.create(readSimple());
        assertSimple(eo);
        return eo;
    }

    public static EO compareSimple()  {
        EO eo = JSONInputReader.compareInputJSON(SIMPLE, MapProviderEO.createSimple());
        assertSimple(eo);
        return eo;
    }

    public static void assertSimple(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_INTEGER));
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(F_TEST_FLOAT));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(F_TEST_DATE));
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }


    public static String readAll()  {
        return read();
    }

    public static String read()  {
        return JSONInputReader.readInputJSON(ALL);
    }

    public static EO create()  {
        final EO eo = TestEOProvider.create(read());
        asserts(eo);
        return eo;
    }

    public static EO compare()  {
        final EO eo = JSONInputReader.compareInputJSON(ALL, MapProviderEO.create());
        asserts(eo);
        return eo;
    }

    public static void asserts(final EO eo)  {
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_INTEGER));
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(F_TEST_FLOAT));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(F_TEST_DATE));
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }


    public static String getJSONSmallWithKeys()  {
        String eo = JSONInputReader.readInputJSON(SMALL_WITH_KEYS);
        return eo;
    }

    public static String getJSONSmallWithKeysAndList()  {
        String eo = JSONInputReader.readInputJSON(SMALL_WITH_KEYS_AND_LIST);
        return eo;
    }

    public static final String toJSONMap(Object... keyValues) {
        StringBuilder builder = new StringBuilder("{");
        if (keyValues == null || keyValues.length == 0) {
            return builder.append("}").toString();
        }
        for (int i = 0; i < keyValues.length; i++) {
            builder.append("\"");
            builder.append(keyValues[i]);
            builder.append("\": ");
            i++;
            if (i == keyValues.length) {
                builder.append("null");
                continue;
            }
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
            } else {
                builder.append(keyValues[i]);
            }
            if (i != keyValues.length - 1) {
                builder.append(",");
            }
        }
        return builder.append("}").toString();
    }
}
