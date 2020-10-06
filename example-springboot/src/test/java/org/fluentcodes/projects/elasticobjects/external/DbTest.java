package org.fluentcodes.projects.elasticobjects.external;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EoRoot;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.db.DbQueryCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Before;
import org.junit.Test;


public class DbTest {
    @Before
    public void init() {
        EO eo = ProviderRootTestScope.createEo();
        Call call = new DbSqlCall("h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void call_DbQuery_AnObject__execute__3() {
        DbQueryCall dbQueryCall = new DbQueryCall("h2:mem:basic:AnObject");
        Assertions.assertThat(dbQueryCall).isNotNull();
        EO eo = ProviderRootTestScope.createEo();
        dbQueryCall.execute(eo);
        Assertions.assertThat(eo.size()).isEqualTo(3);
    }

    @Test
    public void eo_DbQuery_AnObject__execute__3() {
        DbQueryCall dbQueryCall = new DbQueryCall("h2:mem:basic:AnObject");
        Assertions.assertThat(dbQueryCall).isNotNull();
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(dbQueryCall);
        eo.execute();
        Assertions.assertThat(eo.size()).isEqualTo(3);
    }

    @Test
    public void eo_DbQuery_AnObject_rowHead_1_rowStart_0_rowEnd_2__execute__2() {
        DbQueryCall call = new DbQueryCall("h2:mem:basic:AnObject");
        call.setRowHead(-1);
        call.setRowEnd(2);
        call.setRowStart(0);
        Assertions.assertThat(call).isNotNull();
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.size()).isEqualTo(2);
    }

    @Test
    public void eo_DbQuery_AnObject_rowHead_1_rowStart_0_rowEnd_2_json__execute__2() {
        EO eo = ProviderRootTestScope.createEo("{\n" +
                "   \"(DbQueryCall)abc\":{\n" +
                "       \"configKey\":\"h2:mem:basic:AnObject\",\n" +
                "        \"rowHead\":-1,\n" +
                "        \"rowStart\":0,\n" +
                "        \"rowEnd\":2\n" +
                "   },\n" +
                "   \"_serializationType\":\"STANDARD\"\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.getEo("abc").size()).isEqualTo(2);
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbQueryCall.class);
    }
}
