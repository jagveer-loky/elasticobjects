package org.fluentcodes.projects.elasticobjects.test;

import java.util.ArrayList;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class ListProvider {

    public static List createEmpty() {
        return new ArrayList();
    }

    public static List createString() {
        List list = new ArrayList();
        list.add(S_STRING);
        return list;
    }

    public static List createInteger() {
        List list = new ArrayList();
        list.add(S_INTEGER);
        return list;
    }

    public static List createLong() {
        List list = new ArrayList();
        list.add(SAMPLE_LONG);
        return list;
    }

    public static List createFloat() {
        List list = new ArrayList();
        list.add(SAMPLE_FLOAT);
        return list;
    }

    public static List createDouble() {
        List list = new ArrayList();
        list.add(SAMPLE_DOUBLE);
        return list;
    }

    public static List createDate() {
        List list = new ArrayList();
        list.add(SAMPLE_DATE);
        return list;
    }

    public static List createBoolean() {
        List list = new ArrayList();
        list.add(S_BOOLEAN);
        return list;
    }

    public static List createMap () {
        List list = new ArrayList();
        list.add(MapProvider.createSmall());
        return list;
    }

    public static List createList () {
        List list = new ArrayList();
        list.add(createSmall());
        return list;
    }

    public static List createBT() {
        List list = new ArrayList();
        list.add(BTProvider.createSimple());
        return list;
    }

    public static List createST() {
        List list = new ArrayList();
        list.add(STProvider.createSimple());
        return list;
    }

    public static List createSmall() {
        List list = new ArrayList();
        list.add(S_STRING);
        list.add(S_INTEGER);
        return list;
    }

    public static void addSimple(List list) {
        list.add(S_STRING);
        list.add(S_INTEGER);
        list.add(SAMPLE_LONG);
        list.add(SAMPLE_FLOAT);
        list.add(SAMPLE_DOUBLE);
        list.add(SAMPLE_DATE);
        list.add(S_BOOLEAN);
    }

    public static List createSimple() {
        List list = new ArrayList();
        addSimple(list);
        return list;
    }

    public static List create() {
        List list = new ArrayList();
        addSimple(list);
        list.add(createSmall());
        list.add(MapProvider.createSmall());
        list.add(BTProvider.createSimple());
        list.add(STProvider.createSimple());
        return list;
    }

    //</call>
    public static final List toList(Object... keyValues) {
        List list = new ArrayList();
        for (int i=0; i<keyValues.length;i++) {
            list.add(keyValues[i]);
        }
        return list;
    }


}
