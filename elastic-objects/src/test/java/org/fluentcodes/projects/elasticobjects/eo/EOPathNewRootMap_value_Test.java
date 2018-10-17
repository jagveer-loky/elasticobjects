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
    public void mapInteger_ok() throws Exception {
        EOTest.map_ok(F_TEST_INTEGER, S_INTEGER);
    }

    @Test
    public void mapArrayList_ok() throws Exception {
        EOTest.map_ok(F_UNTYPED_LIST, new ArrayList());
    }

    @Test
    public void mapList_ok() throws Exception {
        EOTest.map_ok(F_UNTYPED_LIST, new ArrayList());
    }

    @Test
    public void mapMap_ok() throws Exception {
        EOTest.map_ok(F_UNTYPED_MAP, new HashMap());
    }

    @Test
    public void mapHashMap_ok() throws Exception {
        EOTest.map_ok(F_UNTYPED_MAP, new HashMap());
    }

    @Test
    public void withHashMapWithBT_ok() throws Exception {
        EOTest.map_ok(F_BASIC_TEST, new HashMap());
    }

    @Test
    public void withJsonSmallLevel0() throws Exception {
        EOBuilder builder = DevObjectProvider.createEOBuilder();
        String json = MapProviderJSON.getJSONSmallWithKeys();
        EO child = builder
                .setPath(S_LEVEL0)
                .map(json);
        EO root = child.getRoot();
        Assert.assertEquals(Map.class, child.getModelClass());
        Assert.assertEquals(String.class, child.getChild(S_KEY0).getModelClass());
        Assert.assertEquals(S_STRING, child.get(S_KEY0));
        Assert.assertEquals(Long.class, child.getChild(S_KEY1).getModelClass());
        Assert.assertEquals(new Long(S_INTEGER), child.get(S_KEY1));
        Assert.assertEquals(LinkedHashMap.class, root.get(Path.DELIMITER).getClass());
    }

    @Test
    public void withJsonmallWithKeysAndList() throws Exception {
        EOBuilder builder = DevObjectProvider.createEOBuilder();
        String json = MapProviderJSON.getJSONSmallWithKeysAndList();
        EO adapter = builder
                .setPath(S_LEVEL0)
                .map(json);
        Assert.assertEquals(Map.class, adapter.getModelClass());
        Assert.assertEquals(String.class, adapter.getChild(S_KEY0).getModelClass());
        Assert.assertEquals(S_STRING, adapter.get(S_KEY0));
        Assert.assertEquals(List.class, adapter.getChild(S_KEYLIST).getModelClass());
        Assert.assertEquals(new Long(S_INTEGER), adapter.getChild(S_KEYLIST).get(S1));
        Assert.assertEquals(S_STRING, adapter.getChild(S_KEYLIST).get(S0));
        Assert.assertEquals(LinkedHashMap.class, adapter.getRoot().get().getClass());
    }

    @Test
    public void withListStringPath2() throws Exception {
        TestHelper.printStartMethod();
        List<String> list = ListProvider.toList(S_STRING);
        EO adapter = TestObjectProvider.createEOBuilder()
                .setPath(S_PATH2)
                .map(list);
        Assert.assertEquals(S_STRING, adapter.getRoot().get(toPath(S_PATH2,S0)));
    }



    @Test
    public void mapBT_ok() throws Exception {
        EOTest.map_ok(F_BASIC_TEST, new BasicTest());
    }

    @Test
    public void mapBTStringWithMap_ok() throws Exception {
        final EO child = EOTest.map_ok(F_UNTYPED_MAP, BTProvider.createString());
        Assert.assertNotNull(((EOContainer)child).getChildAdapter(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
    }

    /**
     * Given empty eo with map model
     * when adding jsn BasicTest small with one entry path
     * the value will be set to the path and the model in the path is BasicTest
     */

    @Test
    public void jsnBTSmallLevel0_ok() throws Exception {
        final String jsnSmall = BTProviderJSN.readSmall();
        final EO eoMapEmpty = MapProviderEO.createEmpty();

        eoMapEmpty
                .add(S_LEVEL0)
                .map(jsnSmall);

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
    public void jsnBTSmallPath2_ok() throws Exception {
        final EO eoMapEmpty = MapProviderEO.createEmpty();
        final String jsnSmall = BTProviderJSN.readSmall();

        eoMapEmpty
                .add(S_PATH2)
                .map(jsnSmall);

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
    public void jsnBTAllPath2_ok() throws Exception {
        final String jsnAll = BTProviderJSN.readAll();
        final EO eoMapEmpty = MapProviderEO.createEmpty();

        eoMapEmpty
                .add(S_PATH2)
                .map(jsnAll);

        Assert.assertEquals(BasicTest.class, eoMapEmpty.getChild(S_PATH2).getModelClass());
        Assert.assertEquals(BasicTest.class, eoMapEmpty.getChild(S_PATH2).get().getClass());
        Assert.assertEquals(S_INTEGER, eoMapEmpty.get(S_PATH2 + Path.DELIMITER + F_TEST_INTEGER));
        AssertEO.compare(eoMapEmpty);
    }

}

