package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.junit.Assert;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class STProvider {

    public static SubTest createEmpty() {
        return new SubTest();
    }

    public static SubTest createString() {
        SubTest object = new SubTest();
        object.setTestString(S_STRING);
        return object;
    }

    public static SubTest createName() {
        SubTest object = new SubTest();
        object.setName(S_STRING_OTHER);
        return object;
    }

    private static void addSubTest(SubTest object) {
        object.setSubTest(createSimple());
    }

    public static SubTest createST() {
        SubTest object = new SubTest();
        object.setSubTest(createSimple());
        return object;
    }

    static void addSimple(SubTest object) {
        object.setTestString(S_STRING);
        object.setName(S_STRING_OTHER);
    }

    public static SubTest createSimple() {
        SubTest object = new SubTest();
        addSimple(object);
        return object;
    }

    public static Map<String, SubTest> createSubTestMap() {
        Map<String, SubTest> map = new LinkedHashMap<>();
        map.put(S_KEY0, createSimple());
        map.put(S_KEY1, createSimple());
        map.put(S_KEY2, createSimple());
        return map;
    }

    public static List<SubTest> createSubTestList() {
        List<SubTest> list = new ArrayList<>();
        list.add(createSimple());
        list.add(createSimple());
        list.add(createSimple());
        return list;
    }

    public static SubTest create() {
        SubTest object = new SubTest();
        addSimple(object);
        addSubTest(object);
        return object;
    }

    public static void createSTRecursivePath3() {
        Assert.assertEquals(F_SUB_TEST + Path.DELIMITER + F_SUB_TEST + Path.DELIMITER + F_SUB_TEST, STProviderEO.createSTPath(3));
    }
}
