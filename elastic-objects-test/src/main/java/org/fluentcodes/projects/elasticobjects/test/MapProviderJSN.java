package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.eo.EOBuilder;
import org.junit.Assert;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.test.JSONInputReader.*;

public class MapProviderJSN {
    public static EOBuilder builder() {
        return TestEOProvider.createEOBuilder();
    }

    public static String readEmpty()  {
        return JSONInputReader.readInputJSN(EMPTY);
    }

    public static String getJSONEmpty()  {
        String eo = JSONInputReader.readInputJSN(EMPTY);
        return eo;
    }

    public static EO createEmpty()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readEmpty());
        BTProviderEO.assertEmpty(eo);
        return eo;
    }

    public static EO compareEmpty()  {
        EO eo = JSONInputReader.compareInputJSN(EMPTY, MapProviderEO.createEmpty());
        Assert.assertEquals(Map.class, eo.getModelClass());
        return eo;
    }


    public static String readString()  {
        return readInputJSN(STRING);
    }

    public static EO createString()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString()  {
        EO eo = JSONInputReader.compareInputJSN(STRING, MapProviderEO.createString());
        BTProviderEO.assertString(eo);
        return eo;
    }

    public static String readInteger()  {
        return readInputJSN(INT);
    }

    public static EO createInteger()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO compareInteger()  {
        EO eo = JSONInputReader.compareInputJSN(INT, MapProviderEO.createInteger());
        BTProviderEO.assertInteger(eo);
        return eo;
    }


    public static String readLong()  {
        return readInputJSN(LONG);
    }

    public static EO createLong()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static EO compareLong()  {
        EO eo = JSONInputReader.compareInputJSN(LONG, MapProviderEO.createLong());
        BTProviderEO.assertLong(eo);
        return eo;
    }

    public static String readFloat()  {
        return readInputJSN(FLOAT);
    }

    public static EO createFloat()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }

    public static EO compareFloat()  {
        EO eo = JSONInputReader.compareInputJSN(FLOAT, MapProviderEO.createFloat());
        BTProviderEO.assertFloat(eo);
        return eo;
    }


    public static String readDouble()  {
        return readInputJSN(DOUBLE);
    }

    public static EO createDouble()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO compareDouble()  {
        EO eo = JSONInputReader.compareInputJSN(DOUBLE, MapProviderEO.createDouble());
        BTProviderEO.assertDouble(eo);
        return eo;
    }


    public static String readDate()  {
        return readInputJSN(DATE);
    }

    public static EO createDate()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }

    public static EO compareDate()  {
        EO eo = JSONInputReader.compareInputJSN(DATE, MapProviderEO.createDate());
        BTProviderEO.assertDate(eo);
        return eo;
    }


    public static String readBoolean()  {
        return readInputJSN(BOOLEAN);
    }

    public static EO createBoolean()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO compareBoolean()  {
        EO eo = JSONInputReader.compareInputJSN(BOOLEAN, MapProviderEO.createBoolean());
        BTProviderEO.assertBoolean(eo);
        return eo;
    }


    public static String readMap()  {
        return readInputJSN(MAP);
    }

    public static EO createMap()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }

    public static EO compareMap()  {
        EO eo = JSONInputReader.compareInputJSN(MAP, MapProviderEO.createMap());
        BTProviderEO.assertMap(eo);
        return eo;
    }


    public static String readList()  {
        return readInputJSN(LIST);
    }

    public static EO createList()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readList());
        BTProviderEO.assertList(eo);
        return eo;
    }

    public static EO compareList()  {
        EO eo = JSONInputReader.compareInputJSN(LIST, MapProviderEO.createList());
        BTProviderEO.assertList(eo);
        return eo;
    }


    public static String readBT()  {
        return readInputJSN(BASIC_TEST);
    }

    public static EO createBT()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }

    public static EO compareBT()  {
        EO eo = JSONInputReader.compareInputJSN(BASIC_TEST, MapProviderEO.createBT());
        BTProviderEO.assertBT(eo);
        return eo;
    }


    public static String readST()  {
        return readInputJSN(SUB_TEST);
    }

    public static EO createST()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readST());
        BTProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST()  {
        EO eo = JSONInputReader.compareInputJSN(SUB_TEST, MapProviderEO.createST());
        BTProviderEO.assertST(eo);
        return eo;
    }


    public static String readMapST()  {
        return readInputJSN(SUB_TEST_MAP);
    }

    public static EO createMapST()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }

    public static EO compareMapST()  {
        EO eo = JSONInputReader.compareInputJSN(SUB_TEST_MAP, MapProviderEO.createMapST());
        BTProviderEO.assertMapST(eo);
        return eo;
    }


    public static String readListST()  {
        return readInputJSN(SUB_TEST_LIST);
    }

    public static EO createListST()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static EO compareListST()  {
        EO eo = JSONInputReader.compareInputJSN(SUB_TEST_LIST, MapProviderEO.createListST());
        BTProviderEO.assertListST(eo);
        return eo;
    }

    public static String readSmall()  {
        return JSONInputReader.readInputJSN(SMALL);
    }

    public static EO createSmall()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static EO compareSmall()  {
        EO eo = JSONInputReader.compareInputJSN(SMALL, MapProviderEO.createSmall());
        BTProviderEO.assertSmall(eo);
        return eo;
    }

    public static String readSimple()  {
        return JSONInputReader.readInputJSN(SIMPLE);
    }

    public static EO createSimple()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(readSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO compareSimple()  {
        EO eo = JSONInputReader.compareInputJSN(SIMPLE, MapProviderEO.createSimple());
        BTProviderEO.assertSimple(eo);
        return eo;
    }

    public static String readAll()  {
        return read();
    }

    public static String read()  {
        return JSONInputReader.readInputJSN(ALL);
    }

    public static EO create()  {
        final EO eo = builder()
                .setModels(Map.class)
                .map(read());
        BTProviderEO.asserts(eo);
        return eo;
    }

    public static EO compare()  {
        final EO eo = JSONInputReader.compareInputJSN(ALL, MapProviderEO.create());
        BTProviderEO.asserts(eo);
        return eo;
    }
}
