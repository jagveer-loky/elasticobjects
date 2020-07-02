package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOPathNewRootMap_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EOPathNewRootMap_models_Test.class);

    @Test
    public void mapInteger_ok() throws Exception {
        EOTest.map_ok(F_TEST_INTEGER, Integer.class);
    }

    @Test
    public void mapMap_ok() throws Exception {
        EOTest.map_ok(F_UNTYPED_MAP, Map.class);
    }

    @Test
    public void mapHasMap_ok() throws Exception {
        EOTest.map_ok(F_UNTYPED_MAP, HashMap.class);
    }


    @Test
    public void mapList_ok() throws Exception {
        EOTest.map_ok(F_UNTYPED_LIST, List.class);
    }

    @Test
    public void mapArrayList_ok() throws Exception {
        EOTest.map_ok(F_UNTYPED_LIST, ArrayList.class);
    }

    @Test
    public void mapBT_ok() throws Exception {
        EOTest.map_ok(F_BASIC_TEST, BasicTest.class);
    }


    /**
     * Given eo builder with BasicTest Model
     * when adding jsn empty
     * the model is BasicTest
     */

    @Test
    public void mapJsnEmptyBT_ok() throws Exception {
        final String jsnEmpty = MapProviderJSON.readEmpty();

        final EO eoBTJson = TestEOProvider
                .createEOBuilder()
                .setModels(BasicTest.class)
                .map(jsnEmpty);

        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eoBTJson.getLog(), eoBTJson.getLog().isEmpty());
        Assert.assertEquals(BasicTest.class, eoBTJson.getModelClass());
        Assert.assertEquals(BasicTest.class, eoBTJson.get().getClass());
        Assert.assertTrue(eoBTJson.isEmpty());
    }

    // Given eo builder with BasicTest
    // when adding jsn small
    // the model is BasicTest
    @Test
    public void mapJsnSmallBT_ok() throws Exception {
        final String jsnSmall = MapProviderJSON.readSmall();

        final EO eoBTJsonSmall = TestEOProvider
                .createEOBuilder()
                .setModels(BasicTest.class)
                .map(jsnSmall);

        Assert.assertTrue(INFO_LOG_EMPTY_FAILS + eoBTJsonSmall.getLog(), eoBTJsonSmall.getLog().isEmpty());
        Assert.assertEquals(BasicTest.class, eoBTJsonSmall.getModelClass());
        Assert.assertEquals(BasicTest.class, eoBTJsonSmall.get().getClass());
        Assert.assertEquals(S_INTEGER, eoBTJsonSmall.get(F_TEST_INTEGER));
    }

    /**
     * Given eo builder with BasicTest
     * when adding jsn list small
     * no value will be added since no mapping is available and instead of an execption a log message is written.
     */

    @Test
    public void mapJsnListSmallBT_fails() throws Exception {
        final String jsnListSmall = ListProviderJSON.readSmall();

        final EO eoBTJsonListSmall = TestEOProvider
                .createEOBuilder()
                .setModels(BasicTest.class)
                .map(jsnListSmall);

        Assert.assertEquals(BasicTest.class, eoBTJsonListSmall.getModelClass());
        Assert.assertFalse(INFO_LOG_NOT_EMPTY_FAILS, eoBTJsonListSmall.getLog().isEmpty());
    }
}

