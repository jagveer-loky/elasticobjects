package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
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
    public void givenCallForModelAndConfigFilterConfigCall_whenExecute_thenXpected() {
        ConfigCall call = new ConfigCall(ModelConfig.class,"ConfigCall");
        EO eo = ProviderRootTest.createEo();
        List result = call.execute(eo);
        Assertions.assertThat(result).isNotEmpty();
        new XpectEo().compareAsString(result);
    }


    @Test
    public void givenCallForFieldAndFilterModuleEoTest_whenExecute_thenXpected()  {
        final EO eo = ProviderRootTest.createEo();
        final ConfigCall call = new ConfigCall(FieldConfig.class);
        call.setFilterModule("eo-test");
        call.setFilterSubModule(MAIN);
        List result = call.execute(eo);
        new XpectEo().compareAsString(result);
    }

    @Test
    public void givenEoForModelAndFilterModuleEoTest_whenExecute_thenXpected()  {
        final EO eo = ProviderRootTest.createEo();
        final ConfigCall call = new ConfigCall(ModelConfig.class);
        call.setFilterModule("eo-test");
        call.setFilterSubModule(MAIN);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectEo().compareAsString(eo.get());
    }


    @Test
    public void givenEoForModelAndFilterKeyMap_whenExecute_thenXpected() {
        final EO eo = ProviderRootTest.createEo();
        final ConfigCall call = new ConfigCall(ModelConfig.class, ".*Map");
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        new XpectEo().compareAsString(eo.get());
    }

}
