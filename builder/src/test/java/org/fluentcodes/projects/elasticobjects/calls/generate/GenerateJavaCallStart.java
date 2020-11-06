package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateDirResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreCall;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceStoreKeepCall;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateCall.FILE_ENDING;
import static org.fluentcodes.projects.elasticobjects.domain.Base.NATURAL_ID;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE_SCOPE;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateJavaCallStart {

    @Test
    public void callTemplateResourceCall_JavaBuilderTpl__execute__logEmpty() {
        TemplateResourceCall call = new TemplateResourceCall("JavaBuilder.tpl");
        EO eo = ProviderRootTestScope.createEo();
        eo.set(".*", MODULE);
        eo.set(".*", MODULE_SCOPE);
        eo.set("", FILE_ENDING);
        eo.set(TemplateDirResourceCall.class.getSimpleName() , NATURAL_ID);
        String result = call.execute(eo);
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(result).contains("/java/org/");
        System.out.println(result);
    }
}
