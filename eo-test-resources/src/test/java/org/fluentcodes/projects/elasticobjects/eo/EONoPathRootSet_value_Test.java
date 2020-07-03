package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NAME;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * @author Werner Diwischek
 * @since 1.10.2018
 */

public class EONoPathRootSet_value_Test {
    private static final Logger LOG = LogManager.getLogger(EONoPathRootSet_value_Test.class);

    @Test
    public void givenString_withInteger_ok()  {
        final EO eoString = DevObjectProvider.createEOString();
        EOTest
                .setEOValue_ok(eoString, S_INTEGER);
    }

    @Test
    public void createWithInteger_ok()  {
        final EORoot root = new EORoot(DevObjectProvider.EO_CONFIGS);
        Assert.assertNotNull(root);
        root.setValue("test", "test2");
        String value = (String)root.get("test");
    }


    @Test
    public void givenString_withBoolean_ok()  {
        final EO eoString = DevObjectProvider.createEOString();
        EOTest
                .setEOValue_ok(eoString, S_BOOLEAN);
    }

    @Test
    public void givenString_withMap_ok()  {
        final EO eoString = DevObjectProvider.createEOString();
        EOTest
                .setEOValue_ok(eoString, new LinkedHashMap());
    }

    @Test
    public void givenMapEmpy_withNull_ok()  {
        final EO adapter = DevObjectProvider.createEO();
        adapter.add().set(null);
        Assert.assertEquals(Map.class, adapter.getModelClass());
    }

    @Test
    public void givenMapEmpty_withString_ok()  {
        EO eoMapEmpty = DevObjectProvider.createEO();
        eoMapEmpty.add()
                .set(S_STRING);
    }

    @Test
    public void givenMapString_withString_ok()  {
        final EO eoMapString = MapProviderEO.createString();
        EOTest
                .setEOValue_ok(eoMapString, S_STRING_OTHER);
        Assert.assertEquals(S_STRING_OTHER, eoMapString.get());
    }

    @Test
    public void givenMapString_withMapEmpty_ok()  {
        final EO eoMapString = MapProviderEO.createString();
        eoMapString.add().set(new HashMap<>());
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eoMapString.getModelClass());
        Assert.assertNull(INFO_NULL_FAILS, eoMapString.get(F_TEST_STRING));
    }

    @Test
    public void givenMapEmpty_withMapString_ok()  {
        final EO eoMapEmpty = MapProviderEO.create();
        eoMapEmpty.add().set(MapProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, eoMapEmpty.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMapEmpty.get(F_TEST_STRING));
    }

    @Test
    public void givenMapEmpty_withBT_ok()  {
        final EO eoMapString = MapProviderEO.createEmpty();
        EOTest
                .setEOValue_ok(eoMapString, new BasicTest());
    }

    @Test
    public void givenMapEmpty_withSTString_ok()  {
        final EO eoMapString = MapProviderEO.createEmpty();
        EOTest
                .setEOValue_ok(eoMapString, STProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMapString.get(F_TEST_STRING));
    }

    /**
     * Given empty eo with ListModel model
     * when adding json map small
     * original model ListModel is kept and values mapped to List.
     */
    //TODO Behaviour as decided?!
    @Test
    public void givenListEmpty_withMapJsonSmall_ok()  {
        final EO listEO = ListProviderEO.createEmpty();
        final String jsonMapSmall = MapProviderJSON.readSmall();
        listEO
                .add()
                .map(jsonMapSmall);
        Assert.assertEquals(S_STRING, listEO.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), listEO.get(S1));
        Assert.assertEquals(List.class, listEO.getModelClass());
        Assert.assertEquals(String.class, listEO.getChild(S0).getModelClass());
    }


    @Test
    public void givenBTString_withBTInteger_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));

        EOTest
                .setEOValue_ok(eoBTString, BTProvider.createInteger());
        Assert.assertNull(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTString_withMapInteger_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));

        EOTest
                .setEOValue_ok(eoBTString, MapProvider.createInteger());
        Assert.assertNull(INFO_NULL_FAILS, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTString_withString_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        EOTest
                .setEOValue_ok(eoBTString, S_STRING);
        Assert.assertEquals(S_STRING, eoBTString.get());
    }

    @Test
    public void givenBTString_WithBTInteger_ok()  {
        final EO eoBTString = BTProviderEO.createString();
        EOTest
                .setEOValue_ok(eoBTString, BTProvider.createInteger());

        Assert.assertNull(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }


    @Test
    public void givenSTSimple_withSTString_ok()  {
        final EO eoMapString = STProviderEO.createSimple();
        EOTest
                .setEOValue_ok(eoMapString, STProvider.createString());
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoMapString.get(F_TEST_STRING));
        Assert.assertNull(INFO_NULL_FAILS, eoMapString.get(F_NAME));
    }
}


