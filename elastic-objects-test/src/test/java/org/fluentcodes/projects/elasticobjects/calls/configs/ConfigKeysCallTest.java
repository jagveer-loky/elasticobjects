package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.templates.handler.Parser;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.projects.elasticobjects.xpect.XpectEo;
import org.junit.Ignore;
import org.junit.Test;

import java.util.List;

@Ignore
public class ConfigKeysCallTest {
    @Test
    public void call_configFilter_naturalId__execute__list_size_1() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setConfigFilter("naturalId");
        List<String> result = (List<String>) call.execute(ProviderConfigMaps.createEo());
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void template_ModelConfig_ConfigKeysCall_WEB_configurationList__execute__xpected() {
        EO eo = ProviderConfigMaps.createEo();
        final String targetPath = "configurationList";
        new Parser("#{ConfigKeysCall->ModelConfig, ConfigKeysCall, WEB, " + targetPath + "}.")
                .parse(eo);
        XpectEo.assertJunit(eo.get(targetPath));
    }

    @Test
    public void eo_configFilter_naturalId__execute__list_size_1() {
        ConfigKeysCall call = new ConfigKeysCall();
        call.setConfigType(FieldConfig.class.getSimpleName());
        call.setTargetPath("fieldKeys");
        call.setConfigFilter("naturalId");

        EO eo = ProviderConfigMaps.createEo();
        eo.addCall(call);
        eo.execute();

        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions.assertThat(((List)eo.get("fieldKeys")).size()).isGreaterThan(0);
    }
}
