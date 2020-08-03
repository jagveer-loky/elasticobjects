package org.fluentcodes.projects.elasticobjects.calls;

import org.assertj.core.api.Assertions;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.TEO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.configs.ConfigurationCallRead;
import org.fluentcodes.projects.elasticobjects.models.Config;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;
import org.junit.Assert;
import org.junit.Test;

import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC.*;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.INFO_SIZE_FAILS;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.M_BASIC_TEST;

/**
 * Created 10.6.2018
 */
public class ConfigurationCallReadTest {
    @Test
    public void givenCallWithField_whenExecuteCall_thenMapReturned ()  {
        final EO eoEmpty = ProviderRootTest.createEo();
        final ConfigurationCallRead call = new ConfigurationCallRead();
        call.setFilterConfigName(M_FIELD_CONFIG);
        call.setFilterModule(EO_STATIC.MODULE_NAME);
        call.setFilterSubModule(MAIN);
        call.setFilterKey(".*");
        Map<String, Config> map = call.execute(eoEmpty);
        Assertions.assertThat(map).isNotEmpty();
    }

    @Test
    public void givenEoWithCallWithField_whenExecuteEo_thenEoHasMoreThan100Entries ()  {
        final EO eo = ProviderRootTest.createEo();
        final ConfigurationCallRead call = new ConfigurationCallRead(FIELD);
        call.setTargetPath(FIELD);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertTrue(INFO_SIZE_FAILS + eo.getEo(FIELD).sizeEo(), eo.getEo(FIELD).sizeEo() > 100);
        Assertions.assertThat(eo.get(FIELD, F_DESCRIPTION, F_FIELD_KEY)).isEqualTo(F_DESCRIPTION);
    }

    @Test
    public void givenEoWithCallWithModel_whenExecuteEo_thenEoHasMoreThan60Entries()  {
        final EO eo = ProviderRootTest.createEo();
        final ConfigurationCallRead call = new ConfigurationCallRead(MODEL);
        call.setTargetPath(MODEL);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertTrue(INFO_SIZE_FAILS + eo.getEo(MODEL).sizeEo(), eo.getEo(MODEL).sizeEo() > 60);
        Assertions.assertThat(eo.get(MODEL, "ConfigImpl", F_MODEL_KEY)).isEqualTo("ConfigImpl");
    }

    @Test
    public void givenEoWithCallWithModelAndFilterModel_whenExecuteEo_thenEoHasMoreThan60Entries()  {
        final EO eo = ProviderRootTest.createEo();
        final ConfigurationCallRead call = new ConfigurationCallRead(MODEL);
        call.setTargetPath(MODEL);
        call.setFilterModule(TEO_STATIC.MODULE_NAME);
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assert.assertFalse(eo.isEmpty());
        Assert.assertTrue(INFO_SIZE_FAILS + eo.getEo(MODEL).sizeEo(), eo.getEo(MODEL).sizeEo() == 2);
        Assertions.assertThat(eo.get(MODEL, M_BASIC_TEST, F_MODEL_KEY)).isEqualTo(M_BASIC_TEST);
    }

    @Test
    public void givenEoWithCallWithModelAndFilterKey_whenExecuteEo_thenEoHas2Entries() {
        final EO eo = ProviderRootTest.createEo();
        final ConfigurationCallRead call = new ConfigurationCallRead(MODEL);
        call.setTargetPath(MODEL);
        call.setFilterKey(".*Map");
        eo.addCall(call);
        eo.execute();
        Assertions.assertThat(eo.getLog()).isEmpty();
        Assertions
                .assertThat(eo.getEo(MODEL).size())
                .isEqualTo(2);
        Assertions
                .assertThat(eo.get(MODEL, M_HASH_MAP, F_MODEL_KEY))
                .isEqualTo(M_HASH_MAP);
    }

}
