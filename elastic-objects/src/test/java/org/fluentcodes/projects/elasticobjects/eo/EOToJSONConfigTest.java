package org.fluentcodes.projects.elasticobjects.eo;

import org.fluentcodes.projects.elasticobjects.config.HostConfig;
import org.fluentcodes.projects.elasticobjects.paths.Path;
import org.fluentcodes.projects.elasticobjects.test.MapProviderJSON;
import org.fluentcodes.projects.elasticobjects.test.TestObjectProvider;
import org.fluentcodes.projects.elasticobjects.utils.TestHelper;
import org.junit.Assert;
import org.junit.Test;

import java.util.LinkedHashMap;
import java.util.Map;

import static org.fluentcodes.projects.elasticobjects.EO_STATIC_TEST.H_TEST;
import static org.fluentcodes.projects.elasticobjects.TEO_STATIC.*;

/**
 * Created by werner.diwischek on 14.1.18.
 */
public class EOToJSONConfigTest extends TestHelper {

    @Test
    public void configImpl() throws Exception {
        final HostConfig config = (HostConfig) TestObjectProvider.EO_CONFIGS_CACHE.find(HostConfig.class, H_TEST);
        EO eoHost = TestObjectProvider.createEOBuilder().set(config);
        String serHost = new EOToJSON().toJSON(eoHost);

    }


}
