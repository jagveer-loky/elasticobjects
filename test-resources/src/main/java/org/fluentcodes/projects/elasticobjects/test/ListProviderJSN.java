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

public class ListProviderJSN {
    public static EOBuilder builder() {
        return TestObjectProvider.createEOBuilder();
    }

    public static String readEmpty() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.LIST, EMPTY);
    }

    public static EO createEmpty() throws Exception {
        final EO eo = builder().map(readEmpty());
        ListProviderEO.assertEmpty(eo);
        return  eo;
    }

    public static EO compareEmpty() throws Exception {
        EO eoEmpty = JSONInputReader.compareInputJSN(TYPE.LIST, EMPTY, ListProviderEO.createEmpty());
        ListProviderEO.assertEmpty(eoEmpty);
        return eoEmpty;
    }


    public static String readString() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.LIST, STRING);
    }

    public static EO createString() throws Exception {
        final EO eo = builder().map(readString());
        ListProviderEO.assertString(eo);
        return eo;
    }

    public static EO compareString() throws Exception {
        final EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, STRING, ListProviderEO.createString());
        ListProviderEO.assertString(eo);
        return eo;
    }


    public static String readInteger() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.LIST, INT);
    }

    public static EO createInteger() throws Exception {
        final EO eo = builder().map(readInteger());
        ListProviderEO.assertInteger(eo);
        return eo;
    }

    public static EO compareInteger() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, INT, ListProviderEO.createInteger());
        ListProviderEO.assertInteger(eo);
        return eo;
    }


    public static String readLong() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.LIST, LONG);
    }

    public static EO createLong() throws Exception {
        final EO eo= builder().map(readLong());
        ListProviderEO.assertLong(eo);
        return eo;
    }

    public static EO compareLong() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, LONG, ListProviderEO.createLong());
        ListProviderEO.assertLong(eo);
        return eo;
    }


    public static String readFloat() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.LIST, FLOAT);
    }

    public static EO createFloat() throws Exception {
        final EO eo = builder().map(readFloat());
        ListProviderEO.assertFloat(eo);
        return eo;
    }

    public static EO compareFloat() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, FLOAT, ListProviderEO.createFloat());
        ListProviderEO.assertFloat(eo);
        return eo;
    }


    public static String readDouble() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.LIST, DOUBLE);
    }

    public static EO createDouble() throws Exception {
        final EO eo = builder().map(readDouble());
        ListProviderEO.assertDouble(eo);
        return eo;
    }

    public static EO compareDouble() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, DOUBLE, ListProviderEO.createDouble());
        ListProviderEO.assertDouble(eo);
        return eo;
    }


    public static String readDate() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.LIST, DATE);
    }

    public static EO createDate() throws Exception {
        final EO eo = builder().map(readDate());
        ListProviderEO.assertDate(eo);
        return eo;
    }

    public static EO compareDate() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, DATE, ListProviderEO.createDate());
        ListProviderEO.assertDate(eo);
        return eo;
    }


    public static String readBoolean() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.LIST, BOOLEAN);
    }

    public static EO createBoolean() throws Exception {
        final EO eo = builder().map(readBoolean());
        ListProviderEO.assertBoolean(eo);
        return eo;
    }

    public static EO compareBoolean() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, BOOLEAN, ListProviderEO.createBoolean());
        ListProviderEO.assertBoolean(eo);
        return eo;
    }

    public static String readMap () throws Exception {
        return readInputJSN(TYPE.LIST, MAP);
    }

    public static EO createMap() throws Exception {
        final EO eo = builder().map(readMap());
        ListProviderEO.assertMap(eo);
        return eo;
    }

    public static EO compareMap() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, MAP, ListProviderEO.createMap());
        ListProviderEO.assertMap(eo);
        return eo;
    }

    public static String readList () throws Exception {
        return readInputJSN(TYPE.LIST, LIST);
    }

    public static EO createList() throws Exception {
        final EO eo = builder().map(readList());
        ListProviderEO.assertList(eo);
        return eo;
    }

    public static EO compareList() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, LIST, ListProviderEO.createList());
        ListProviderEO.assertList(eo);
        return eo;
    }

    public static String readST () throws Exception {
        return readInputJSN(TYPE.LIST, SUB_TEST);
    }

    public static EO createST() throws Exception {
        final EO eo = builder().map(readST());
        ListProviderEO.assertST(eo);
        return eo;
    }

    public static EO compareST() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, SUB_TEST, ListProviderEO.createST());
        ListProviderEO.assertST(eo);
        return eo;
    }

    public static String readBT () throws Exception {
        return readInputJSN(TYPE.LIST, BASIC_TEST);
    }

    public static EO createBT() throws Exception {
        final EO eo = builder().map(readBT());
        ListProviderEO.assertBT(eo);
        return eo;
    }

    public static EO compareBT() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, BASIC_TEST, ListProviderEO.createBT());
        ListProviderEO.assertBT(eo);
        return eo;
    }


    public static String readSmall () throws Exception {
        return readInputJSN(TYPE.LIST, SMALL);
    }

    public static String getSmall() throws Exception {
        return JSONInputReader.readInputJSN(TYPE.LIST, SMALL);
    }

    public static EO createSmall() throws Exception {
        final EO eo = builder().map(readSmall());
        ListProviderEO.assertSmall(eo);
        return eo;
    }

    public static EO compareSmall() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, SMALL, ListProviderEO.createSmall());
        ListProviderEO.assertSmall(eo);
        return eo;
    }


    public static String readSimple () throws Exception {
        return readInputJSN(TYPE.LIST, SIMPLE);
    }

    public static EO createSimple() throws Exception {
        final EO eo = builder().map(readSimple());
        ListProviderEO.assertSimple(eo);
        return eo;
    }

    public static EO compareSimple() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, SIMPLE, ListProviderEO.createSimple());
        ListProviderEO.assertSimple(eo);
        return eo;
    }

    public static String readAll () throws Exception {
        return readInputJSN(TYPE.LIST, ALL);
    }

    public static EO create() throws Exception {
        final EO eo = builder().map(readAll());
        ListProviderEO.asserts(eo);
        return eo;
    }

    public static EO compare() throws Exception {
        EO eo = JSONInputReader.compareInputJSN(TYPE.LIST, ALL, ListProviderEO.create());
        ListProviderEO.asserts(eo);
        return eo;
    }
}
