package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.domain.test.AnObject;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateCall.BUILD_PATH;
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
        eo.set(Moduls.EO_TEST.getName(), MODULE);
        eo.set("main", MODULE_SCOPE);
        eo.set(AnObject.class.getSimpleName() , NATURAL_ID);
        eo.set("..", BUILD_PATH);
        String result = call.execute(eo);
        Assertions.assertThat(result).contains("Written configuration to");
        Assertions.assertThat(eo.getLog()).isEmpty();
    }
}
