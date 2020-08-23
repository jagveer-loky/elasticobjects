package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.testitemproviders.TestProviderTemplateContent;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderJsonCalls;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

import java.util.List;

public class ConfigCallTest {
    @Test
    public void testConfigCallForm_whenConfigTypeFieldConfig_thenFieldConfigsUsedForLinkList() {
        EO eo = ProviderRootTestScope.createEo();
        eo.set("Scope", "configSelected");
        eo.set("ModelConfig", "configType");
        eo.set("Scope", "configFilter");
        eo.addCall(new TemplateResourceCall("ConfigsPage.html"));
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectString().compareAsString((String)eo.get("_template"));
    }

}
