package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.DB_EO_STATIC_TEST.DQ_H2_MEM_BASIC_BASIC_TEST;

/**
 * Created by Werner on 17.8.2020.
 */
public class DbSqlCallTest {
    private final static String DROP = "h2:mem:basic:BasicTestDrop";
    private final static String CREATE = "h2:mem:basic:Create";
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(DbSqlCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbSqlCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ConfigModelChecks.resolve(DbSqlCall.class);
    }

    @Test
    public void queryBasicTest()  {
        DbSqlCall call = new DbSqlCall(CREATE);
        Assert.assertNotNull(call);
        EO eo = ProviderRootTestScope.createEo();
        Object result = call.execute(eo);
    }
}

