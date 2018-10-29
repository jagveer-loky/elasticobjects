package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.F_NAME;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathNewRootMap_value_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathNewRootMap_value_Test.class);

    @Test
    public void mapNull_ok() throws Exception {
        EOTest.mapValue_ok(null);
    }

    @Test
    public void mapInteger_ok() throws Exception {
        EOTest.mapValue_ok(S_INTEGER);
    }

    @Test
    public void mapArrayList_ok() throws Exception {
        EOTest.mapValue_ok(new ArrayList());
    }

    @Test
    public void mapHashMap_ok() throws Exception {
        EOTest.mapValue_ok(new HashMap());
    }

    /**
     * Given eo builder with no Model
     * when map jsn map empty
     * Model keeps default and eo is empty.
     */
    @Test
    public void jsnMapEmpty_ok() throws Exception {
        String jsnEmpty = JSONInputReader.readInput(JSONInputReader.EMPTY);
        EO adapter = TestObjectProvider
                .createEOBuilder()
                .map(jsnEmpty);
        Assert.assertEquals(Map.class, adapter.getModelClass());
        Assert.assertTrue(adapter.isEmpty());
    }

    // Given eo builder with no model setting
    // when adding json BasicTest empty
    // the model is Map
    @Test
    public void jsonMapEmpty2_ok() throws Exception {
        final String jsonEmpty = MapProviderJSON.getJSONEmpty();
        final EO eoJsnBTEmpty = TestObjectProvider
                .createEOBuilder()
                .map(jsonEmpty);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eoJsnBTEmpty.getLog(), eoJsnBTEmpty.getLog().isEmpty());
        Assert.assertEquals(Map.class, eoJsnBTEmpty.getModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoJsnBTEmpty.get().getClass());
        Assert.assertTrue(eoJsnBTEmpty.isEmpty());
    }

    /**
     * Given eo builder with no Model
     * when adding json map with null or empty values
     * values will be added with default types.
     */
    @Test
    public void jsonMapEmptyValues_ok() throws Exception {
        EO adapter = TestObjectProvider
                .createEOBuilder()
                .map(MapProviderJSON.getJSONEmptyValues());
        Assert.assertEquals(Map.class, adapter.getChild(F_UNTYPED_MAP).getModelClass());
        Assert.assertEquals(List.class, adapter.getChild(F_UNTYPED_LIST).getModelClass());
        Assert.assertEquals(Map.class, adapter.getChild(F_TEST_OBJECT).getModelClass());
        Assert.assertEquals(new Long(0), adapter.get(F_TEST_LONG));
    }

    /**
     * Given eo builder with no Model
     * when adding json map small
     * values will be added with default types.
     */
    @Test
    public void jsonMapSmall_ok() throws Exception {
        final EO eoJsonSmall = TestObjectProvider
                .createEOBuilder()
                .map(MapProviderJSON.readSmall());
        Assert.assertEquals(new Long(S_INTEGER), eoJsonSmall.get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, eoJsonSmall.get(F_TEST_STRING));
    }

    /**
     * Given eo builder with no Model
     * when adding jsn map small
     * values will be added with the correct type.
     */
    @Test
    public void jsnMapSmall_ok() throws Exception {
        final String jsnSmall = JSONInputReader.readInput(JSONInputReader.SMALL);
        final EO eoJsnSmall = TestObjectProvider
                .createEOBuilder()
                .map(jsnSmall);
        Assert.assertEquals(S_INTEGER, eoJsnSmall.get(F_TEST_INTEGER));
        Assert.assertEquals(S_STRING, eoJsnSmall.get(F_TEST_STRING));
    }

    /**
     * Given eo builder with no Model
     * when map json map empty
     * Model keeps default and eo is empty.
     */
    @Test
    public void jsonMapEmpty_ok() throws Exception {
        EO eoRoot = TestObjectProvider
                .createEOBuilder()
                .map(MapProviderJSON.getJSONEmpty());
        Assert.assertEquals(Map.class, eoRoot.getModelClass());
        TestObjectProvider.checkLogEmpty(eoRoot);
    }


    /**
     * Given eo builder
     * when adding json list empty
     * List will be set
     */
    @Test
    public void jsnListEmpty_ok() throws Exception {
        final String jsnListEmpty = ListProviderJSON.readEmpty();
        final EO eoJsnListEmpty = DevObjectProvider
                .createEOBuilder()
                .map(jsnListEmpty);
        Assert.assertEquals(List.class, eoJsnListEmpty.getModelClass());
        //TODO checkConfig if necessary Assert.assertTrue(params.isFromJSON());
    }

    /**
     * Given eo builder
     * when adding json list empty
     * List will be set
     */
    @Test
    public void jsonListEmpty_ok() throws Exception {
        final String jsonListEmpty = ListProviderJSON.readEmpty();
        final EO eoJsonListEmpty = TestObjectProvider
                .createEOBuilder()
                .map(jsonListEmpty);
        Assert.assertEquals(List.class, eoJsonListEmpty.getModelClass());
        Assert.assertEquals(Object.class, eoJsonListEmpty.getModels().getChildModelClass());
        //TODO checkConfig if necessary Assert.assertTrue(params.isFromJSON());
    }


    @Test
    public void mapBT_ok() throws Exception {
        EOTest.mapValue_ok(new BasicTest());
    }

    @Test
    public void mapBTString_ok() throws Exception {
        EO root = EOTest.mapValue_ok(BTProvider.createString());
        Assert.assertNotNull(((EOContainer) root).getChildAdapter(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
    }

    // Given eo builder with no model setting
    // when adding jsn BasicTest empty
    // the model is BasicTest
    @Test
    public void jsnBTEmpty_ok() throws Exception {
        final String jsnBTEmpty = BTProviderJSN.readEmpty();

        final EO eoJsnBTEmpty = TestObjectProvider
                .createEOBuilder()
                .map(jsnBTEmpty);

        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eoJsnBTEmpty.getLog(), eoJsnBTEmpty.getLog().isEmpty());
        Assert.assertEquals(BasicTest.class, eoJsnBTEmpty.getModelClass());
        Assert.assertEquals(BasicTest.class, eoJsnBTEmpty.get().getClass());
        Assert.assertTrue(eoJsnBTEmpty.isEmpty());
    }

    // Given eo builder with map
    // when adding jsn BasicTest small
    // the value will be set to the path and the model is BasicTest

    @Test
    public void jsnBTSmall_ok() throws Exception {
        final String jsnBTSmall = BTProviderJSN.readSmall();

        final EO eoJsnBTSmall = TestObjectProvider
                .createEOBuilder()
                .map(jsnBTSmall);

        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eoJsnBTSmall.getLog(), eoJsnBTSmall.getLog().isEmpty());
        Assert.assertEquals(BasicTest.class, eoJsnBTSmall.getModelClass());
        Assert.assertEquals(BasicTest.class, eoJsnBTSmall.get().getClass());
        Assert.assertEquals(S_INTEGER, eoJsnBTSmall.get(F_TEST_INTEGER));
    }

    // Given eo builder with map
    // when adding json map small (json contains no information about the model)
    // the value will be set and the model is Map
    @Test
    public void jsonBTSmall_ok() throws Exception {
        final String jsonBTSmall = MapProviderJSON.readSmall();
        final EO eoJsnBTSmall = TestObjectProvider
                .createEOBuilder()
                .map(jsonBTSmall);

        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eoJsnBTSmall.getLog(), eoJsnBTSmall.getLog().isEmpty());
        Assert.assertEquals(Map.class, eoJsnBTSmall.getModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoJsnBTSmall.get().getClass());
        Assert.assertEquals(new Long(S_INTEGER), eoJsnBTSmall.get(F_TEST_INTEGER));
    }

    @Test
    public void mapMap_ChangesInOriginalMap_HasNoChangeInEO() throws Exception {
        TestHelper.printStartMethod();
        Map map = MapProvider.createString();

        EO adapter = EOTest.mapValue_ok(map);

        map.put(S_KEY1, map);
        Assert.assertEquals(S_STRING, ((Map) map.get(S_KEY1)).get(F_TEST_STRING));
        Assert.assertNull(INFO_NULL_FAILS, adapter.get(S_KEY1));
    }

    @Test
    public void mapMapWithSubMap_ok() throws Exception {
        Map map = MapProvider.createString();
        Map map2 = MapProvider.createString();
        map.put(S_KEY1, map2);

        EO adapter = EOTest.mapValue_ok(map);

        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, adapter.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, adapter.get(toPath(S_KEY1, F_TEST_STRING)));
    }


    @Ignore
    // TODO avoid recursive mapping!
    @Test
    public void mapMapWithSubMap_fails() throws Exception {
        Map map = MapProvider.createString();
        map.put(S_KEY1, map);

        EO adapter = EOTest.mapValue_ok(map);

        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, adapter.get(S_KEY1));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, adapter.get(toPath(S_TEST_STRING, S_KEY1)));
    }

    @Test
    public void setMapIntegerKey() throws Exception {
        Map map = MapProvider.toMap(3, 1);
        EO builder = TestObjectProvider.createEOBuilder()
                .map(map);
        Assert.assertEquals(new Integer(1), builder.get(S3));
    }


    @Test
    public void withList() throws Exception {
        EO builder = TestObjectProvider.createEOBuilder()
                .map(Arrays.asList(S_EMPTY, S_EMPTY, S_STRING));
        Assert.assertEquals(S_STRING, builder.get(S2));
    }


    // TODO check ...
    @Test
    public void withBT_mapMapEmpty() throws Exception {
        final EO eoBTEmpty = BTProviderJSN.createEmpty();
        Assert.assertEquals(INFO_COMPARE_FAILS, BasicTest.class, eoBTEmpty.getModelClass());
        Assert.assertEquals(INFO_COMPARE_FAILS, BasicTest.class, eoBTEmpty.get().getClass());
    }

    @Test
    public void withJsnSTSimple() throws Exception {
        String JSON = STProviderJSN.readSimple();
        EO adapter = TestObjectProvider.createEOBuilder()
                .map(JSON);
        Assert.assertEquals(S_STRING_OTHER, adapter.get(F_NAME));
        Assert.assertEquals(S_STRING, adapter.get(F_TEST_STRING));
        Assert.assertEquals(SubTest.class, adapter.getModelClass());
    }


}

