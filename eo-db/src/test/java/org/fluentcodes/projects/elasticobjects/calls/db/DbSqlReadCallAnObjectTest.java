package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.DbConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.junit.Before;
import org.junit.Test;


public class DbSqlReadCallAnObjectTest {
    @Before
    public void init() {
        EO eo = ProviderConfigMaps.createEo();
        Call call = new DbSqlExecuteCall(DbConfig.H2_BASIC, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void call_DbQuery_AnObject__execute__3() {
        DbSqlReadCall dbQueryCall = new DbSqlReadCall(DbConfig.H2_BASIC, "h2:mem:basic:AnObject");
        Assertions.assertThat(dbQueryCall).isNotNull();
        EO eo = ProviderConfigMaps.createEo();
        dbQueryCall.execute(eo);
        Assertions.assertThat(eo.size()).isEqualTo(3);
    }
}
