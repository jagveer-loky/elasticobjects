package org.fluentcodes.projects.elasticobjects.calls.files;

import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

public class FaqNavGenerate {
    @Test
    public void generate() {
        FaqNavCall call = new FaqNavCall("WEB_FAQ");
        call.execute(ProviderRootTestScope.createEo());
    }
}
