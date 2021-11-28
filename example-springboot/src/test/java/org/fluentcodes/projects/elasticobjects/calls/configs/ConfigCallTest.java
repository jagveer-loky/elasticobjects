package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Ignore;
import org.junit.Test;

public class ConfigCallTest {
    // TODO later...
    @Ignore
    @Test
    public void testConfigCallForm__configTypeFieldConfig__fieldConfigsUsedForLinkList() {
        EO eo = ProviderConfigMaps.createEo();
        eo.set("Scope", "configSelected");
        eo.set("ModelConfig", "configType");
        eo.set("Scope", "configFilter");
        eo.set("-", "selectedItem");
        eo.addCall(new TemplateResourceCall("ConfigsPage.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
       XpectString.assertJunit((String)eo.get("_template"));
    }

}
