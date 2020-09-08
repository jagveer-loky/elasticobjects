package org.fluentcodes.projects.elasticobjects.calls.configs;

import org.fluentcodes.projects.elasticobjects.ConfigModelChecks;
import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.calls.Call;
import org.fluentcodes.projects.elasticobjects.testitemprovider.ProviderRootTestScope;
import org.fluentcodes.tools.xpect.IOString;
import org.fluentcodes.tools.xpect.XpectEo;
import org.fluentcodes.tools.xpect.XpectString;
import org.junit.Test;

import java.util.Map;

/**
 * Created 7.9.2020
 */
public class ConfigOpenApiCallTest {
    @Test
    public void createByModelConfig()  {
        ConfigModelChecks.create(ConfigOpenApiCall.class);
    }


    @Test
    public void callConfigFilter_eqConfigCall__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final ConfigOpenApiCall call = new ConfigOpenApiCall(ConfigCall.class.getSimpleName());
        Map result = call.execute(eo);
        new XpectEo().compareAsString(result);
    }

    @Test
    public void eoConfigFilter_eqConfigCall__execute__xpected()  {
        final EO eo = ProviderRootTestScope.createEo();
        final Call call = new ConfigOpenApiCall(ConfigCall.class.getSimpleName())
                .setTargetPath("result");
        eo.addCall(call);
        eo.execute();
        Object resultValue = eo.get("result");
        new XpectEo<>().compareAsString(resultValue);
    }
}
