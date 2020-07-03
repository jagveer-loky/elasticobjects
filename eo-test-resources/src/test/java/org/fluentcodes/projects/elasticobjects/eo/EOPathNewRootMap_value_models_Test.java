package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.BTProvider;
import org.fluentcodes.projects.elasticobjects.test.EOTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EOPathNewRootMap_value_models_Test {
    private static final Logger LOG = LogManager.getLogger(EOPathNewRootMap_value_models_Test.class);

    @Test
    public void mapInteger_ok()  {
        EOTest.map_ok(F_TEST_INTEGER, S_INTEGER, Integer.class);
    }

    @Test
    public void mapArrayList_ok()  {
        EOTest.map_ok(F_UNTYPED_LIST, new ArrayList(), ArrayList.class);
    }

    @Test
    public void mapList_ok()  {
        EOTest.map_ok(F_UNTYPED_LIST, new ArrayList(), List.class);
    }

    @Test
    public void mapMap_ok()  {
        EOTest.map_ok(F_UNTYPED_MAP, new HashMap(), Map.class);
    }

    @Test
    public void mapHashMap_ok()  {
        EOTest.map_ok(F_UNTYPED_MAP, new HashMap(), HashMap.class);
    }

    @Test
    public void mapHashMapWithBT_ok()  {
        EOTest.map_ok(F_BASIC_TEST, new HashMap(), BasicTest.class);
    }

    @Test
    public void mapBT_ok()  {
        EOTest.map_ok(F_BASIC_TEST, new BasicTest(), BasicTest.class);
    }

    @Test
    public void mapBTStringWithMap_ok()  {
        final EO child = EOTest.map_ok(F_UNTYPED_MAP, BTProvider.createString(), Map.class);
        Assert.assertNotNull(((EOContainer) child).getChildAdapter(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, child.get(F_TEST_STRING));
        Assert.assertEquals(INFO_COMPARE_FAILS, Map.class, child.getModelClass());
    }

}

