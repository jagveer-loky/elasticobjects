package org.fluentcodes.projects.elasticobjects.test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_CONTENT;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class MapProvider {

    public static Map createList () {
        Map map = new LinkedHashMap();
        map.put(F_UNTYPED_LIST, ListProvider.createSmall());
        return map;
    }

    public static Map createBT() {
        Map map = new LinkedHashMap();
        map.put(F_BASIC_TEST, BTProvider.createSimple());
        return map;
    }

    public static Map createST() {
        Map map = new LinkedHashMap();
        map.put(F_SUB_TEST, STProvider.createSimple());
        return map;
    }

    public static Map createMapST() {
        Map map = new LinkedHashMap();
        map.put(F_SUB_TEST_MAP, STProvider.createSubTestMap());
        return map;
    }

    public static Map createListST() {
        Map map = new LinkedHashMap();
        map.put(F_SUB_TEST_LIST, STProvider.createSubTestList());
        return map;
    }

    public static void addSimple(Map map) {
        addMapWithString(map);
        addMapWithInteger(map);
        addMapWithLong(map);
        addMapWithFloat(map);
        addMapWithDouble(map);
        addMapWithDate(map);
        addMapWithBoolean(map);
    }

    public static Map createSimple() {
        Map map = new LinkedHashMap();
        addSimple(map);
        return map;
    }

    public static Map createString() {
        Map map = new LinkedHashMap();
        addMapWithString(map);
        return map;
    }

    static void addMapWithString(Map map) {
        map.put(F_TEST_STRING, S_STRING);
    }

    public static Map createContent(final Object content) {
        Map map = new LinkedHashMap();
        map.put(F_CONTENT, content);
        return map;
    }

    static void addMapWithInteger(Map map) {
        map.put(F_TEST_INTEGER, S_INTEGER);
    }

    public static Map createInteger() {
        Map map = new LinkedHashMap();
        addMapWithInteger(map);
        return map;
    }

    static void addMapWithLong(Map map) {
        map.put(F_TEST_LONG, SAMPLE_LONG);
    }

    public static Map createLong() {
        Map map = new LinkedHashMap();
        addMapWithLong(map);
        return map;
    }

    static void addMapWithFloat(Map map) {
        map.put(F_TEST_FLOAT, SAMPLE_FLOAT);
    }

    public static Map createFloat() {
        Map map = new LinkedHashMap();
        addMapWithFloat(map);
        return map;
    }

    static void addMapWithDouble(Map map) {
        map.put(F_TEST_DOUBLE, SAMPLE_DOUBLE);
    }

    public static Map createDouble() {
        Map map = new LinkedHashMap();
        addMapWithDouble(map);
        return map;
    }

    static void addMapWithDate(Map map) {
        map.put(F_TEST_DATE, SAMPLE_DATE);
    }

    public static Map createDate() {
        Map map = new LinkedHashMap();
        addMapWithDate(map);
        return map;
    }

    static void addMapWithBoolean(Map map) {
        map.put(F_TEST_BOOLEAN, S_BOOLEAN);
    }

    public static Map createBoolean() {
        Map map = new LinkedHashMap();
        addMapWithBoolean(map);
        return map;
    }

    public static Map createMap () {
        Map map = new LinkedHashMap();
        map.put(F_UNTYPED_MAP, createSmall());
        return map;
    }

    public static Map createSmall() {
        Map map = new LinkedHashMap();
        addMapWithString(map);
        addMapWithInteger(map);
        return map;
    }

    public static Map create() {
        Map map = new LinkedHashMap();
        addSimple(map);
        map.put(F_SUB_TEST, STProvider.createSimple());
        map.put(F_BASIC_TEST, BTProvider.createSimple());
        map.put(F_UNTYPED_LIST, ListProvider.createSmall());
        map.put(F_UNTYPED_MAP, createSmall());
        map.put(F_SUB_TEST_MAP, STProvider.createSubTestMap());
        return map;
    }

    public static Map createBig(int length) throws Exception {
        final Map bigMap = new HashMap();
        for (int i = 0;i<length;i++) {
            bigMap.put("key" + i, i);
        }
        return bigMap;
    }

    public static Map createEmpty() {
        return new LinkedHashMap();
    }

    public static Map createStringOther() {
        Map map = new LinkedHashMap();
        map.put(F_TEST_STRING, S_STRING_OTHER);
        return map;
    }

    public static final void addMap(Map map, Object... keyValues) throws Exception{
        for (int i=0; i<keyValues.length; i++) {
            if (i == keyValues.length-1) {
                throw new Exception("Uneven key value pairs at " +i );
            }
            map.put(keyValues[i], keyValues[i+1]);
            i++;
        }
    }

    public static final Map toMap(Object... keyValues) throws Exception{
        Map map = new LinkedHashMap();
        addMap(map, keyValues);
        return map;
    }
}
