package org.fluentcodes.projects.elasticobjects.calls.db;

import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Assert;
import org.junit.Test;


/**
 * Created by Werner on 17.8.2020.
 */
public class DbSqlExecuteCallTest {
    private final static String DROP = "h2:mem:basic:AnObjectDrop";
    private final static String CREATE = "h2:mem:basic:Create";
    @Test
    public void createByModelConfig()  {
        ModelConfigChecks.create(DbSqlExecuteCall.class);
    }

    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(DbSqlExecuteCall.class);
    }

    @Test
    public void resolveModelConfig()  {
        ModelConfigChecks.resolve(DbSqlExecuteCall.class);
    }

    @Test
    public void queryAnObject()  {
        DbSqlExecuteCall call = new DbSqlExecuteCall(DbConfig.H2_BASIC, CREATE);
        Assert.assertNotNull(call);
        EO eo = ProviderRootTestScope.createEo();
        call.execute(eo);
    }
}

