package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

public class FaqNavGenerate {
    @Ignore
    @Test
    public void generate() {
        EO eo = ProviderRootTestScope.createEo();
        eo.set("examples","directory");
        TemplateResourceCall call = new TemplateResourceCall("Nav.tpl");
        String result = call.execute(eo);
    }
}
