package org.fluentcodes.projects.elasticobjects.calls.generate;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.files.FileConfig;
import org.fluentcodes.projects.elasticobjects.calls.templates.TemplateResourceCall;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.models.ModuleScope;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.junit.Ignore;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.calls.generate.GenerateCall.FILE_ENDING;
import static org.fluentcodes.projects.elasticobjects.calls.generate.json.GenerateJsonConfigCall.CONFIG_TYPE;
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
        eo.set(Moduls.ALL.getName(), MODULE);
        eo.set(ModuleScope.MAIN.dir(), MODULE_SCOPE);
        eo.set("", FILE_ENDING);
        eo.set(ModelConfig.class.getSimpleName() , CONFIG_TYPE);
        String result = call.execute(eo);
        System.out.println(result);
        Assertions.assertThat(result).contains("src/main/resources");
        Assertions.assertThat(eo.getLog()).isEmpty();
    }
}
