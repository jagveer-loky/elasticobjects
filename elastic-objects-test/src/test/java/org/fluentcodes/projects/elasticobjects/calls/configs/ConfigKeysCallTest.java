package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserCurlyBracket;
import org.fluentcodes.projects.elasticobjects.calls.templates.ParserCurlyBracketTest;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

import java.util.List;

public class ConfigKeysCallTest {
    @Test
    public void call_configFilter_naturalId__execute__list_size_1() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setConfigFilter("naturalId");
        List<String> result = (List<String>) call.execute(ProviderRootTestScope.createEo());
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void template_ModelConfig_ConfigKeysCall_WEB_configurationList__execute__xpected() {
        EO eo = ProviderRootTestScope.createEo();
        final String targetPath = "configurationList";
        new ParserCurlyBracket("==>{ConfigKeysCall->ModelConfig, ConfigKeysCall, WEB, " + targetPath + "}.")
                .parse(eo);
        new XpectEo<>().compareAsString(eo.get(targetPath));
    }

    @Test
    public void eo_configFilter_naturalId__execute__list_size_1() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setTargetPath("fieldKeys");
        call.setConfigFilter("naturalId");

        EO eo = ProviderRootTestScope.createEo();
        eo.addCall(call);
        eo.execute();

        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(((List)eo.get("fieldKeys")).size()).isGreaterThan(0);
    }
}
