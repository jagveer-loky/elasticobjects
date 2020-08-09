package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTest;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;


public class LikeTest {
    private static final Logger LOG = LogManager.getLogger(LikeTest.class);

    @Test
    public void filterAdapter()  {
        EO adapter = ProviderRootTest.createEo();
        adapter.set(S_STRING,S_TEST_STRING);
        Condition condition = new Like(S_TEST_STRING, S_STRING);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + adapter.get(S_TEST_STRING),
                condition.filter(adapter));
        condition = new Like(S_TEST_STRING, S_STRING_OTHER);
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + condition.toString() + adapter.get(S_TEST_STRING),
                condition.filter(adapter));
    }

    @Test
    public void filterRow()  {
        List row = ProviderListJson.JSON_FILTER.createList();
        Condition condition = new Like(S0, S_STRING);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + row.get(0),
                condition.filter(row));
        condition = new Like(S2, S_STRING_OTHER);
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + condition.toString() + row.get(2),
                condition.filter(row));
        condition = new Like(S4, S_INTEGER);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + condition.toString() + " " + row.get(3),
                condition.filter(row));
    }
}
