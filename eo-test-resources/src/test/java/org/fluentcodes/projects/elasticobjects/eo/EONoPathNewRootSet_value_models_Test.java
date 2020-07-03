package org.fluentcodes.projects.elasticobjects.eo;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.assets.BasicTest;
import org.fluentcodes.projects.elasticobjects.assets.SubTest;
import org.fluentcodes.projects.elasticobjects.test.EOTest;
import org.fluentcodes.projects.elasticobjects.test.STProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

public class EONoPathNewRootSet_value_models_Test extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EONoPathNewRootSet_value_models_Test.class);

    @Test
    public void withMapAndInteger_ok()  {
        EOTest.setValueWins_ok(S_INTEGER, Map.class);
    }

    @Test
    public void withBTAndInteger_ok()  {
        EOTest.setValueWins_ok(S_INTEGER, BasicTest.class);
    }

    @Test
    public void withIntegerAndInteger_ok()  {
        EOTest.setValueWins_ok(S_INTEGER, Integer.class);
    }

    @Test
    public void withIntegerAndHashMap_ok()  {
        EOTest.setValueWins_ok(new HashMap(), Integer.class);
    }

    @Test
    public void withListAndArrayList_ok()  {
        EOTest.setValue_ok(new ArrayList(), List.class);
    }

    @Test
    public void withMapAndArrayList_ok()  {
        EOTest.setValueWins_ok(new ArrayList(), Map.class);
    }

    @Test
    public void withMapAndHashMap_ok()  {
        EOTest.setValue_ok(new HashMap(), Map.class);
    }

    @Test
    public void withBTAndBT_ok()  {
        EOTest.setValue_ok(new BasicTest(), BasicTest.class);
    }

    @Test
    public void withBTAndMap_ok()  {
        EOTest.setValueWins_ok(new BasicTest(), Map.class);
    }

    @Test
    public void withST_ok()  {
        final EO root = EOTest
                .setValue_ok(STProvider.createString(), SubTest.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
    }

    @Test
    public void withSTAndMap_ok()  {
        final EO root = EOTest
                .setValueWins_ok(STProvider.createString(), Map.class);
        Assert.assertEquals(INFO_COMPARE_FAILS, S_STRING, root.get(F_TEST_STRING));
    }
}

