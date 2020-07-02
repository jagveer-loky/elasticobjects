package org.fluentcodes.projects.elasticobjects.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_CONDITION_FALSE_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_CONDITION_TRUE_FAILS;
import static org.fluentcodes.projects.elasticobjects.test.ListProvider.toList;

public class EqTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(EqTest.class);

    @Test
    public void filterAdapter() throws Exception {
        EO adapter = TestEOProvider.createEmptyMap();
        adapter.add(TEO_STATIC.S_TEST_STRING).set(TEO_STATIC.S_STRING);
        Eq eq = new Eq(TEO_STATIC.S_TEST_STRING, TEO_STATIC.S_STRING);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + eq.toString() + adapter.get(TEO_STATIC.S_TEST_STRING), eq.filter(adapter));
        Eq eq2 = new Eq(TEO_STATIC.S_TEST_STRING, TEO_STATIC.S_STRING_OTHER);
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + eq.toString() + adapter.get(TEO_STATIC.S_TEST_STRING), eq2.filter(adapter));
    }

    @Test
    public void filterRow() throws Exception {
        List list = toList(TEO_STATIC.S_STRING, TEO_STATIC.S_STRING_OTHER, null, TEO_STATIC.S_KEY0, TEO_STATIC.S_INTEGER);
        Eq eq = new Eq(TEO_STATIC.S0, TEO_STATIC.S_STRING);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + eq.toString() + list.get(0), eq.filter(list));
        Eq eq2 = new Eq(TEO_STATIC.S3, TEO_STATIC.S_STRING);
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + eq2.toString() + list.get(3), eq2.filter(list));
        Eq eq3 = new Eq(TEO_STATIC.S4, TEO_STATIC.S_INTEGER);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + eq3.toString() + list.get(4), eq3.filter(list));
    }
}
