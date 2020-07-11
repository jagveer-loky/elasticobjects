package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.EO;
import org.fluentcodes.projects.elasticobjects.EOToJSON;
import org.fluentcodes.projects.elasticobjects.config.HostConfig;
import org.fluentcodes.projects.elasticobjects.test.TestProviderRootTest;

import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.H_TEST;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONConfigTest {

    @Test
    public void configImpl()  {
        final HostConfig config = (HostConfig) TestProviderRootTest.EO_CONFIGS.find(HostConfig.class, H_TEST);
        EO eoHost = TestProviderRootTest.createEo(config);
        String serHost = new EOToJSON().toJSON(eoHost);

    }


}
