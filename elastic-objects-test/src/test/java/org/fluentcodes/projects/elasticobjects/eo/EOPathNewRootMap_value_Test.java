package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOPathNewRootMap_value_Test {
    private static final Logger LOG = LogManager.getLogger(EOPathNewRootMap_value_Test.class);

    @Test
    public void mapInteger_ok()  {
        EOTestHelper.map_ok(F_TEST_INTEGER, S_INTEGER);
    }

    @Test
    public void mapArrayList_ok()  {
        EOTestHelper.map_ok(F_UNTYPED_LIST, new ArrayList());
    }

    @Test
    public void mapList_ok()  {
        EOTestHelper.map_ok(F_UNTYPED_LIST, new ArrayList());
    }

    @Test
    public void mapMap_ok()  {
        EOTestHelper.map_ok(F_UNTYPED_MAP, new HashMap());
    }

    @Test
    public void mapHashMap_ok()  {
        EOTestHelper.map_ok(F_UNTYPED_MAP, new HashMap());
    }

    @Test
    public void withHashMapWithBT_ok()  {
        EOTestHelper.map_ok(F_BASIC_TEST, new HashMap());
    }

    @Test
    public void withListStringPath2()  {
        TestHelper.printStartMethod();
        List<String> list = ListProvider.toList(S_STRING);
        EO adapter = TestEOProvider.create(list);
        Assert.assertEquals(S_STRING, adapter.getRoot().get(toPath(S_PATH2, S0)));
    }


    @Test
    public void mapBT_ok()  {
        EOTestHelper.map_ok(F_BASIC_TEST, new BasicTest());
    }

    @Test
    public void mapBTStringWithMap_ok()  {
        final EO child = EOTestHelper.map_ok(F_UNTYPED_MAP, BTProvider.createString());
        Assert.assertNotNull(child.getChild(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
    }

    /**
     * Given empty eo with map model
     * when adding jsn BasicTest small with one entry path
     * the value will be set to the path and the model in the path is BasicTest
     */

    @Test
    public void jsnBTSmallLevel0_ok()  {
        final String jsnSmall = BTProviderJSN.readSmall();
        final EO eoMapEmpty = MapProviderEO.createEmpty();

        eoMapEmpty.setPathValue(S_LEVEL0, jsnSmall);

        Assert.assertEquals(BasicTest.class, eoMapEmpty.getChild(S_LEVEL0).getModelClass());
        Assert.assertEquals(BasicTest.class, eoMapEmpty.getChild(S_LEVEL0).get().getClass());
        Assert.assertTrue(eoMapEmpty.getLog().isEmpty());
        Assert.assertEquals(S_INTEGER, eoMapEmpty.get(S_LEVEL0 + Path.DELIMITER + F_TEST_INTEGER));
        AssertEO.compare(eoMapEmpty);
    }

    /**
     * Given empty eo with map model
     * when adding jsn BasicTest small with longer path
     * the value will be set to the path and the model in the path is BasicTest
     */

    @Test
    public void jsnBTSmallPath2_ok()  {
        final EO eoMapEmpty = MapProviderEO.createEmpty();
        final String jsnSmall = BTProviderJSN.readSmall();

        eoMapEmpty.setPathValue(S_PATH2, jsnSmall);

        Assert.assertEquals(BasicTest.class, eoMapEmpty.getChild(S_PATH2).getModelClass());
        Assert.assertEquals(BasicTest.class, eoMapEmpty.getChild(S_PATH2).get().getClass());
        Assert.assertEquals(S_INTEGER, eoMapEmpty.get(S_PATH2 + Path.DELIMITER + F_TEST_INTEGER));
        AssertEO.compare(eoMapEmpty);
    }

    /**
     * Given empty eo with map model
     * when adding jsn BasicTest all with longer path
     * the value will be set to the path and the model in the path is BasicTest
     */

    @Test
    public void jsnBTAllPath2_ok()  {
        final String jsnAll = BTProviderJSN.readAll();
        final EO eoMapEmpty = MapProviderEO.createEmpty();

        eoMapEmpty.setPathValue(S_PATH2, jsnAll);

        Assert.assertEquals(BasicTest.class, eoMapEmpty.getChild(S_PATH2).getModelClass());
        Assert.assertEquals(BasicTest.class, eoMapEmpty.getChild(S_PATH2).get().getClass());
        Assert.assertEquals(S_INTEGER, eoMapEmpty.get(S_PATH2 + Path.DELIMITER + F_TEST_INTEGER));
        AssertEO.compare(eoMapEmpty);
    }

}

