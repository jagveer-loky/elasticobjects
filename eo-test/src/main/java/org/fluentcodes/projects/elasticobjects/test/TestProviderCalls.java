package org.fluentcodes.projects.elasticobjects.test;

import org.fluentcodes.projects.elasticobjects.EO_STATIC;
import org.fluentcodes.projects.elasticobjects.calls.*;
import org.fluentcodes.projects.elasticobjects.EO;

import java.util.Map;

/**
 * @author werner diwischek
 * @since 17.9. 2018
 */

public class TestProviderCalls {


    public static EO createConfigCallEO(final String key)  {
        final EO eoEmpty = TestProviderRootTest.createEo();
        final ConfigurationCall call = (ConfigurationCall) new ConfigurationCall().setConfigKey( key);
        call.execute(eoEmpty);
        return eoEmpty;
    }


    public static EO createConfigCallEO(final String key, final String... attributeList)  {
        final EO eoEmpty = TestProviderRootTest.createEo();
        final Map attributes = EO_STATIC.toMap(attributeList);
        final ConfigurationCall call = (ConfigurationCall)new ConfigurationCall().setConfigKey(key);
        call.set(eoEmpty, attributes);
        return eoEmpty;
    }
}
