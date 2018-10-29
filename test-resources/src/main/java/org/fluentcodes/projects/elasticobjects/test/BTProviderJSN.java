package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.junit.Assert;

import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

public class BTProviderJSN {
    public static EOBuilder builder() {
        return TestObjectProvider.createEOBuilder();
    }

    public static String readEmpty() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.BT, EMPTY);
    }

    public static String getJSONEmpty() throws Exception {
        String eo = JSONInputReader.readInputJSN(TYPE.BT, EMPTY);
        return eo;
    }

    public static EO createEmpty() throws Exception {
        final EO eo = builder()
                .map(readEmpty());
        BTProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO compareEmpty() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, EMPTY, BTProviderEO.createEmpty());
        Assert.assertEquals(BasicTest.class, eo.getModelClass());
        return eo;
    }


    public static String readString() throws Exception {
        return readInputJSN(TYPE.BT, STRING);
    }

    public static EO createString() throws Exception {
        final EO eo = builder()
                .map(readString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, STRING, BTProviderEO.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static String readInteger() throws Exception {
        return readInputJSN(TYPE.BT, INT);
    }

    public static EO createInteger() throws Exception {
        final EO eo = builder()
                .map(readInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO compareInteger() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, INT, BTProviderEO.createInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }


    public static String readLong() throws Exception {
        return readInputJSN(TYPE.BT, LONG);
    }

    public static EO createLong() throws Exception {
        final EO eo = builder()
                .map(readLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO compareLong() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, LONG, BTProviderEO.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static String readFloat() throws Exception {
        return readInputJSN(TYPE.BT, FLOAT);
    }

    public static EO createFloat() throws Exception {
        final EO eo = builder()
                .map(readFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }

    public static EO compareFloat() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, FLOAT, BTProviderEO.createFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }


    public static String readDouble() throws Exception {
        return readInputJSN(TYPE.BT, DOUBLE);
    }

    public static EO createDouble() throws Exception {
        final EO eo = builder()
                .map(readDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO compareDouble() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, DOUBLE, BTProviderEO.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }


    public static String readDate() throws Exception {
        return readInputJSN(TYPE.BT, DATE);
    }

    public static EO createDate() throws Exception {
        final EO eo = builder()
                .map(readDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }

    public static EO compareDate() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, DATE, BTProviderEO.createDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }


    public static String readBoolean() throws Exception {
        return readInputJSN(TYPE.BT, BOOLEAN);
    }

    public static EO createBoolean() throws Exception {
        final EO eo = builder()
                .map(readBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO compareBoolean() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, BOOLEAN, BTProviderEO.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }


    public static String readMap() throws Exception {
        return readInputJSN(TYPE.BT, MAP);
    }

    public static EO createMap() throws Exception {
        final EO eo = builder()
                .map(readMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }

    public static EO compareMap() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, MAP, BTProviderEO.createMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }


    public static String readList() throws Exception {
        return readInputJSN(TYPE.BT, LIST);
    }

    public static EO createList() throws Exception {
        final EO eo = builder()
                .map(readList());
        BTProviderEO.assertList(eo);
        return eo;
    }

    public static EO compareList() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, LIST, BTProviderEO.createList());
        BTProviderEO.assertList(eo);
        return eo;
    }


    public static String readBT() throws Exception {
        return readInputJSN(TYPE.BT, BASIC_TEST);
    }

    public static EO createBT() throws Exception {
        final EO eo = builder()
                .map(readBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }

    public static EO compareBT() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, BASIC_TEST, BTProviderEO.createBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }


    public static String readST() throws Exception {
        return readInputJSN(TYPE.BT, SUB_TEST);
    }

    public static EO createST() throws Exception {
        final EO eo = builder()
                .map(readST());
        BTProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SUB_TEST, BTProviderEO.createST());
        BTProviderEO.assertST(eo);
        return eo;
    }


    public static String readMapST() throws Exception {
        return readInputJSN(TYPE.BT, SUB_TEST_MAP);
    }

    public static EO createMapST() throws Exception {
        final EO eo = builder()
                .map(readMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }

    public static EO compareMapST() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SUB_TEST_MAP, BTProviderEO.createMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }


    public static String readListST() throws Exception {
        return readInputJSN(TYPE.BT, SUB_TEST_LIST);
    }

    public static EO createListST() throws Exception {
        final EO eo = builder()
                .map(readListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static EO compareListST() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SUB_TEST_LIST, BTProviderEO.createListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static String readSmall() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.BT, SMALL);
    }

    public static EO createSmall() throws Exception {
        final EO eo = builder()
                .map(readSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static EO compareSmall() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SMALL, BTProviderEO.createSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static String readSimple() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.BT, SIMPLE);
    }

    public static EO createSimple() throws Exception {
        final EO eo = builder()
                .map(readSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO compareSimple() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SIMPLE, BTProviderEO.createSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static String readAll() throws Exception {
        return read();
    }

    public static String read() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.BT, ALL);
    }

    public static EO create() throws Exception {
        final EO eo = builder()
                .map(read());
        BTProviderEO.asserts(eo);
        return eo;
    }

    public static EO compare() throws Exception {
        final EO eo = JSONInputReader.compareInputJSN(TYPE.BT, ALL, BTProviderEO.create());
        BTProviderEO.asserts(eo);
        return eo;
    }
}