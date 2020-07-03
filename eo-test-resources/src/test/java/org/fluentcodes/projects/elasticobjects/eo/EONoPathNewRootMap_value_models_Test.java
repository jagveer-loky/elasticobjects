package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathNewRootMap_value_models_Test {
    private static final Logger LOG = LogManager.getLogger(EONoPathNewRootMap_value_models_Test.class);

    @Test
    public void mapInteger_ok()  {
        EOTest.mapValue_ok(S_INTEGER, Integer.class);
    }

    @Test
    public void mapInteger_withString_ok()  {
        EO root = EOTest.mapValue_ok(S_INTEGER, String.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_INTEGER.toString(), root.get());
    }

    @Test
    public void mapArrayList_ok()  {
        EOTest.mapValue_ok(new ArrayList(), ArrayList.class);
    }

    @Test
    public void mapList_ok()  {
        EOTest.mapValue_ok(new ArrayList(), List.class);
    }

    @Test
    public void mapMap_ok()  {
        EOTest.mapValue_ok(new HashMap(), Map.class);
    }

    @Test
    public void mapHashMap_ok()  {
        EOTest.mapValue_ok(new HashMap(), HashMap.class);
    }

    /**
     * Given eo builder with List Model
     * when adding json map empty
     * List will be set
     */

    //TODO Is this the correct behaviour expected?!
    @Test
    public void jsonMapEmptyList_ok()  {
        final String jsonMapSmall = MapProviderJSON.readSmall();
        final EO listEO = TestEOProvider
                .createEOBuilder()
                .setModels(List.class)
                .map(jsonMapSmall);
        Assert.assertEquals(S_STRING, listEO.get(S0));
        Assert.assertEquals(new Long(S_INTEGER), listEO.get(S1));
        Assert.assertEquals(List.class, listEO.getModelClass());
        Assert.assertEquals(String.class, listEO.getChild(S0).getModelClass());
    }


    /**
     * Given eo builder with BasicTest
     * when adding json empty
     * the model is BasicTest
     */

    @Test
    public void jsonMapEmptyBT_ok()  {
        final String jsonEmpty = MapProviderJSON.getJSONEmpty();
        final EO eoBTJson = TestEOProvider
                .createEOBuilder()
                .setModels(BasicTest.class)
                .map(jsonEmpty);
        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eoBTJson.getLog(), eoBTJson.getLog().isEmpty());
        Assert.assertEquals(BasicTest.class, eoBTJson.getModelClass());
        Assert.assertEquals(BasicTest.class, eoBTJson.get().getClass());
        Assert.assertTrue(eoBTJson.isEmpty());
    }

    // Given eo builder with BasicTest
    // when adding json small
    // the model is BasicTest
    @Test
    public void jsonMapSmallBT_ok()  {
        final String jsonSmall = MapProviderJSON.readSmall();

        final EO eoBTJsonSmall = TestEOProvider
                .createEOBuilder()
                .setModels(BasicTest.class)
                .map(jsonSmall);

        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eoBTJsonSmall.getLog(), eoBTJsonSmall.getLog().isEmpty());
        Assert.assertEquals(BasicTest.class, eoBTJsonSmall.getModelClass());
        Assert.assertEquals(BasicTest.class, eoBTJsonSmall.get().getClass());
        Assert.assertEquals(S_INTEGER, eoBTJsonSmall.get(F_TEST_INTEGER));
    }

    /**
     * Given eo builder with BasicTest
     * when adding json list small
     * no value will be added since no mapping is available and instead of an execption a log message is written.
     */
    @Test
    public void jsonListSmallBT_fails()  {
        final String jsonListSmall = ListProviderJSON.getSmall();

        final EO eoBTJsonListSmall = TestEOProvider
                .createEOBuilder()
                .setModels(BasicTest.class)
                .map(jsonListSmall);

        Assert.assertEquals(BasicTest.class, eoBTJsonListSmall.getModelClass());
        Assert.assertFalse(INFO_EXPECTED_EXCEPTION, eoBTJsonListSmall.getLog().isEmpty());
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + eoBTJsonListSmall.size(), eoBTJsonListSmall.isEmpty());
    }

    @Test
    public void mapHashMapWithBT_ok()  {
        EOTest.mapValue_ok(new HashMap(), BasicTest.class);
    }

    @Test
    public void mapBT_ok()  {
        EOTest.mapValue_ok(new BasicTest(), BasicTest.class);
    }


    @Test
    public void withST_ok()  {
        final EO root = EOTest
                .mapValue_ok(STProvider.createString(), SubTest.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
    }

    @Test
    public void withSTAndMap_ok()  {
        final EO root = EOTest
                .mapValue_ok(STProvider.createString(), Map.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
    }

    @Test
    public void mapBTStringWithMap_ok()  {
        EO root = EOTest.mapValue_ok(BTProvider.createString(), Map.class);
        Assert.assertNotNull(((EOContainer) root).getChildAdapter(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
    }


    /**
     * Given eo builder with SubTest Model
     * when adding jsn empty
     * the model is Subest
     */

    @Test
    public void jsnMapEmptyST_ok()  {
        final String jsnEmpty = MapProviderJSON.getJSONEmpty();

        final EO eoBTJson = TestEOProvider
                .createEOBuilder()
                .setModels(SubTest.class)
                .map(jsnEmpty);

        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eoBTJson.getLog(), eoBTJson.getLog().isEmpty());
        Assert.assertEquals(SubTest.class, eoBTJson.getModelClass());
        Assert.assertEquals(SubTest.class, eoBTJson.get().getClass());
        Assert.assertTrue(eoBTJson.isEmpty());
    }

}

