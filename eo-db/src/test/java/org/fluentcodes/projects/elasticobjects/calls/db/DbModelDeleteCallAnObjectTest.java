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


public class DbModelDeleteCallAnObjectTest {
    @Before
    public void init() {
        EO eo = ProviderRootTestScope.createEo();
        Call call = new DbSqlExecuteCall(H2_BASIC, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void call_AnObject_3L__execute__deleted() {
        DbModelDeleteCall call = new DbModelDeleteCall(H2_BASIC);
        call.setTargetPath("/result/values");
        Assertions.assertThat(call).isNotNull();
        EO eo = ProviderRootTestScope.createEo();
        AnObject anObject = new AnObject();
        anObject.setId(3L);
        EO child = eo.set(anObject, "test");
        Object value = call.execute(child);
        Assertions.assertThat(eo.getEo("/result/values").size()).isEqualTo(1);
        new XpectEo<>().compareAsString(eo);
    }

}
