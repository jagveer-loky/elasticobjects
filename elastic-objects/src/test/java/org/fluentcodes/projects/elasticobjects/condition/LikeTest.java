package org.fluentcodes.projects.elasticobjects.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.eo.EO;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;

import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_CONDITION_FALSE_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_CONDITION_TRUE_FAILS;
import static org.fluentcodes.projects.elasticobjects.test.ListProvider.toList;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class LikeTest extends TestHelper {
    private static final Logger LOG = LogManager.getLogger(LikeTest.class);

    @Test
    public void filterAdapter() throws Exception {
        EO adapter = TestObjectProvider.createEOFromJson();
        adapter.add(TEO_STATIC.S_TEST_STRING).set(TEO_STATIC.S_STRING);
        Condition condition = new Like(TEO_STATIC.S_TEST_STRING, TEO_STATIC.S_STRING);
        Assert.assertTrue( INFO_CONDITION_TRUE_FAILS + condition.toString() + adapter.get(TEO_STATIC.S_TEST_STRING),
                condition.filter(adapter));
        condition = new Like(TEO_STATIC.S_TEST_STRING, TEO_STATIC.S_STRING_OTHER);
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + condition.toString() + adapter.get(TEO_STATIC.S_TEST_STRING),
                condition.filter(adapter));
    }

    @Test
    public void filterRow() throws Exception {
        List list = toList(TEO_STATIC.S_STRING, null, TEO_STATIC.S_STRING, TEO_STATIC.S_INTEGER);
        Condition condition = new Like(TEO_STATIC.S0, TEO_STATIC.S_STRING);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + list.get(0),
                condition.filter(list));
        condition = new Like(TEO_STATIC.S2, TEO_STATIC.S_STRING_OTHER);
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + condition.toString() + list.get(2),
                condition.filter(list));
        condition = new Like(TEO_STATIC.S3, TEO_STATIC.S_INTEGER);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + list.get(3),
                condition.filter(list));
    }
}
