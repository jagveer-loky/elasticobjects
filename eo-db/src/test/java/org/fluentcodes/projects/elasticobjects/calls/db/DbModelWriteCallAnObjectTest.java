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


public class DbModelWriteCallAnObjectTest {
    @Before
    public void init() {
        EO eo = ProviderRootTestScope.createEo();
        Call call = new DbSqlExecuteCall(H2_BASIC, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void call_AnObject_1L_value1New__execute__updated() {
        DbModelWriteCall dbQueryCall = new DbModelWriteCall(H2_BASIC);
        Assertions.assertThat(dbQueryCall).isNotNull();
        dbQueryCall.setTargetPath("/result/values");
        EO eo = ProviderRootTestScope.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("value1New");
        anObject.setId(1L);
        EO child = eo.set(anObject, "test");
        dbQueryCall.execute(child);
        Assertions.assertThat(eo.getEo("/result/values").size()).isEqualTo(1);
        new XpectEo<>().compareAsString(eo);
    }

    @Test
    public void call_AnObject_4L_value4__execute__inserted() {
        DbModelWriteCall dbQueryCall = new DbModelWriteCall(H2_BASIC);
        Assertions.assertThat(dbQueryCall).isNotNull();
        dbQueryCall.setTargetPath("/result/values");
        EO eo = ProviderRootTestScope.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("value4");
        anObject.setId(4L);
        EO child = eo.set(anObject, "test");
        dbQueryCall.execute(child);
        Assertions.assertThat(eo.getEo("/result/values").size()).isEqualTo(1);
        new XpectEo<>().compareAsString(eo);
    }

}
