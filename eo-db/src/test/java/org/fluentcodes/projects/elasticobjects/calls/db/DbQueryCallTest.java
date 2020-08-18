package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

/**
 * Created by Werner on 28.1.2018.
 */
public class DbQueryCallTest {
    public static final String H2_MEM_BASIC_BASIC_TEST = "h2:mem:basic:BasicTest";
    public static final String H2_MEM_BASIC_SUB_TEST = "h2:mem:basic:SubTest";
    public static final String H2_MEM_BASIC_TEST_DROP = "h2:mem:basic:BasicTestDrop";
    public static final String H2_MEM_BASIC_CREATE = "h2:mem:basic:Create";

    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(DbQueryCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbQueryCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ConfigModelChecks.resolve(DbQueryCall.class);
    }

    @Test
    public void queryBasicTest()  {
        DbQueryCall call = new DbQueryCall(H2_MEM_BASIC_BASIC_TEST);
        Assert.assertNotNull(call);
        EO eo = ProviderRootTestScope.createEo();
        call.execute(eo);
        int counter = 0;
        Assert.assertEquals(2, eo.size());
        ;
    }
}

