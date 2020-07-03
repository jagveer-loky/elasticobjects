package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.junit.Assert;

import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

public class BTProviderJSN {
    public static EOBuilder builder() {
        return TestEOProvider.createEOBuilder();
    }

    public static String readEmpty()  {
        return JSONInputReader.readInputJSN(TYPE.BT, EMPTY);
    }

    public static String getJSONEmpty()  {
        String eo = JSONInputReader.readInputJSN(TYPE.BT, EMPTY);
        return eo;
    }

    public static EO createEmpty()  {
        final EO eo = builder()
                .map(readEmpty());
        BTProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO compareEmpty()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, EMPTY, BTProviderEO.createEmpty());
        Assert.assertEquals(BasicTest.class, eo.getModelClass());
        return eo;
    }


    public static String readString()  {
        return readInputJSN(TYPE.BT, STRING);
    }

    public static EO createString()  {
        final EO eo = builder()
                .map(readString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, STRING, BTProviderEO.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static String readInteger()  {
        return readInputJSN(TYPE.BT, INT);
    }

    public static EO createInteger()  {
        final EO eo = builder()
                .map(readInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO compareInteger()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, INT, BTProviderEO.createInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }


    public static String readLong()  {
        return readInputJSN(TYPE.BT, LONG);
    }

    public static EO createLong()  {
        final EO eo = builder()
                .map(readLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO compareLong()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, LONG, BTProviderEO.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static String readFloat()  {
        return readInputJSN(TYPE.BT, FLOAT);
    }

    public static EO createFloat()  {
        final EO eo = builder()
                .map(readFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }

    public static EO compareFloat()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, FLOAT, BTProviderEO.createFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }


    public static String readDouble()  {
        return readInputJSN(TYPE.BT, DOUBLE);
    }

    public static EO createDouble()  {
        final EO eo = builder()
                .map(readDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO compareDouble()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, DOUBLE, BTProviderEO.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }


    public static String readDate()  {
        return readInputJSN(TYPE.BT, DATE);
    }

    public static EO createDate()  {
        final EO eo = builder()
                .map(readDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }

    public static EO compareDate()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, DATE, BTProviderEO.createDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }


    public static String readBoolean()  {
        return readInputJSN(TYPE.BT, BOOLEAN);
    }

    public static EO createBoolean()  {
        final EO eo = builder()
                .map(readBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO compareBoolean()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, BOOLEAN, BTProviderEO.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }


    public static String readMap()  {
        return readInputJSN(TYPE.BT, MAP);
    }

    public static EO createMap()  {
        final EO eo = builder()
                .map(readMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }

    public static EO compareMap()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, MAP, BTProviderEO.createMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }


    public static String readList()  {
        return readInputJSN(TYPE.BT, LIST);
    }

    public static EO createList()  {
        final EO eo = builder()
                .map(readList());
        BTProviderEO.assertList(eo);
        return eo;
    }

    public static EO compareList()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, LIST, BTProviderEO.createList());
        BTProviderEO.assertList(eo);
        return eo;
    }


    public static String readBT()  {
        return readInputJSN(TYPE.BT, BASIC_TEST);
    }

    public static EO createBT()  {
        final EO eo = builder()
                .map(readBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }

    public static EO compareBT()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, BASIC_TEST, BTProviderEO.createBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }


    public static String readST()  {
        return readInputJSN(TYPE.BT, SUB_TEST);
    }

    public static EO createST()  {
        final EO eo = builder()
                .map(readST());
        BTProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SUB_TEST, BTProviderEO.createST());
        BTProviderEO.assertST(eo);
        return eo;
    }


    public static String readMapST()  {
        return readInputJSN(TYPE.BT, SUB_TEST_MAP);
    }

    public static EO createMapST()  {
        final EO eo = builder()
                .map(readMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }

    public static EO compareMapST()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SUB_TEST_MAP, BTProviderEO.createMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }


    public static String readListST()  {
        return readInputJSN(TYPE.BT, SUB_TEST_LIST);
    }

    public static EO createListST()  {
        final EO eo = builder()
                .map(readListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static EO compareListST()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SUB_TEST_LIST, BTProviderEO.createListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static String readSmall()  {
        return JSONInputReader.readInputJSN(TYPE.BT, SMALL);
    }

    public static EO createSmall()  {
        final EO eo = builder()
                .map(readSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static EO compareSmall()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SMALL, BTProviderEO.createSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static String readSimple()  {
        return JSONInputReader.readInputJSN(TYPE.BT, SIMPLE);
    }

    public static EO createSimple()  {
        final EO eo = builder()
                .map(readSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO compareSimple()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.BT, SIMPLE, BTProviderEO.createSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static String readAll()  {
        return read();
    }

    public static String read()  {
        return JSONInputReader.readInputJSN(TYPE.BT, ALL);
    }

    public static EO create()  {
        final EO eo = builder()
                .map(read());
        BTProviderEO.asserts(eo);
        return eo;
    }

    public static EO compare()  {
        final EO eo = JSONInputReader.compareInputJSN(TYPE.BT, ALL, BTProviderEO.create());
        BTProviderEO.asserts(eo);
        return eo;
    }
}