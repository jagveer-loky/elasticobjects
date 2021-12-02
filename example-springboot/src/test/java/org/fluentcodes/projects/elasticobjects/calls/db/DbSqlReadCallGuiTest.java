package org.fluentcodes.projects.elasticobjects.calls.db;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.PathElement;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Before;
import org.junit.Test;


public class DbSqlReadCallGuiTest {
    private static final String DB_DEFAULT = "h2:mem:basic";
    private static final String DB_TABLE = "h2:mem:basic:AnObject";

    @Before
    public void init() {
        EO eo = ProviderConfigMaps.createEo();
        Call call = new DbSqlExecuteCall(DB_DEFAULT, "h2:mem:basic:Create");
        call.execute(eo);
    }

    @Test
    public void call_DbQuery_AnObject__execute__3() {
        DbSqlReadCall call = new DbSqlReadCall(DB_DEFAULT, DB_TABLE);
        Assertions.assertThat(call).isNotNull();
        EO eo = ProviderConfigMaps.createEo();
        call.execute(eo);
        Assertions.assertThat(eo.size()).isEqualTo(3);
    }

    @Test
    public void eo_DbQuery_AnObject__execute__3() {
        DbSqlReadCall call = new DbSqlReadCall(DB_DEFAULT, DB_TABLE);
        Assertions.assertThat(call).isNotNull();
        EO eo = ProviderConfigMaps.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.size()).isEqualTo(3);
    }

    @Test
    public void eo_DbQuery_AnObject_rowHead_1_rowStart_0_rowEnd_2__execute__2() {
        DbSqlReadCall call = new DbSqlReadCall(DB_DEFAULT, DB_TABLE);
        call.setRowHead(-1);
        call.setRowEnd(2);
        call.setRowStart(0);
        Assertions.assertThat(call).isNotNull();
        EO eo = ProviderConfigMaps.createEo();
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.size()).isEqualTo(2);
    }

    @Test
    public void eo_DbQuery_AnObject_rowHead_1_rowStart_0_rowEnd_2_json__execute__2() {
        EO eo = ProviderConfigMaps.createEo("{\n" +
                "   \"(DbSqlReadCall)abc\":{\n" +
                "       \"hostConfigKey\":\"h2:mem:basic\",\n" +
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
        EO eo = ProviderConfigMaps.createEo("{\n" +
                "   \"(DbSqlReadCall)xyz\":{\n" +
                "       \"hostConfigKey\":\"h2:mem:basic\",\n" +
                "       \"sqlKey\":\"h2:mem:basic:AnObject\"\n" +
                "   },\n" +
                "   \"(TemplateResourceCall).\":{\"fileConfigKey\":\"table.tpl\", \"sourcePath\":\"xyz\"},\n" +
                "   \"asTemplate\":true\n" +
                "}");
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(eo.getEo("xyz").size()).isEqualTo(3);
       XpectString.assertJunit((String) eo.get(PathElement.TEMPLATE));
    }

    @Test
    public void template_h2MemBasicAnObject_tableTpl__execute__xpected() {
        final TemplateCall call = new TemplateCall("START " +
                "#{DbSqlReadCall->h2:mem:basic, h2:mem:basic:AnObject, xyz}.\n" +
                "#{TemplateResourceCall->table.tpl, xyz}." +
                "END");
        EO eo = ProviderConfigMaps.createEo();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog())
                .isEmpty();
       XpectString.assertJunit(result);
    }

}
