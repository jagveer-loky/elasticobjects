package org.fluentcodes.projects.elasticobjects.external;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlReadCall;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlExecuteCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Before;
import org.junit.Test;


public class DbTest {
    private static final String DB_DEFAULT = "h2:mem:basic";
    private static final String DB_TABLE = "h2:mem:basic:AnObject";
    @Before
    public void init() {
        EO eo = ProviderRootTestScope.createEo();
        Call call = new DbSqlExecuteCall(DB_DEFAULT,"h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void call_DbQuery_AnObject__execute__3() {
        DbSqlReadCall call = new DbSqlReadCall(DB_DEFAULT,DB_TABLE);
        Assertions.assertThat(call).isNotNull();
        EO eo = ProviderRootTestScope.createEo();
        call.execute(eo);
        Assertions.assertThat(eo.size()).isEqualTo(3);
    }

    @Test
    public void eo_DbQuery_AnObject__execute__3() {
        DbSqlReadCall call = new DbSqlReadCall(DB_DEFAULT,DB_TABLE);
        Assertions.assertThat(call).isNotNull();
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.size()).isEqualTo(3);
    }

    @Test
    public void eo_DbQuery_AnObject_rowHead_1_rowStart_0_rowEnd_2__execute__2() {
        DbSqlReadCall call = new DbSqlReadCall(DB_DEFAULT,DB_TABLE);
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
                "   \"(DbSqlReadCall)abc\":{\n" +
                "       \"configKey\":\"h2:mem:basic\",\n" +
                "       \"sqlKey\":\"h2:mem:basic:AnObject\",\n" +
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
    public void eo_DbQuery_with_tableTpl____3() {
        EO eo = ProviderRootTestScope.createEo("{\n" +
                "   \"(DbSqlReadCall)xyz\":{\n" +
                "       \"configKey\":\"h2:mem:basic\",\n" +
                "       \"sqlKey\":\"h2:mem:basic:AnObject\"\n" +
                "   },\n" +
                "   \"(TemplateResourceCall).\":{\"configKey\":\"table.tpl\", \"sourcePath\":\"xyz\"},\n" +
                "   \"asTemplate\":true\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo("xyz").size()).isEqualTo(3);
        new XpectString().compareAsString((String) eo.get(PathElement.TEMPLATE));
    }

    @Test
    public void compareModelConfig()  {
        ConfigModelChecks.compare(DbSqlReadCall.class);
    }
}
