package org.fluentcodes.projects.elasticobjects.calls.xlsx;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ModelConfigChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

public class XlsxTest {
    @Test
    public void create() {
        XlsxReadCall xlsxReadCall = new XlsxReadCall ();
        Assertions.assertThat(xlsxReadCall).isNotNull();
    }
    @Test
    public void compareModelConfig()  {
        ModelConfigChecks.compare(XlsxReadCall.class);
    }

    @Test
    public void call_AnObjectXlsxTest__execute__notEmpty()  {
        final Call call = new XlsxReadCall("AnObject.xlsx:test");
        EO eo = ProviderRootTestScope.createEo(new ArrayList<>());
        call.execute(eo);
        List value = (List)eo.get();
        Assertions.assertThat(value).isNotEmpty();
     }

    @Test
    public void template_AnObjectXlsx_tableTpl__execute__xpected()  {
        final TemplateCall call = new TemplateCall("START " +
                "==>{XlsxReadCall->AnObject.xlsx:test, xyz}.\n" +
                "==>{TemplateResourceCall->table.tpl, xyz}." +
                "END");
        EO eo = ProviderRootTestScope.createEo();
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog())
                .isEmpty();
        new XpectString().compareAsString(result);
    }
}
