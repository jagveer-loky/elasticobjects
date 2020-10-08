package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

public class WebCallTest {
    @Test
    public void givenHeaderHtml_whenExecute_thenLoaded() {
        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(new TemplateResourceCall("WEB").setFileName("Header.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectString().compareAsString((String)eo.get("_template"));
    }
}
