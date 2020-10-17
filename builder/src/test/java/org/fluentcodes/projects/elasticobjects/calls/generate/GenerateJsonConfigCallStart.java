package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.db.DbSqlConfig;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.domain.Base;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateCall.BUILD_PATH;
import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateJsonConfigCall.CONFIG_TYPE;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE;
import static org.fluentcodes.projects.elasticobjects.models.Config.MODULE_SCOPE;

/**
 * @author Werner Diwischek
 * @since 9.10.2020.
 */
public class GenerateJsonConfigCallStart {

   @Test
    public void callTemplateResourceCall_JsonBuilderTpl__execute__logEmpty() {
        TemplateResourceCall call = new TemplateResourceCall("JsonBuilder.tpl");
        EO eo = ProviderRootTestScope.createEo();
        eo.set(".*", MODULE);
        eo.set("main", MODULE_SCOPE);
        eo.set(FieldConfig.class.getSimpleName() , CONFIG_TYPE);
        eo.set("..", BUILD_PATH);
        String result = call.execute(eo);
        System.out.println(result);
        Assertions.assertThat(result).contains("Written configuration to");
        Assertions.assertThat(eo.getLog()).isEmpty();
    }
}
