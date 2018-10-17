package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathChildSet_value_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathChildSet_value_Test.class);

    @Test
    public void givenMapString_withInteger_ok() throws Exception {
        final EO root = TestObjectProvider
                .createEOBuilder()
                .set(MapProvider.createString());

        final EO child = root.getChild(F_TEST_STRING);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get());

        EOTest
                .setEOValue_ok(child, S_INTEGER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER, root.get(F_TEST_STRING));
    }

    @Test
    public void givenMapString_withMap_ok() throws Exception {
        final EO root = TestObjectProvider
                .createEOBuilder()
                .set(MapProvider.createString());

        final EO child = root.getChild(F_TEST_STRING);

        EOTest
                .setEOValue_ok(child, new HashMap());
        Assert.assertEquals(INFO_COMPARE_FAILS, HashMap.class, root.get(F_TEST_STRING).getClass());
    }

    @Test
    public void givenListEmpty_withBoolean_ok() throws Exception {
        final EO root = DevObjectProvider.createEO(List.class);
        root
                .add()
                .set(S_BOOLEAN);
        Assert.assertEquals(S_BOOLEAN, root.get());
        Assert.assertEquals(Boolean.class, root.getModelClass());
    }


    @Test
    public void givenBTMap_withMapString_ok() throws Exception {
        final EO root = TestObjectProvider
                .createEOBuilder()
                .set(BTProvider.createMap());
        final EO child = root.getChild(F_UNTYPED_MAP);

        child.add().set(MapProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, HashMap.class, child.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
    }

    @Test
    public void givenBTString_withInteger_fails() throws Exception {
        final EO root = TestObjectProvider
                .createEOBuilder()
                .set(BTProvider.createString());
        final EO child = root.getChild(F_TEST_STRING);

        EOTest
                .setEO_fails(child, S_INTEGER);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
    }

    @Test
    public void givenBTString_withBTInteger_fails() throws Exception {
        final EO root = TestObjectProvider
                .createEOBuilder()
                .set(BTProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));

        final EO child = root.getChild(F_TEST_STRING);
        EOTest
                .setEO_fails(child, BTProvider.createInteger());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
    }

    @Test
    public void givenBTString_withMapInteger_fails() throws Exception {
        final EO root = TestObjectProvider
                .createEOBuilder()
                .set(BTProvider.createString());

        final EO child = root.getChild(F_TEST_STRING);
        EOTest
                .setEO_fails(child, MapProvider.createInteger());
    }

}

