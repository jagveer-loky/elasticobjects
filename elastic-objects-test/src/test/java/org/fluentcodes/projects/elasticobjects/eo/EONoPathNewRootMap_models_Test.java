package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.DevObjectProvider;
import org.fluentcodes.projects.elasticobjects.test.EOTestHelper;
import org.fluentcodes.projects.elasticobjects.test.MapProviderJSN;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathNewRootMap_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathNewRootMap_models_Test.class);

    @Test
    public void mapString_ok()  {
        EOTestHelper.map_ok(String.class);
    }

    @Test
    public void mapInteger_ok()  {
        EOTestHelper.map_ok(Integer.class);
    }

    @Test
    public void mapList_ok()  {
        EOTestHelper.map_ok(List.class);
    }

    @Test
    public void mapArrayList_ok()  {
        EOTestHelper.map_ok(ArrayList.class);
    }

    @Test
    public void mapListString_ok()  {
        EO eoEmpty = EOTestHelper.map_ok(List.class, String.class);
        Assert.assertEquals(String.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(ArrayList.class, eoEmpty.get().getClass());
    }

    @Test
    public void mapListBT_ok()  {
        EO eoEmpty = EOTestHelper.map_ok(List.class, BasicTest.class);
        Assert.assertEquals(BasicTest.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(ArrayList.class, eoEmpty.get().getClass());
    }

    @Test
    public void mapMapString_ok()  {
        EO eoEmpty = EOTestHelper.map_ok(Map.class, String.class);
        Assert.assertEquals(String.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoEmpty.get().getClass());
    }

    @Test
    public void mapMapList_ok()  {
        EO eoEmpty = EOTestHelper.map_ok(Map.class, List.class);
        Assert.assertEquals(List.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoEmpty.get().getClass());
    }

    @Test
    public void mapMapBT_ok()  {
        EO eoEmpty = EOTestHelper.map_ok(Map.class, BasicTest.class);
        Assert.assertEquals(BasicTest.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoEmpty.get().getClass());
    }

    @Test
    public void mapHashMap_ok()  {
        EOTestHelper.map_ok(HashMap.class);
    }

    @Test
    public void mapBT_ok()  {
        EOTestHelper.map_ok(BasicTest.class);
    }
}

