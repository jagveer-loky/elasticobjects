package org.fluentcodes.projects.elasticobjects.calls.condition;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderListJson;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;

import org.junit.Assert;
import org.junit.Test;

import java.util.List;

public class EqTest {
    private static final Logger LOG = LogManager.getLogger(EqTest.class);

    @Test
    public void filterAdapter()  {
        EO adapter = ProviderRootTestScope.createEo();
        adapter.set(S_STRING, S_TEST_STRING);
        Eq eq = new Eq(S_TEST_STRING, S_STRING);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + eq.toString() + adapter.get(S_TEST_STRING), eq.filter(adapter));
        Eq eq2 = new Eq(S_TEST_STRING, S_STRING_OTHER);
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + eq.toString() + adapter.get(S_TEST_STRING), eq2.filter(adapter));
    }

    @Test
    public void filterRow()  {
        List row = ProviderListJson.JSON_FILTER.createListDev();
        Eq eq = new Eq(S0, S_STRING);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + eq.toString() + row.get(0), eq.filter(row));
        Eq eq2 = new Eq(S3, S_STRING);
        Assert.assertFalse(INFO_CONDITION_FALSE_FAILS + eq2.toString() + row.get(3), eq2.filter(row));
        Eq eq3 = new Eq(S3, S_INTEGER);
        Assert.assertTrue(INFO_CONDITION_TRUE_FAILS + eq3.toString() + row.get(3), eq3.filter(row));
    }
}
