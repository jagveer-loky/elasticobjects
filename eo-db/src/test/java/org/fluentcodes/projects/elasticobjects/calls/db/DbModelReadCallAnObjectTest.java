package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
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
        DbModelReadCall call = new DbModelReadCall();
        call.setTargetPath("/result");
        Assertions.assertThat(call).isNotNull();
        EO eo = ProviderRootTestScope.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("value1");
        EO child = eo.set(anObject, "test");
        call.execute(child);
        Assertions.assertThat(eo.getEo("/result").size()).isEqualTo(1);
        Assertions.assertThat(eo.get("/result/0/myString")).isEqualTo("value1");
        Assertions.assertThat(eo.get("/result/0/id")).isEqualTo(1L);
    }

    @Test
    public void eo_DbQuery_AnObject__execute__1() {
        EO eo = ProviderRootTestScope.createEo();
        AnObject anObject = new AnObject();
        anObject.setMyString("value1");
        EO child = eo.set(anObject, "test");

        DbModelReadCall call = new DbModelReadCall();
        call.setTargetPath("/result");
        call.setSourcePath("/test");
        eo.addCall(call);

        eo.execute();
        Assertions.assertThat(eo.getEo("/result").size()).isEqualTo(1);
        Assertions.assertThat(eo.get("/result/0/myString")).isEqualTo("value1");
        Assertions.assertThat(eo.get("/result/0/id")).isEqualTo(1L);
    }

    @Test
    public void eo_sourcePath_abc_AnObject_id_1_targetPath_xyz__execute__1() {
        EO eo = ProviderRootTestScope.createEo("{\n" +
                "   \"(AnObject)abc\":{\n" +
                "        \"id\":1\n" +
                "   },\n" +
                "   \"(DbModelReadCall)/xyz\":{\n" +
                "       \"sourcePath\":\"abc\",\n" +
                "       \"hostConfigKey\":\"h2:mem:basic\"\n" +
                "   }\n" +
                "}");

        eo.execute();
        Assertions.assertThat(eo.getEo("/xyz").size()).isEqualTo(1);
        Assertions.assertThat(eo.get("/xyz/0/myString")).isEqualTo("value1");
        Assertions.assertThat(eo.get("/xyz/0/id")).isEqualTo(1L);
    }



}
