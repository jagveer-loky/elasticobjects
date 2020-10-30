package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Before;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.db.DbConfig.H2_BASIC;


public class DbModelReadCallAnObjectTest {
    @Before
    public void init() {
        EO eo = ProviderRootTestScope.createEo();
        Call call = new DbSqlExecuteCall(H2_BASIC, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void call_DbQuery_AnObject__execute__3() {
        DbModelReadCall dbQueryCall = new DbModelReadCall(H2_BASIC);
        dbQueryCall.setTargetPath("/result/values");
        Assertions.assertThat(dbQueryCall).isNotNull();
        EO eo = ProviderRootTestScope.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("value1");
        EO child = eo.set(anObject, "test");
        dbQueryCall.execute(child);
        Assertions.assertThat(eo.getEo("/result/values").size()).isEqualTo(1);
        new XpectEo<>().compareAsString(eo);
    }

}
