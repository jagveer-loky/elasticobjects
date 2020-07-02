package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Models will be ignored anyway.
 * So it will be basically the same as {@link EONoPathRootMap_value_Test}
 */
public class EONoPathRootMap_value_models_Test {
    private static final Logger LOG = LogManager.getLogger(EONoPathRootMap_value_models_Test.class);

    public static EO mapEO_ok(final EO root, final Object value, final Class... classes) throws Exception {
        final Class modelClass = root.getModelClass();
        final EO child = root.add()
                .setModels(classes)
                .map(value);
        Assert.assertEquals(INFO_COMPARE_FAILS, modelClass, root.getModelClass());
        Assert.assertTrue(INFO_EMPTY_FAILS, root.getLog().isEmpty());
        return child;
    }

    public static EO mapEO_fails(final EO root, final Object value, final Class... classes) throws Exception {
        final EO child = root.add()
                .setModels(classes)
                .map(value);
        Assert.assertFalse(INFO_NOT_EMPTY_FAILS, root.getLog().isEmpty());
        return child;
    }

    @Test
    public void givenString_withIntegerAndInteger_ok() throws Exception {
        final EO eoString = DevObjectProvider
                .createEOBuilder()
                .set(S_STRING);
        mapEO_ok(eoString, S_INTEGER, Integer.class);
        Assert.assertEquals(S_INTEGER.toString(), eoString.get());
    }

    @Test
    public void givenString_withBooleanAndInteger_ok() throws Exception {
        final EO eoString = DevObjectProvider
                .createEOBuilder()
                .set(S_STRING);
        mapEO_ok(eoString, S_BOOLEAN, Integer.class);
        Assert.assertEquals(S_BOOLEAN.toString(), eoString.get());
    }


    @Test
    public void givenString_WithMapAndList_fails() throws Exception {
        final EO eoString = DevObjectProvider
                .createEOBuilder()
                .set(S_STRING);
        mapEO_fails(eoString, new LinkedHashMap(), List.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, eoString.get());
    }

    @Test
    public void givenMapString_withStringAndMap_fails() throws Exception {
        final EO eoMap = MapProviderEO.createString();
        mapEO_fails(eoMap, S_STRING_OTHER, Map.class);
    }

    @Test
    public void givenMapWithString_withEmptyMapAndString_ok() throws Exception {
        final EO eoMap = MapProviderEO.createString();
        Assert.assertEquals(S_STRING, eoMap.get(F_TEST_STRING));
        mapEO_ok(eoMap, new HashMap(), String.class);
        Assert.assertEquals(S_STRING, eoMap.get(F_TEST_STRING));
    }

    @Test
    public void givenBTWithString_withBTWithInteger_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));

        mapEO_ok(eoBTString, BTProvider.createInteger(), Map.class);
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTWithString_withMapWithIntegerAndInteger_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));

        mapEO_ok(eoBTString, MapProvider.createInteger(), Integer.class);
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
        Assert.assertEquals(S_INTEGER, eoBTString.get(F_TEST_INTEGER));
    }

    @Test
    public void givenBTWithString_withString_fails() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        mapEO_fails(eoBTString, S_STRING_OTHER);
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
    }

    @Test
    public void givenBTString_withMap_ok() throws Exception {
        final EO eoBTString = BTProviderEO.createString();
        mapEO_ok(eoBTString, new HashMap());
        Assert.assertEquals(S_STRING, eoBTString.get(F_TEST_STRING));
    }


}


