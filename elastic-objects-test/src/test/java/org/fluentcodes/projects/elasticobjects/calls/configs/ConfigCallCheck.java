package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.MAIN;

/**
 * Created 10.6.2018
 */
public class ConfigCallCheck {

    @Test
    public void call_ModelConfig_configFilter_ConfigCall__execute__xpected() {
        ConfigCall call = new ConfigCall(ModelConfig.class,"ConfigCall");
        EO eo = ProviderRootTestScope.createEo();
        List result = (List) call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.EO)
                .build()
                .compareAsString(result);
    }


    @Test
    public void call_FieldConfig_module_eoTest__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigCall call = new ConfigCall(FieldConfig.class);
        call.setFilterModule("elastic-objects-test");
        call.setFilterSubModule(MAIN);
        List result = (List) call.execute(eo);
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.EO)
                .build()
                .compareAsString(result);
    }

    @Test
    public void call_FieldConfig_configFilter_length_condition__execute__xpected() {
        final EO eo = ProviderRootTestScope.createEo();
        final Call call = new ConfigCall(FieldConfig.class)
                .setConfigFilter("length")
                .setStartCondition("length eq $(0/modelKey)$");
        List result = (List) call.execute(eo);
        Assertions.assertThat(result.size()).isEqualTo(1);
    }

    @Test
    public void eo_ModelConfig_module_eoTest_submodule_main__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigCall call = new ConfigCall(ModelConfig.class);
        call.setFilterModule("elastic-objects-test");
        call.setFilterSubModule(MAIN);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.EO)
                .build()
                .compareAsString(eo.get());
    }


    @Test
    public void eo_ModelConfig_configFilter_Map__execute__xpected() {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigCall call = new ConfigCall(ModelConfig.class, ".*Map");
        List result = (List) call.execute(eo);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.EO)
                .build()
                .compareAsString(eo.get());
    }
}
