package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.JSONSerializationType;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.projects.elasticobjects.models.FieldConfig;
import org.fluentcodes.projects.elasticobjects.models.ModelConfig;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Test;

import java.util.List;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.MAIN;

/**
 * Created 10.6.2018
 */
public class ConfigCallTest {

    @Test
    public void whenCompareConfigurations_thenXpected() {
        ConfigCall call = new ConfigCall(ModelConfig.class,"ConfigCall");
        EO eo = ProviderRootTestScope.createEo();
        List result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.EO)
                .build()
                .compareAsString(result);
    }


    @Test
    public void givenCallForFieldAndFilterModuleEoTest_whenExecute_thenXpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigCall call = new ConfigCall(FieldConfig.class);
        call.setFilterModule("eo-test");
        call.setFilterSubModule(MAIN);
        List result = call.execute(eo);
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.EO)
                .build()
                .compareAsString(result);
    }

    @Test
    public void givenEoForModelAndFilterModuleEoTest_whenExecute_thenXpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigCall call = new ConfigCall(ModelConfig.class);
        call.setFilterModule("eo-test");
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
    public void givenEoForModelAndFilterKeyMap_whenExecute_thenXpected() {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigCall call = new ConfigCall(ModelConfig.class, ".*Map");
        List result = call.execute(eo);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectEo.Builder<>()
                .setType(JSONSerializationType.EO)
                .build()
                .compareAsString(eo.get());
    }

}
