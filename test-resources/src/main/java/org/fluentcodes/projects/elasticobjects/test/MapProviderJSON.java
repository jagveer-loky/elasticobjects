package org.fluentcodes.projects.elasticobjects.test;

import com.sun.javafx.collections.MappingChange;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;

import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

public class MapProviderJSON {
    public static EOBuilder builder() {
        return TestObjectProvider.createEOBuilder();
    }

    public static String readEmpty() throws Exception {
        return JSONInputReader.readInputJSON(EMPTY);
    }

    public static String getJSONEmpty () throws Exception {
        String eo = JSONInputReader.readInputJSON(EMPTY);
        return eo;
    }

    public static EO createEmpty() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readEmpty());
        BTProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO compareEmpty() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(EMPTY, MapProviderEO.createEmpty());
        Assert.assertEquals(Map.class, eo.getModelClass());
        return eo;
    }

    public static String getJSONEmptyValues () throws Exception {
        String eo = JSONInputReader.readInputJSON(EMPTY_VALUES);
        return eo;
    }

    public static String readString () throws Exception {
        return readInputJSON(STRING);
    }

    public static EO createString() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(STRING, MapProviderEO.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static String readInteger () throws Exception {
        return readInputJSON(INT);
    }

    public static EO createInteger() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readInteger());
        assertInteger(eo);
        return eo;
    }

    public static EO compareInteger() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(INT, MapProviderEO.createInteger());
        assertInteger(eo);
        return eo;
    }
    public static void assertInteger(final EO eo) throws Exception{
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_INTEGER));
    }

    public static String readLong () throws Exception {
        return readInputJSON(LONG);
    }

    public static EO createLong() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO compareLong() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(LONG, MapProviderEO.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static String readFloat () throws Exception {
        return readInputJSON(FLOAT);
    }

    public static EO createFloat() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readFloat());
        assertFloat(eo);
        return eo;
    }

    public static EO compareFloat() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(FLOAT, MapProviderEO.createFloat());
        assertFloat(eo);
        return eo;
    }
    public static void assertFloat(final EO eo) throws Exception {
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(F_TEST_FLOAT));
    }


    public static String readDouble () throws Exception {
        return readInputJSON(DOUBLE);
    }

    public static EO createDouble() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO compareDouble() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(DOUBLE, MapProviderEO.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }


    public static String readDate () throws Exception {
        return readInputJSON(DATE);
    }

    public static EO createDate() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readDate());
        assertDate(eo);
        return eo;
    }

    public static EO compareDate() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(DATE, MapProviderEO.createDate());
        assertDate(eo);
        return eo;
    }
    public static void assertDate(final EO eo) throws Exception{
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(F_TEST_DATE));
    }


    public static String readBoolean() throws Exception {
        return readInputJSON(BOOLEAN);
    }

    public static EO createBoolean() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO compareBoolean() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(BOOLEAN, MapProviderEO.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }


    public static String readMap() throws Exception {
        return readInputJSON(MAP);
    }

    public static EO createMap() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readMap());
        assertMap(eo);
        return eo;
    }

    public static EO compareMap() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(MAP, MapProviderEO.createMap());
        assertMap(eo);
        return eo;
    }
    public static void assertMap(final EO eo) throws Exception{
        Assert.assertEquals(S_STRING, eo.get(F_UNTYPED_MAP + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_UNTYPED_MAP + Path.DELIMITER + F_TEST_INTEGER));
    }


    public static String readList() throws Exception {
        return readInputJSON(LIST);
    }

    public static EO createList() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readList());
        assertList(eo);
        return eo;
    }

    public static EO compareList() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(LIST, MapProviderEO.createList());
        assertList(eo);
        return eo;
    }
    public static void assertList(final EO eo)throws Exception {
        Assert.assertEquals(S_STRING, eo.get(F_UNTYPED_LIST + Path.DELIMITER + S0));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_UNTYPED_LIST + Path.DELIMITER + S1));
    }

    public static String readBT() throws Exception {
        return readInputJSON(BASIC_TEST);
    }

    public static EO createBT() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readBT());
        assertBT(eo);
        return eo;
    }

    public static EO compareBT() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(BASIC_TEST, MapProviderEO.createBT());
        assertBT(eo);
        return eo;
    }
    public static void assertBT(final EO eo) throws Exception{
        Assert.assertEquals(S_STRING, eo.get(F_BASIC_TEST + Path.DELIMITER + F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_BASIC_TEST + Path.DELIMITER + F_TEST_INTEGER));
    }

    public static String readST() throws Exception {
        return readInputJSON(SUB_TEST);
    }

    public static EO createST() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readST());
        BTProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(SUB_TEST, MapProviderEO.createST());
        BTProviderEO.assertST(eo);
        return eo;
    }


    public static String readMapST() throws Exception {
        return readInputJSON(SUB_TEST_MAP);
    }

    public static EO createMapST() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }

    public static EO compareMapST() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(SUB_TEST_MAP, MapProviderEO.createMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }


    public static String readListST() throws Exception {
        return readInputJSON(SUB_TEST_LIST);
    }

    public static EO createListST() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static EO compareListST() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(SUB_TEST_LIST, MapProviderEO.createListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static String readSmall() throws Exception {
        return JSONInputReader.readInputJSON(SMALL);
    }

    public static EO createSmall() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readSmall());
        assertSmall(eo);
        return eo;
    }

    public static EO compareSmall() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(SMALL, MapProviderEO.createSmall());
        assertSmall(eo);
        return eo;
    }
    public static void assertSmall(final EO eo) throws Exception{
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_INTEGER));
    }


    public static String readSimple() throws Exception {
        return JSONInputReader.readInputJSON(SIMPLE);
    }

    public static EO createSimple() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readSimple());
        assertSimple(eo);
        return eo;
    }

    public static EO compareSimple() throws Exception {
        EO eo = JSONInputReader.compareInputJSON(SIMPLE, MapProviderEO.createSimple());
        assertSimple(eo);
        return eo;
    }
    public static void assertSimple(final EO eo) throws Exception{
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_INTEGER));
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(F_TEST_FLOAT));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(F_TEST_DATE));
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }


    public static String readAll() throws Exception {
        return read();
    }

    public static String read() throws Exception {
        return JSONInputReader.readInputJSON(ALL);
    }

    public static EO create() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(read());
        asserts(eo);
        return eo;
    }

    public static EO compare() throws Exception {
        final EO eo = JSONInputReader.compareInputJSON(ALL, MapProviderEO.create());
        asserts(eo);
        return eo;
    }
    public static void asserts(final EO eo) throws Exception{
        Assert.assertEquals(S_STRING, eo.get(F_TEST_STRING));
        Assert.assertEquals(new Long(S_INTEGER), eo.get(F_TEST_INTEGER));
        Assert.assertEquals(SAMPLE_LONG, eo.get(F_TEST_LONG));
        Assert.assertEquals(new Double(SAMPLE_FLOAT.toString()), eo.get(F_TEST_FLOAT));
        Assert.assertEquals(SAMPLE_DOUBLE, eo.get(F_TEST_DOUBLE));
        Assert.assertEquals(SAMPLE_DATE.getTime(), eo.get(F_TEST_DATE));
        Assert.assertEquals(S_BOOLEAN, eo.get(F_TEST_BOOLEAN));
    }


    public static String getJSONSmallWithKeys () throws Exception {
        String eo = JSONInputReader.readInputJSON(SMALL_WITH_KEYS);
        return eo;
    }

    public static String getJSONSmallWithKeysAndList () throws Exception {
        String eo = JSONInputReader.readInputJSON(SMALL_WITH_KEYS_AND_LIST);
        return eo;
    }

    public static final String toJSONMap(Object... keyValues) {
        StringBuilder builder = new StringBuilder("{");
        if (keyValues == null || keyValues.length == 0) {
            return builder.append("}").toString();
        }
        for (int i=0; i<keyValues.length;i++) {
            builder.append("\"");
            builder.append(keyValues[i]);
            builder.append("\":");
            i++;
            if (i==keyValues.length) {
                builder.append("null");
                continue;
            }
            Object object = keyValues[i];
            if (object instanceof String) {
                String value = (String)keyValues[i];
                if (value.startsWith("[")||value.startsWith("{")) {
                    builder.append(keyValues[i]);
                }
                else {
                    builder.append("\"");
                    builder.append(keyValues[i]);
                    builder.append("\"");
                }
            }
            else if (object instanceof List) {
                builder.append("[]");
            }
            else if (object instanceof Map) {
                builder.append("{}");
            }
            else {
                builder.append(keyValues[i]);
            }
            if (i!=keyValues.length-1) {
                builder.append(",");
            }
        }
        return builder.append("}").toString();
    }
}
