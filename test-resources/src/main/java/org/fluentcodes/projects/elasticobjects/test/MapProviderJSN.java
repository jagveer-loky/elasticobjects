package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.junit.Assert;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

public class MapProviderJSN {
    public static EOBuilder builder() {
        return TestObjectProvider.createEOBuilder();
    }
    public static String readEmpty() throws Exception {
        return JSONInputReader.readInputJSN(EMPTY);
    }

    public static String getJSONEmpty () throws Exception {
        String eo = JSONInputReader.readInputJSN(EMPTY);
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
        EO eo = JSONInputReader.compareInputJSN(EMPTY, MapProviderEO.createEmpty());
        Assert.assertEquals(Map.class, eo.getModelClass());
        return eo;
    }
    

    public static String readString () throws Exception {
        return readInputJSN(STRING);
    }

    public static EO createString() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(STRING, MapProviderEO.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static String readInteger () throws Exception {
        return readInputJSN(INT);
    }

    public static EO createInteger() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO compareInteger() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(INT, MapProviderEO.createInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }


    public static String readLong () throws Exception {
        return readInputJSN(LONG);
    }

    public static EO createLong() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO compareLong() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(LONG, MapProviderEO.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static String readFloat () throws Exception {
        return readInputJSN(FLOAT);
    }

    public static EO createFloat() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }

    public static EO compareFloat() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(FLOAT, MapProviderEO.createFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }


    public static String readDouble () throws Exception {
        return readInputJSN(DOUBLE);
    }

    public static EO createDouble() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO compareDouble() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(DOUBLE, MapProviderEO.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }


    public static String readDate () throws Exception {
        return readInputJSN(DATE);
    }

    public static EO createDate() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }

    public static EO compareDate() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(DATE, MapProviderEO.createDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }


    public static String readBoolean() throws Exception {
        return readInputJSN(BOOLEAN);
    }

    public static EO createBoolean() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO compareBoolean() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(BOOLEAN, MapProviderEO.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }


    public static String readMap() throws Exception {
        return readInputJSN(MAP);
    }

    public static EO createMap() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }

    public static EO compareMap() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(MAP, MapProviderEO.createMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }


    public static String readList() throws Exception {
        return readInputJSN(LIST);
    }

    public static EO createList() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readList());
        BTProviderEO.assertList(eo);
        return eo;
    }

    public static EO compareList() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(LIST, MapProviderEO.createList());
        BTProviderEO.assertList(eo);
        return eo;
    }


    public static String readBT() throws Exception {
        return readInputJSN(BASIC_TEST);
    }

    public static EO createBT() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }

    public static EO compareBT() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(BASIC_TEST, MapProviderEO.createBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }


    public static String readST() throws Exception {
        return readInputJSN(SUB_TEST);
    }

    public static EO createST() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readST());
        BTProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(SUB_TEST, MapProviderEO.createST());
        BTProviderEO.assertST(eo);
        return eo;
    }


    public static String readMapST() throws Exception {
        return readInputJSN(SUB_TEST_MAP);
    }

    public static EO createMapST() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }

    public static EO compareMapST() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(SUB_TEST_MAP, MapProviderEO.createMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }


    public static String readListST() throws Exception {
        return readInputJSN(SUB_TEST_LIST);
    }

    public static EO createListST() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static EO compareListST() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(SUB_TEST_LIST, MapProviderEO.createListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static String readSmall() throws Exception {
        return JSONInputReader.readInputJSN(SMALL);
    }

    public static EO createSmall() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static EO compareSmall() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(SMALL, MapProviderEO.createSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static String readSimple() throws Exception {
        return JSONInputReader.readInputJSN(SIMPLE);
    }

    public static EO createSimple() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO compareSimple() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(SIMPLE, MapProviderEO.createSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static String readAll() throws Exception {
        return read();
    }

    public static String read() throws Exception {
        return JSONInputReader.readInputJSN(ALL);
    }

    public static EO create() throws Exception {
        final EO eo = builder()
                .setModels(Map.class)
                .map(read());
        BTProviderEO.asserts(eo);
        return eo;
    }

    public static EO compare() throws Exception {
        final EO eo = JSONInputReader.compareInputJSN(ALL, MapProviderEO.create());
        BTProviderEO.asserts(eo);
        return eo;
    }
}
