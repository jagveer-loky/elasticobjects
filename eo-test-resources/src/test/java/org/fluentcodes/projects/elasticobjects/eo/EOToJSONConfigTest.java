package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.config.HostConfig;
import org.fluentcodes.projects.elasticobjects.test.TestEOProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Test;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.H_TEST;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONConfigTest extends TestHelper {

    @Test
    public void configImpl()  {
        final HostConfig config = (HostConfig) TestEOProvider.EO_CONFIGS.find(HostConfig.class, H_TEST);
        EO eoHost = TestEOProvider.createEOBuilder().set(config);
        String serHost = new EOToJSON().toJSON(eoHost);

    }


}
