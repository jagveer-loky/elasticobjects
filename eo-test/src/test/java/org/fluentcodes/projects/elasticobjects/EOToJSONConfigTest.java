package org.fluentcodes.projects.elasticobjects;

import org.fluentcodes.projects.elasticobjects.calls.HostConfig;
import org.fluentcodes.projects.elasticobjects.fileprovider.ProviderRootTest;

import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.H_TEST;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONConfigTest {

    @Test
    public void configImpl()  {
        final HostConfig config = (HostConfig) ProviderRootTest.EO_CONFIGS.find(HostConfig.class, H_TEST);
        EO eoHost = ProviderRootTest.createEo(config);
        String serHost = new EOToJSON().toJSON(eoHost);

    }


}
