package org.fluentcodes.projects.elasticobjects.calls.templates;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

/**
 * @author Werner Diwischek
 * @since 30.10.18.
 */
public class TemplateConfigTest {
    private static final String TEST_TPL_HTML = "test";
    @Test
     public void testPermissions() {
         EO eo = ProviderRootTestScope.createEo();
         eo.setRoles("guest");
         eo.addCall(new TemplateResourceCall("Header.html"));
         eo.execute();
     }
}
