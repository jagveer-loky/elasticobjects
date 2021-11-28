package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.IModelConfigCreateTests;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderConfigMaps;
import org.fluentcodes.tools.xpect.XpectEo;
import org.junit.Test;

import java.util.Map;

/**
 * Created 7.9.2020
 */

public class ConfigOpenApiCallTest implements IModelConfigCreateTests {

    @Override
    public Class<?> getModelConfigClass() {
        return ConfigOpenApiCall.class;
    }

    @Override
    @Test
    public void create_noEoException() {
        assertCreateNoException();
    }

    @Override
    @Test
    public void compareModelConfig() {
        assertModelConfigEqualsPersisted();
    }

    @Override
    @Test
    public void compareBeanFromModelConfig() {
        assertBeanFromModelConfigEqualsPersisted();
    }

    @Test
    public void callConfigFilter_eqConfigCall__execute__xpected() {
        final EO eo = ProviderConfigMaps.createEo();
        final ConfigOpenApiCall call = new ConfigOpenApiCall(ConfigCall.class.getSimpleName());
        Map result = (Map) call.execute(eo);
        XpectEo.assertJunit(result);
    }

    @Test
    public void eoConfigFilter_eqConfigCall__execute__xpected() {
        final EO eo = ProviderConfigMaps.createEo();
        final Call call = new ConfigOpenApiCall(ConfigCall.class.getSimpleName())
                .setTargetPath("result");
        eo.addCall(call);
        eo.execute();
        Object resultValue = eo.get("result");
        XpectEo.assertJunit(resultValue);
    }
}
