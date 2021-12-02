package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateDirResourceCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

public class WebCallTest {
    @Test
    public void eo_TemplateDirResourceCall_HeaderHtml__execute__xpected() {
        EO eo = ProviderConfigMaps.createEo();
        eo.set("test", "selectedItem");
        eo.addCall(new TemplateDirResourceCall("WEB").setFileName("Header.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
       XpectString.assertJunit((String)eo.get("_template"));
    }
}
