package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.assets.BasicTest;

import java.util.ArrayList;
import java.util.HashMap;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class BTProvider {
    public static BasicTest createEmpty() {
        return new BasicTest();
    }

    public static BasicTest createString() {
        BasicTest object = new BasicTest();
        object.setTestString(S_STRING);
        return object;
    }

    public static BasicTest createInteger() {
        BasicTest object = new BasicTest();
        object.setTestInt(S_INTEGER);
        return object;
    }

    public static BasicTest createLong() {
        BasicTest object = new BasicTest();
        object.setTestLong(SAMPLE_LONG);
        return object;
    }

    public static BasicTest createFloat() {
        BasicTest object = new BasicTest();
        object.setTestFloat(SAMPLE_FLOAT);
        return object;
    }

    public static BasicTest createDouble() {
        BasicTest object = new BasicTest();
        object.setTestDouble(SAMPLE_DOUBLE);
        return object;
    }

    public static BasicTest createDate() {
        BasicTest object = new BasicTest();
        object.setTestDate(SAMPLE_DATE);
        return object;
    }

    public static BasicTest createBoolean() {
        BasicTest object = new BasicTest();
        object.setTestBoolean(S_BOOLEAN);
        return object;
    }

    public static BasicTest createMap () {
        BasicTest map = new BasicTest();
        map.setUntypedMap((HashMap) MapProvider.createSmall());
        return map;
    }

    public static BasicTest createList () {
        BasicTest basicTest= new BasicTest();
        basicTest.setUntypedList((ArrayList) ListProvider.createSmall());
        return basicTest;
    }

    public static BasicTest createBT() {
        BasicTest basicTest = new BasicTest();
        basicTest.setBasicTest(createSimple());
        return basicTest;
    }

    public static BasicTest createST() {
        BasicTest basicTest = new BasicTest();
        basicTest.setSubTest( STProvider.createSimple());
        return basicTest;
    }

    public static BasicTest createMapST() {
        BasicTest basicTest = new BasicTest();
        basicTest.setSubTestMap((HashMap) STProvider.createSubTestMap());
        return basicTest;
    }

    public static BasicTest createListST() {
        BasicTest basicTest = new BasicTest();
        basicTest.setSubTestList((ArrayList) STProvider.createSubTestList());
        return basicTest;
    }

    static void addUntyped(BasicTest object) {
        object.setUntypedList((ArrayList) ListProvider.create());
        object.setUntypedMap((HashMap) MapProvider.create());
    }

    static void addModels(BasicTest object) {
        object.setBasicTest(createSimple());
        object.setSubTest(STProvider.createSimple());
    }

    public static BasicTest create() {
        BasicTest object = new BasicTest();
        addSimple(object);
        addUntyped(object);
        addModels(object);
        object.setSubTestMap((HashMap) STProvider.createSubTestMap());
        object.setSubTestList((ArrayList) STProvider.createSubTestList());
        return object;
    }

    public static BasicTest createSmall() {
        BasicTest object = new BasicTest();
        object.setTestString(S_STRING);
        object.setTestInt(S_INTEGER);
        return object;
    }

    static void addSimple(BasicTest object) {
        object.setTestString(S_STRING);
        object.setTestInt(S_INTEGER);
        object.setTestBoolean(S_BOOLEAN);
        object.setTestLong(SAMPLE_LONG);
        object.setTestFloat(SAMPLE_FLOAT);
        object.setTestDouble(SAMPLE_DOUBLE);
        object.setTestDate(SAMPLE_DATE);
        object.setTestBoolean(S_BOOLEAN);
    }

    public static BasicTest createSimple() {
        BasicTest object = new BasicTest();
        addSimple(object);
        return object;
    }
}
