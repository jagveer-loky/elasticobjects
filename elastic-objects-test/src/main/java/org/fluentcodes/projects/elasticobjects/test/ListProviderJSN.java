package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;


import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

public class ListProviderJSN {
    
    public static String readEmpty()  {
        return JSONInputReader.readInputJSN(TYPE.LIST, EMPTY);
    }

    public static EO createEmpty()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readEmpty());
        ListProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO compareEmpty()  {
        EO eoEmpty = JSONInputReader.compareInputJSN(TYPE.LIST, EMPTY, ListProviderEO.createEmpty());
        ListProviderEO.assertEmpty(eoEmpty);
        return eoEmpty;
    }


    public static String readString()  {
        return JSONInputReader.readInputJSN(TYPE.LIST, STRING);
    }

    public static EO createString()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readString());
        ListProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString()  {
        final EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, STRING, ListProviderEO.createString());
        ListProviderEO.assertString(eo);
        return eo;
    }


    public static String readInteger()  {
        return JSONInputReader.readInputJSN(TYPE.LIST, INT);
    }

    public static EO createInteger()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readInteger());
        ListProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO compareInteger()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, INT, ListProviderEO.createInteger());
        ListProviderEO.assertInteger(eo);
        return eo;
    }


    public static String readLong()  {
        return JSONInputReader.readInputJSN(TYPE.LIST, LONG);
    }

    public static EO createLong()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readLong());
        ListProviderEO.assertLong(eo);
        return eo;
    }

    public static EO compareLong()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, LONG, ListProviderEO.createLong());
        ListProviderEO.assertLong(eo);
        return eo;
    }


    public static String readFloat()  {
        return JSONInputReader.readInputJSN(TYPE.LIST, FLOAT);
    }

    public static EO createFloat()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readFloat());
        ListProviderEO.assertFloat(eo);
        return eo;
    }

    public static EO compareFloat()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, FLOAT, ListProviderEO.createFloat());
        ListProviderEO.assertFloat(eo);
        return eo;
    }


    public static String readDouble()  {
        return JSONInputReader.readInputJSN(TYPE.LIST, DOUBLE);
    }

    public static EO createDouble()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readDouble());
        ListProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO compareDouble()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, DOUBLE, ListProviderEO.createDouble());
        ListProviderEO.assertDouble(eo);
        return eo;
    }


    public static String readDate()  {
        return JSONInputReader.readInputJSN(TYPE.LIST, DATE);
    }

    public static EO createDate()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readDate());
        ListProviderEO.assertDate(eo);
        return eo;
    }

    public static EO compareDate()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, DATE, ListProviderEO.createDate());
        ListProviderEO.assertDate(eo);
        return eo;
    }


    public static String readBoolean()  {
        return JSONInputReader.readInputJSN(TYPE.LIST, BOOLEAN);
    }

    public static EO createBoolean()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readBoolean());
        ListProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO compareBoolean()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, BOOLEAN, ListProviderEO.createBoolean());
        ListProviderEO.assertBoolean(eo);
        return eo;
    }

    public static String readMap()  {
        return readInputJSN(TYPE.LIST, MAP);
    }

    public static EO createMap()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readMap());
        ListProviderEO.assertMap(eo);
        return eo;
    }

    public static EO compareMap()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, MAP, ListProviderEO.createMap());
        ListProviderEO.assertMap(eo);
        return eo;
    }

    public static String readList()  {
        return readInputJSN(TYPE.LIST, LIST);
    }

    public static EO createList()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readList());
        ListProviderEO.assertList(eo);
        return eo;
    }

    public static EO compareList()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, LIST, ListProviderEO.createList());
        ListProviderEO.assertList(eo);
        return eo;
    }

    public static String readST()  {
        return readInputJSN(TYPE.LIST, SUB_TEST);
    }

    public static EO createST()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readST());
        ListProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, SUB_TEST, ListProviderEO.createST());
        ListProviderEO.assertST(eo);
        return eo;
    }

    public static String readBT()  {
        return readInputJSN(TYPE.LIST, BASIC_TEST);
    }

    public static EO createBT()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readBT());
        ListProviderEO.assertBT(eo);
        return eo;
    }

    public static EO compareBT()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, BASIC_TEST, ListProviderEO.createBT());
        ListProviderEO.assertBT(eo);
        return eo;
    }


    public static String readSmall()  {
        return readInputJSN(TYPE.LIST, SMALL);
    }

    public static String getSmall()  {
        return JSONInputReader.readInputJSN(TYPE.LIST, SMALL);
    }

    public static EO createSmall()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readSmall());
        ListProviderEO.assertSmall(eo);
        return eo;
    }

    public static EO compareSmall()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, SMALL, ListProviderEO.createSmall());
        ListProviderEO.assertSmall(eo);
        return eo;
    }


    public static String readSimple()  {
        return readInputJSN(TYPE.LIST, SIMPLE);
    }

    public static EO createSimple()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readSimple());
        ListProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO compareSimple()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, SIMPLE, ListProviderEO.createSimple());
        ListProviderEO.assertSimple(eo);
        return eo;
    }

    public static String readAll()  {
        return readInputJSN(TYPE.LIST, ALL);
    }

    public static EO create()  {
        final EO eo = TestEOProvider.create(); eo.mapObject(readAll());
        ListProviderEO.asserts(eo);
        return eo;
    }

    public static EO compare()  {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, ALL, ListProviderEO.create());
        ListProviderEO.asserts(eo);
        return eo;
    }
}
