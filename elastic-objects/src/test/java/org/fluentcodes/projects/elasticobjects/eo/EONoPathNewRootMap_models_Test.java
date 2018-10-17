package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.test.*;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.*;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.S0;

public class EONoPathNewRootMap_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathNewRootMap_models_Test.class);

    @Test
    public void mapString_ok() throws Exception {
        EOTest.map_ok(String.class);
    }

    @Test
    public void mapInteger_ok() throws Exception {
        EOTest.map_ok(Integer.class);
    }

    @Test
    public void mapList_ok() throws Exception {
        EOTest.map_ok(List.class);
    }

    @Test
    public void mapArrayList_ok() throws Exception {
        EOTest.map_ok(ArrayList.class);
    }

    @Test
    public void mapListString_ok() throws Exception {
        EO eoEmpty = EOTest.map_ok(List.class, String.class);
        Assert.assertEquals(String.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(ArrayList.class, eoEmpty.get().getClass());
    }

    @Test
    public void mapListBT_ok() throws Exception {
        EO eoEmpty = EOTest.map_ok(List.class, BasicTest.class);
        Assert.assertEquals(BasicTest.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(ArrayList.class, eoEmpty.get().getClass());
    }

    /**
     * Given eo builder setting List Model
     * when adding json map empty
     * List will be set
     */
    @Test
    public void withJsnMapSmallList_ok() throws Exception {
        final String jsnMapSmall = MapProviderJSN.readSmall();
        final EO listEO = DevObjectProvider.createEOBuilder()
                .setModels(List.class)
                .map(jsnMapSmall);
        Assert.assertEquals(S_STRING, listEO.get(S0));
        Assert.assertEquals(S_INTEGER, listEO.get(S1));
        Assert.assertEquals(List.class,listEO.getModelClass());
        Assert.assertEquals(String.class,listEO.getChild(S0).getModelClass());
    }

    @Test
    public void mapMap_ok() throws Exception {
        EOTest.map_ok(Map.class);
    }

    @Test
    public void mapMapString_ok() throws Exception {
        EO eoEmpty = EOTest.map_ok(Map.class, String.class);
        Assert.assertEquals(String.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoEmpty.get().getClass());
    }

    @Test
    public void mapMapList_ok() throws Exception {
        EO eoEmpty = EOTest.map_ok(Map.class, List.class);
        Assert.assertEquals(List.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoEmpty.get().getClass());
    }

    @Test
    public void mapMapBT_ok() throws Exception {
        EO eoEmpty = EOTest.map_ok(Map.class, BasicTest.class);
        Assert.assertEquals(BasicTest.class, eoEmpty.getModels().getChildModelClass());
        Assert.assertEquals(LinkedHashMap.class, eoEmpty.get().getClass());
    }

    @Test
    public void mapHashMap_ok() throws Exception {
        EOTest.map_ok(HashMap.class);
    }

    @Test
    public void mapBT_ok() throws Exception {
        EOTest.map_ok(BasicTest.class);
    }
}

